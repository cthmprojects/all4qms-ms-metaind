import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IIndicador } from 'app/entities/all4qmsMsMetaind/indicador/indicador.model';
import { IndicadorService } from 'app/entities/all4qmsMsMetaind/indicador/service/indicador.service';
import { EnumTemporal } from 'app/entities/enumerations/enum-temporal.model';
import { IndicadorMetaService } from '../service/indicador-meta.service';
import { IIndicadorMeta } from '../indicador-meta.model';
import { IndicadorMetaFormService, IndicadorMetaFormGroup } from './indicador-meta-form.service';

@Component({
  standalone: true,
  selector: 'jhi-indicador-meta-update',
  templateUrl: './indicador-meta-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class IndicadorMetaUpdateComponent implements OnInit {
  isSaving = false;
  indicadorMeta: IIndicadorMeta | null = null;
  enumTemporalValues = Object.keys(EnumTemporal);

  indicadorsSharedCollection: IIndicador[] = [];

  protected indicadorMetaService = inject(IndicadorMetaService);
  protected indicadorMetaFormService = inject(IndicadorMetaFormService);
  protected indicadorService = inject(IndicadorService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: IndicadorMetaFormGroup = this.indicadorMetaFormService.createIndicadorMetaFormGroup();

  compareIndicador = (o1: IIndicador | null, o2: IIndicador | null): boolean => this.indicadorService.compareIndicador(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ indicadorMeta }) => {
      this.indicadorMeta = indicadorMeta;
      if (indicadorMeta) {
        this.updateForm(indicadorMeta);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const indicadorMeta = this.indicadorMetaFormService.getIndicadorMeta(this.editForm);
    if (indicadorMeta.id !== null) {
      this.subscribeToSaveResponse(this.indicadorMetaService.update(indicadorMeta));
    } else {
      this.subscribeToSaveResponse(this.indicadorMetaService.create(indicadorMeta));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIndicadorMeta>>): void {
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

  protected updateForm(indicadorMeta: IIndicadorMeta): void {
    this.indicadorMeta = indicadorMeta;
    this.indicadorMetaFormService.resetForm(this.editForm, indicadorMeta);

    this.indicadorsSharedCollection = this.indicadorService.addIndicadorToCollectionIfMissing<IIndicador>(
      this.indicadorsSharedCollection,
      indicadorMeta.indicador,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.indicadorService
      .query()
      .pipe(map((res: HttpResponse<IIndicador[]>) => res.body ?? []))
      .pipe(
        map((indicadors: IIndicador[]) =>
          this.indicadorService.addIndicadorToCollectionIfMissing<IIndicador>(indicadors, this.indicadorMeta?.indicador),
        ),
      )
      .subscribe((indicadors: IIndicador[]) => (this.indicadorsSharedCollection = indicadors));
  }
}
