import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IMetaRecurso } from 'app/entities/all4qmsMsMetaind/meta-recurso/meta-recurso.model';
import { MetaRecursoService } from 'app/entities/all4qmsMsMetaind/meta-recurso/service/meta-recurso.service';
import { IMetaObjetivo } from 'app/entities/all4qmsMsMetaind/meta-objetivo/meta-objetivo.model';
import { MetaObjetivoService } from 'app/entities/all4qmsMsMetaind/meta-objetivo/service/meta-objetivo.service';
import { EnumTemporal } from 'app/entities/enumerations/enum-temporal.model';
import { MetaService } from '../service/meta.service';
import { IMeta } from '../meta.model';
import { MetaFormService, MetaFormGroup } from './meta-form.service';

@Component({
  standalone: true,
  selector: 'jhi-meta-update',
  templateUrl: './meta-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MetaUpdateComponent implements OnInit {
  isSaving = false;
  meta: IMeta | null = null;
  enumTemporalValues = Object.keys(EnumTemporal);

  metaRecursosSharedCollection: IMetaRecurso[] = [];
  metaObjetivosSharedCollection: IMetaObjetivo[] = [];

  protected metaService = inject(MetaService);
  protected metaFormService = inject(MetaFormService);
  protected metaRecursoService = inject(MetaRecursoService);
  protected metaObjetivoService = inject(MetaObjetivoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: MetaFormGroup = this.metaFormService.createMetaFormGroup();

  compareMetaRecurso = (o1: IMetaRecurso | null, o2: IMetaRecurso | null): boolean => this.metaRecursoService.compareMetaRecurso(o1, o2);

  compareMetaObjetivo = (o1: IMetaObjetivo | null, o2: IMetaObjetivo | null): boolean =>
    this.metaObjetivoService.compareMetaObjetivo(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ meta }) => {
      this.meta = meta;
      if (meta) {
        this.updateForm(meta);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const meta = this.metaFormService.getMeta(this.editForm);
    if (meta.id !== null) {
      this.subscribeToSaveResponse(this.metaService.update(meta));
    } else {
      this.subscribeToSaveResponse(this.metaService.create(meta));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMeta>>): void {
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

  protected updateForm(meta: IMeta): void {
    this.meta = meta;
    this.metaFormService.resetForm(this.editForm, meta);

    this.metaRecursosSharedCollection = this.metaRecursoService.addMetaRecursoToCollectionIfMissing<IMetaRecurso>(
      this.metaRecursosSharedCollection,
      ...(meta.recursos ?? []),
    );
    this.metaObjetivosSharedCollection = this.metaObjetivoService.addMetaObjetivoToCollectionIfMissing<IMetaObjetivo>(
      this.metaObjetivosSharedCollection,
      meta.metaObjetivo,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.metaRecursoService
      .query()
      .pipe(map((res: HttpResponse<IMetaRecurso[]>) => res.body ?? []))
      .pipe(
        map((metaRecursos: IMetaRecurso[]) =>
          this.metaRecursoService.addMetaRecursoToCollectionIfMissing<IMetaRecurso>(metaRecursos, ...(this.meta?.recursos ?? [])),
        ),
      )
      .subscribe((metaRecursos: IMetaRecurso[]) => (this.metaRecursosSharedCollection = metaRecursos));

    this.metaObjetivoService
      .query()
      .pipe(map((res: HttpResponse<IMetaObjetivo[]>) => res.body ?? []))
      .pipe(
        map((metaObjetivos: IMetaObjetivo[]) =>
          this.metaObjetivoService.addMetaObjetivoToCollectionIfMissing<IMetaObjetivo>(metaObjetivos, this.meta?.metaObjetivo),
        ),
      )
      .subscribe((metaObjetivos: IMetaObjetivo[]) => (this.metaObjetivosSharedCollection = metaObjetivos));
  }
}
