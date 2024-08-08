import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMetaObjetivo, NewMetaObjetivo } from '../meta-objetivo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMetaObjetivo for edit and NewMetaObjetivoFormGroupInput for create.
 */
type MetaObjetivoFormGroupInput = IMetaObjetivo | PartialWithRequiredKeyOf<NewMetaObjetivo>;

type MetaObjetivoFormDefaults = Pick<NewMetaObjetivo, 'id'>;

type MetaObjetivoFormGroupContent = {
  id: FormControl<IMetaObjetivo['id'] | NewMetaObjetivo['id']>;
  politicaSGQ: FormControl<IMetaObjetivo['politicaSGQ']>;
  desdobramentoSGQ: FormControl<IMetaObjetivo['desdobramentoSGQ']>;
  objetivoSGQ: FormControl<IMetaObjetivo['objetivoSGQ']>;
};

export type MetaObjetivoFormGroup = FormGroup<MetaObjetivoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MetaObjetivoFormService {
  createMetaObjetivoFormGroup(metaObjetivo: MetaObjetivoFormGroupInput = { id: null }): MetaObjetivoFormGroup {
    const metaObjetivoRawValue = {
      ...this.getFormDefaults(),
      ...metaObjetivo,
    };
    return new FormGroup<MetaObjetivoFormGroupContent>({
      id: new FormControl(
        { value: metaObjetivoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      politicaSGQ: new FormControl(metaObjetivoRawValue.politicaSGQ),
      desdobramentoSGQ: new FormControl(metaObjetivoRawValue.desdobramentoSGQ),
      objetivoSGQ: new FormControl(metaObjetivoRawValue.objetivoSGQ),
    });
  }

  getMetaObjetivo(form: MetaObjetivoFormGroup): IMetaObjetivo | NewMetaObjetivo {
    return form.getRawValue() as IMetaObjetivo | NewMetaObjetivo;
  }

  resetForm(form: MetaObjetivoFormGroup, metaObjetivo: MetaObjetivoFormGroupInput): void {
    const metaObjetivoRawValue = { ...this.getFormDefaults(), ...metaObjetivo };
    form.reset(
      {
        ...metaObjetivoRawValue,
        id: { value: metaObjetivoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): MetaObjetivoFormDefaults {
    return {
      id: null,
    };
  }
}
