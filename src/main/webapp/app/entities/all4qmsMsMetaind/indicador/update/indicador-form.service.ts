import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IIndicador, NewIndicador } from '../indicador.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IIndicador for edit and NewIndicadorFormGroupInput for create.
 */
type IndicadorFormGroupInput = IIndicador | PartialWithRequiredKeyOf<NewIndicador>;

type IndicadorFormDefaults = Pick<NewIndicador, 'id'>;

type IndicadorFormGroupContent = {
  id: FormControl<IIndicador['id'] | NewIndicador['id']>;
  codigoIndicador: FormControl<IIndicador['codigoIndicador']>;
  nomeIndicador: FormControl<IIndicador['nomeIndicador']>;
  descricaoIndicador: FormControl<IIndicador['descricaoIndicador']>;
  unidade: FormControl<IIndicador['unidade']>;
  tendencia: FormControl<IIndicador['tendencia']>;
  idProcesso: FormControl<IIndicador['idProcesso']>;
  idMetaIndicador: FormControl<IIndicador['idMetaIndicador']>;
};

export type IndicadorFormGroup = FormGroup<IndicadorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class IndicadorFormService {
  createIndicadorFormGroup(indicador: IndicadorFormGroupInput = { id: null }): IndicadorFormGroup {
    const indicadorRawValue = {
      ...this.getFormDefaults(),
      ...indicador,
    };
    return new FormGroup<IndicadorFormGroupContent>({
      id: new FormControl(
        { value: indicadorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      codigoIndicador: new FormControl(indicadorRawValue.codigoIndicador),
      nomeIndicador: new FormControl(indicadorRawValue.nomeIndicador),
      descricaoIndicador: new FormControl(indicadorRawValue.descricaoIndicador),
      unidade: new FormControl(indicadorRawValue.unidade),
      tendencia: new FormControl(indicadorRawValue.tendencia),
      idProcesso: new FormControl(indicadorRawValue.idProcesso),
      idMetaIndicador: new FormControl(indicadorRawValue.idMetaIndicador),
    });
  }

  getIndicador(form: IndicadorFormGroup): IIndicador | NewIndicador {
    return form.getRawValue() as IIndicador | NewIndicador;
  }

  resetForm(form: IndicadorFormGroup, indicador: IndicadorFormGroupInput): void {
    const indicadorRawValue = { ...this.getFormDefaults(), ...indicador };
    form.reset(
      {
        ...indicadorRawValue,
        id: { value: indicadorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): IndicadorFormDefaults {
    return {
      id: null,
    };
  }
}
