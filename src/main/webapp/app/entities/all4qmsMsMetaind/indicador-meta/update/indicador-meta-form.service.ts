import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IIndicadorMeta, NewIndicadorMeta } from '../indicador-meta.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IIndicadorMeta for edit and NewIndicadorMetaFormGroupInput for create.
 */
type IndicadorMetaFormGroupInput = IIndicadorMeta | PartialWithRequiredKeyOf<NewIndicadorMeta>;

type IndicadorMetaFormDefaults = Pick<NewIndicadorMeta, 'id'>;

type IndicadorMetaFormGroupContent = {
  id: FormControl<IIndicadorMeta['id'] | NewIndicadorMeta['id']>;
  frequencia: FormControl<IIndicadorMeta['frequencia']>;
  anoIndicador: FormControl<IIndicadorMeta['anoIndicador']>;
  meta01: FormControl<IIndicadorMeta['meta01']>;
  meta02: FormControl<IIndicadorMeta['meta02']>;
  meta03: FormControl<IIndicadorMeta['meta03']>;
  meta04: FormControl<IIndicadorMeta['meta04']>;
  meta05: FormControl<IIndicadorMeta['meta05']>;
  meta06: FormControl<IIndicadorMeta['meta06']>;
  meta07: FormControl<IIndicadorMeta['meta07']>;
  meta08: FormControl<IIndicadorMeta['meta08']>;
  meta09: FormControl<IIndicadorMeta['meta09']>;
  meta10: FormControl<IIndicadorMeta['meta10']>;
  meta11: FormControl<IIndicadorMeta['meta11']>;
  meta12: FormControl<IIndicadorMeta['meta12']>;
  medicao01: FormControl<IIndicadorMeta['medicao01']>;
  medicao02: FormControl<IIndicadorMeta['medicao02']>;
  medicao03: FormControl<IIndicadorMeta['medicao03']>;
  medicao04: FormControl<IIndicadorMeta['medicao04']>;
  medicao05: FormControl<IIndicadorMeta['medicao05']>;
  medicao06: FormControl<IIndicadorMeta['medicao06']>;
  medicao07: FormControl<IIndicadorMeta['medicao07']>;
  medicao08: FormControl<IIndicadorMeta['medicao08']>;
  medicao09: FormControl<IIndicadorMeta['medicao09']>;
  medicao10: FormControl<IIndicadorMeta['medicao10']>;
  medicao11: FormControl<IIndicadorMeta['medicao11']>;
  medicao12: FormControl<IIndicadorMeta['medicao12']>;
  indicador: FormControl<IIndicadorMeta['indicador']>;
};

export type IndicadorMetaFormGroup = FormGroup<IndicadorMetaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class IndicadorMetaFormService {
  createIndicadorMetaFormGroup(indicadorMeta: IndicadorMetaFormGroupInput = { id: null }): IndicadorMetaFormGroup {
    const indicadorMetaRawValue = {
      ...this.getFormDefaults(),
      ...indicadorMeta,
    };
    return new FormGroup<IndicadorMetaFormGroupContent>({
      id: new FormControl(
        { value: indicadorMetaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      frequencia: new FormControl(indicadorMetaRawValue.frequencia),
      anoIndicador: new FormControl(indicadorMetaRawValue.anoIndicador),
      meta01: new FormControl(indicadorMetaRawValue.meta01),
      meta02: new FormControl(indicadorMetaRawValue.meta02),
      meta03: new FormControl(indicadorMetaRawValue.meta03),
      meta04: new FormControl(indicadorMetaRawValue.meta04),
      meta05: new FormControl(indicadorMetaRawValue.meta05),
      meta06: new FormControl(indicadorMetaRawValue.meta06),
      meta07: new FormControl(indicadorMetaRawValue.meta07),
      meta08: new FormControl(indicadorMetaRawValue.meta08),
      meta09: new FormControl(indicadorMetaRawValue.meta09),
      meta10: new FormControl(indicadorMetaRawValue.meta10),
      meta11: new FormControl(indicadorMetaRawValue.meta11),
      meta12: new FormControl(indicadorMetaRawValue.meta12),
      medicao01: new FormControl(indicadorMetaRawValue.medicao01),
      medicao02: new FormControl(indicadorMetaRawValue.medicao02),
      medicao03: new FormControl(indicadorMetaRawValue.medicao03),
      medicao04: new FormControl(indicadorMetaRawValue.medicao04),
      medicao05: new FormControl(indicadorMetaRawValue.medicao05),
      medicao06: new FormControl(indicadorMetaRawValue.medicao06),
      medicao07: new FormControl(indicadorMetaRawValue.medicao07),
      medicao08: new FormControl(indicadorMetaRawValue.medicao08),
      medicao09: new FormControl(indicadorMetaRawValue.medicao09),
      medicao10: new FormControl(indicadorMetaRawValue.medicao10),
      medicao11: new FormControl(indicadorMetaRawValue.medicao11),
      medicao12: new FormControl(indicadorMetaRawValue.medicao12),
      indicador: new FormControl(indicadorMetaRawValue.indicador),
    });
  }

  getIndicadorMeta(form: IndicadorMetaFormGroup): IIndicadorMeta | NewIndicadorMeta {
    return form.getRawValue() as IIndicadorMeta | NewIndicadorMeta;
  }

  resetForm(form: IndicadorMetaFormGroup, indicadorMeta: IndicadorMetaFormGroupInput): void {
    const indicadorMetaRawValue = { ...this.getFormDefaults(), ...indicadorMeta };
    form.reset(
      {
        ...indicadorMetaRawValue,
        id: { value: indicadorMetaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): IndicadorMetaFormDefaults {
    return {
      id: null,
    };
  }
}
