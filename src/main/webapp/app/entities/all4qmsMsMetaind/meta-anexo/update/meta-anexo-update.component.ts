import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IMetaResultado } from 'app/entities/all4qmsMsMetaind/meta-resultado/meta-resultado.model';
import { MetaResultadoService } from 'app/entities/all4qmsMsMetaind/meta-resultado/service/meta-resultado.service';
import { IMetaAnexo } from '../meta-anexo.model';
import { MetaAnexoService } from '../service/meta-anexo.service';
import { MetaAnexoFormService, MetaAnexoFormGroup } from './meta-anexo-form.service';

@Component({
  standalone: true,
  selector: 'jhi-meta-anexo-update',
  templateUrl: './meta-anexo-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MetaAnexoUpdateComponent implements OnInit {
  isSaving = false;
  metaAnexo: IMetaAnexo | null = null;

  metaResultadosSharedCollection: IMetaResultado[] = [];

  protected metaAnexoService = inject(MetaAnexoService);
  protected metaAnexoFormService = inject(MetaAnexoFormService);
  protected metaResultadoService = inject(MetaResultadoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: MetaAnexoFormGroup = this.metaAnexoFormService.createMetaAnexoFormGroup();

  compareMetaResultado = (o1: IMetaResultado | null, o2: IMetaResultado | null): boolean =>
    this.metaResultadoService.compareMetaResultado(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ metaAnexo }) => {
      this.metaAnexo = metaAnexo;
      if (metaAnexo) {
        this.updateForm(metaAnexo);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const metaAnexo = this.metaAnexoFormService.getMetaAnexo(this.editForm);
    if (metaAnexo.id !== null) {
      this.subscribeToSaveResponse(this.metaAnexoService.update(metaAnexo));
    } else {
      this.subscribeToSaveResponse(this.metaAnexoService.create(metaAnexo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMetaAnexo>>): void {
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

  protected updateForm(metaAnexo: IMetaAnexo): void {
    this.metaAnexo = metaAnexo;
    this.metaAnexoFormService.resetForm(this.editForm, metaAnexo);

    this.metaResultadosSharedCollection = this.metaResultadoService.addMetaResultadoToCollectionIfMissing<IMetaResultado>(
      this.metaResultadosSharedCollection,
      metaAnexo.metaResultado,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.metaResultadoService
      .query()
      .pipe(map((res: HttpResponse<IMetaResultado[]>) => res.body ?? []))
      .pipe(
        map((metaResultados: IMetaResultado[]) =>
          this.metaResultadoService.addMetaResultadoToCollectionIfMissing<IMetaResultado>(metaResultados, this.metaAnexo?.metaResultado),
        ),
      )
      .subscribe((metaResultados: IMetaResultado[]) => (this.metaResultadosSharedCollection = metaResultados));
  }
}
