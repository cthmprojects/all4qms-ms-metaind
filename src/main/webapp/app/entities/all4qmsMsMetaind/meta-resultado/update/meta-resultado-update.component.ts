import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IMeta } from 'app/entities/all4qmsMsMetaind/meta/meta.model';
import { MetaService } from 'app/entities/all4qmsMsMetaind/meta/service/meta.service';
import { IMetaResultado } from '../meta-resultado.model';
import { MetaResultadoService } from '../service/meta-resultado.service';
import { MetaResultadoFormService, MetaResultadoFormGroup } from './meta-resultado-form.service';

@Component({
  standalone: true,
  selector: 'jhi-meta-resultado-update',
  templateUrl: './meta-resultado-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MetaResultadoUpdateComponent implements OnInit {
  isSaving = false;
  metaResultado: IMetaResultado | null = null;

  metasSharedCollection: IMeta[] = [];

  protected metaResultadoService = inject(MetaResultadoService);
  protected metaResultadoFormService = inject(MetaResultadoFormService);
  protected metaService = inject(MetaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: MetaResultadoFormGroup = this.metaResultadoFormService.createMetaResultadoFormGroup();

  compareMeta = (o1: IMeta | null, o2: IMeta | null): boolean => this.metaService.compareMeta(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ metaResultado }) => {
      this.metaResultado = metaResultado;
      if (metaResultado) {
        this.updateForm(metaResultado);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const metaResultado = this.metaResultadoFormService.getMetaResultado(this.editForm);
    if (metaResultado.id !== null) {
      this.subscribeToSaveResponse(this.metaResultadoService.update(metaResultado));
    } else {
      this.subscribeToSaveResponse(this.metaResultadoService.create(metaResultado));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMetaResultado>>): void {
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

  protected updateForm(metaResultado: IMetaResultado): void {
    this.metaResultado = metaResultado;
    this.metaResultadoFormService.resetForm(this.editForm, metaResultado);

    this.metasSharedCollection = this.metaService.addMetaToCollectionIfMissing<IMeta>(this.metasSharedCollection, metaResultado.meta);
  }

  protected loadRelationshipsOptions(): void {
    this.metaService
      .query()
      .pipe(map((res: HttpResponse<IMeta[]>) => res.body ?? []))
      .pipe(map((metas: IMeta[]) => this.metaService.addMetaToCollectionIfMissing<IMeta>(metas, this.metaResultado?.meta)))
      .subscribe((metas: IMeta[]) => (this.metasSharedCollection = metas));
  }
}
