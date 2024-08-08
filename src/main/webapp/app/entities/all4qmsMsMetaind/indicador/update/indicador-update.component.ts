import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { EnumUnidadeMedida } from 'app/entities/enumerations/enum-unidade-medida.model';
import { EnumTendencia } from 'app/entities/enumerations/enum-tendencia.model';
import { IIndicador } from '../indicador.model';
import { IndicadorService } from '../service/indicador.service';
import { IndicadorFormService, IndicadorFormGroup } from './indicador-form.service';

@Component({
  standalone: true,
  selector: 'jhi-indicador-update',
  templateUrl: './indicador-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class IndicadorUpdateComponent implements OnInit {
  isSaving = false;
  indicador: IIndicador | null = null;
  enumUnidadeMedidaValues = Object.keys(EnumUnidadeMedida);
  enumTendenciaValues = Object.keys(EnumTendencia);

  protected indicadorService = inject(IndicadorService);
  protected indicadorFormService = inject(IndicadorFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: IndicadorFormGroup = this.indicadorFormService.createIndicadorFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ indicador }) => {
      this.indicador = indicador;
      if (indicador) {
        this.updateForm(indicador);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const indicador = this.indicadorFormService.getIndicador(this.editForm);
    if (indicador.id !== null) {
      this.subscribeToSaveResponse(this.indicadorService.update(indicador));
    } else {
      this.subscribeToSaveResponse(this.indicadorService.create(indicador));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIndicador>>): void {
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

  protected updateForm(indicador: IIndicador): void {
    this.indicador = indicador;
    this.indicadorFormService.resetForm(this.editForm, indicador);
  }
}
