import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMeta, NewMeta } from '../meta.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMeta for edit and NewMetaFormGroupInput for create.
 */
type MetaFormGroupInput = IMeta | PartialWithRequiredKeyOf<NewMeta>;

type MetaFormDefaults = Pick<NewMeta, 'id' | 'recursos'>;

type MetaFormGroupContent = {
  id: FormControl<IMeta['id'] | NewMeta['id']>;
  descricao: FormControl<IMeta['descricao']>;
  indicador: FormControl<IMeta['indicador']>;
  medicao: FormControl<IMeta['medicao']>;
  acao: FormControl<IMeta['acao']>;
  avaliacaoResultado: FormControl<IMeta['avaliacaoResultado']>;
  idProcesso: FormControl<IMeta['idProcesso']>;
  monitoramento: FormControl<IMeta['monitoramento']>;
  periodo: FormControl<IMeta['periodo']>;
  recursos: FormControl<IMeta['recursos']>;
  metaObjetivo: FormControl<IMeta['metaObjetivo']>;
};

export type MetaFormGroup = FormGroup<MetaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MetaFormService {
  createMetaFormGroup(meta: MetaFormGroupInput = { id: null }): MetaFormGroup {
    const metaRawValue = {
      ...this.getFormDefaults(),
      ...meta,
    };
    return new FormGroup<MetaFormGroupContent>({
      id: new FormControl(
        { value: metaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      descricao: new FormControl(metaRawValue.descricao),
      indicador: new FormControl(metaRawValue.indicador),
      medicao: new FormControl(metaRawValue.medicao),
      acao: new FormControl(metaRawValue.acao),
      avaliacaoResultado: new FormControl(metaRawValue.avaliacaoResultado),
      idProcesso: new FormControl(metaRawValue.idProcesso),
      monitoramento: new FormControl(metaRawValue.monitoramento),
      periodo: new FormControl(metaRawValue.periodo),
      recursos: new FormControl(metaRawValue.recursos ?? []),
      metaObjetivo: new FormControl(metaRawValue.metaObjetivo),
    });
  }

  getMeta(form: MetaFormGroup): IMeta | NewMeta {
    return form.getRawValue() as IMeta | NewMeta;
  }

  resetForm(form: MetaFormGroup, meta: MetaFormGroupInput): void {
    const metaRawValue = { ...this.getFormDefaults(), ...meta };
    form.reset(
      {
        ...metaRawValue,
        id: { value: metaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): MetaFormDefaults {
    return {
      id: null,
      recursos: [],
    };
  }
}
