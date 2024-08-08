import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IMetaResultado, NewMetaResultado } from '../meta-resultado.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMetaResultado for edit and NewMetaResultadoFormGroupInput for create.
 */
type MetaResultadoFormGroupInput = IMetaResultado | PartialWithRequiredKeyOf<NewMetaResultado>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IMetaResultado | NewMetaResultado> = Omit<T, 'lancadoEm' | 'periodo'> & {
  lancadoEm?: string | null;
  periodo?: string | null;
};

type MetaResultadoFormRawValue = FormValueOf<IMetaResultado>;

type NewMetaResultadoFormRawValue = FormValueOf<NewMetaResultado>;

type MetaResultadoFormDefaults = Pick<NewMetaResultado, 'id' | 'lancadoEm' | 'parcial' | 'metaAtingida' | 'periodo'>;

type MetaResultadoFormGroupContent = {
  id: FormControl<MetaResultadoFormRawValue['id'] | NewMetaResultado['id']>;
  lancadoEm: FormControl<MetaResultadoFormRawValue['lancadoEm']>;
  parcial: FormControl<MetaResultadoFormRawValue['parcial']>;
  metaAtingida: FormControl<MetaResultadoFormRawValue['metaAtingida']>;
  periodo: FormControl<MetaResultadoFormRawValue['periodo']>;
  avaliacao: FormControl<MetaResultadoFormRawValue['avaliacao']>;
  analise: FormControl<MetaResultadoFormRawValue['analise']>;
  meta: FormControl<MetaResultadoFormRawValue['meta']>;
};

export type MetaResultadoFormGroup = FormGroup<MetaResultadoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MetaResultadoFormService {
  createMetaResultadoFormGroup(metaResultado: MetaResultadoFormGroupInput = { id: null }): MetaResultadoFormGroup {
    const metaResultadoRawValue = this.convertMetaResultadoToMetaResultadoRawValue({
      ...this.getFormDefaults(),
      ...metaResultado,
    });
    return new FormGroup<MetaResultadoFormGroupContent>({
      id: new FormControl(
        { value: metaResultadoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      lancadoEm: new FormControl(metaResultadoRawValue.lancadoEm),
      parcial: new FormControl(metaResultadoRawValue.parcial),
      metaAtingida: new FormControl(metaResultadoRawValue.metaAtingida),
      periodo: new FormControl(metaResultadoRawValue.periodo),
      avaliacao: new FormControl(metaResultadoRawValue.avaliacao, {
        validators: [Validators.maxLength(4000)],
      }),
      analise: new FormControl(metaResultadoRawValue.analise, {
        validators: [Validators.maxLength(4000)],
      }),
      meta: new FormControl(metaResultadoRawValue.meta),
    });
  }

  getMetaResultado(form: MetaResultadoFormGroup): IMetaResultado | NewMetaResultado {
    return this.convertMetaResultadoRawValueToMetaResultado(form.getRawValue() as MetaResultadoFormRawValue | NewMetaResultadoFormRawValue);
  }

  resetForm(form: MetaResultadoFormGroup, metaResultado: MetaResultadoFormGroupInput): void {
    const metaResultadoRawValue = this.convertMetaResultadoToMetaResultadoRawValue({ ...this.getFormDefaults(), ...metaResultado });
    form.reset(
      {
        ...metaResultadoRawValue,
        id: { value: metaResultadoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): MetaResultadoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lancadoEm: currentTime,
      parcial: false,
      metaAtingida: false,
      periodo: currentTime,
    };
  }

  private convertMetaResultadoRawValueToMetaResultado(
    rawMetaResultado: MetaResultadoFormRawValue | NewMetaResultadoFormRawValue,
  ): IMetaResultado | NewMetaResultado {
    return {
      ...rawMetaResultado,
      lancadoEm: dayjs(rawMetaResultado.lancadoEm, DATE_TIME_FORMAT),
      periodo: dayjs(rawMetaResultado.periodo, DATE_TIME_FORMAT),
    };
  }

  private convertMetaResultadoToMetaResultadoRawValue(
    metaResultado: IMetaResultado | (Partial<NewMetaResultado> & MetaResultadoFormDefaults),
  ): MetaResultadoFormRawValue | PartialWithRequiredKeyOf<NewMetaResultadoFormRawValue> {
    return {
      ...metaResultado,
      lancadoEm: metaResultado.lancadoEm ? metaResultado.lancadoEm.format(DATE_TIME_FORMAT) : undefined,
      periodo: metaResultado.periodo ? metaResultado.periodo.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
