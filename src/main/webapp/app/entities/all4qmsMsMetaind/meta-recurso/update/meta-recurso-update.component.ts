import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IMeta } from 'app/entities/all4qmsMsMetaind/meta/meta.model';
import { MetaService } from 'app/entities/all4qmsMsMetaind/meta/service/meta.service';
import { IMetaRecurso } from '../meta-recurso.model';
import { MetaRecursoService } from '../service/meta-recurso.service';
import { MetaRecursoFormService, MetaRecursoFormGroup } from './meta-recurso-form.service';

@Component({
  standalone: true,
  selector: 'jhi-meta-recurso-update',
  templateUrl: './meta-recurso-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MetaRecursoUpdateComponent implements OnInit {
  isSaving = false;
  metaRecurso: IMetaRecurso | null = null;

  metasSharedCollection: IMeta[] = [];

  protected metaRecursoService = inject(MetaRecursoService);
  protected metaRecursoFormService = inject(MetaRecursoFormService);
  protected metaService = inject(MetaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: MetaRecursoFormGroup = this.metaRecursoFormService.createMetaRecursoFormGroup();

  compareMeta = (o1: IMeta | null, o2: IMeta | null): boolean => this.metaService.compareMeta(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ metaRecurso }) => {
      this.metaRecurso = metaRecurso;
      if (metaRecurso) {
        this.updateForm(metaRecurso);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const metaRecurso = this.metaRecursoFormService.getMetaRecurso(this.editForm);
    if (metaRecurso.id !== null) {
      this.subscribeToSaveResponse(this.metaRecursoService.update(metaRecurso));
    } else {
      this.subscribeToSaveResponse(this.metaRecursoService.create(metaRecurso));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMetaRecurso>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(metaRecurso: IMetaRecurso): void {
    this.metaRecurso = metaRecurso;
    this.metaRecursoFormService.resetForm(this.editForm, metaRecurso);

    this.metasSharedCollection = this.metaService.addMetaToCollectionIfMissing<IMeta>(
      this.metasSharedCollection,
      ...(metaRecurso.metas ?? []),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.metaService
      .query()
      .pipe(map((res: HttpResponse<IMeta[]>) => res.body ?? []))
      .pipe(map((metas: IMeta[]) => this.metaService.addMetaToCollectionIfMissing<IMeta>(metas, ...(this.metaRecurso?.metas ?? []))))
      .subscribe((metas: IMeta[]) => (this.metasSharedCollection = metas));
  }
}
