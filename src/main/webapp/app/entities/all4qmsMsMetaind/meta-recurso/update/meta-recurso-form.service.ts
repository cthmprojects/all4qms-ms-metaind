import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMetaRecurso, NewMetaRecurso } from '../meta-recurso.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMetaRecurso for edit and NewMetaRecursoFormGroupInput for create.
 */
type MetaRecursoFormGroupInput = IMetaRecurso | PartialWithRequiredKeyOf<NewMetaRecurso>;

type MetaRecursoFormDefaults = Pick<NewMetaRecurso, 'id' | 'metas'>;

type MetaRecursoFormGroupContent = {
  id: FormControl<IMetaRecurso['id'] | NewMetaRecurso['id']>;
  recursoNome: FormControl<IMetaRecurso['recursoNome']>;
  metas: FormControl<IMetaRecurso['metas']>;
};

export type MetaRecursoFormGroup = FormGroup<MetaRecursoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MetaRecursoFormService {
  createMetaRecursoFormGroup(metaRecurso: MetaRecursoFormGroupInput = { id: null }): MetaRecursoFormGroup {
    const metaRecursoRawValue = {
      ...this.getFormDefaults(),
      ...metaRecurso,
    };
    return new FormGroup<MetaRecursoFormGroupContent>({
      id: new FormControl(
        { value: metaRecursoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      recursoNome: new FormControl(metaRecursoRawValue.recursoNome),
      metas: new FormControl(metaRecursoRawValue.metas ?? []),
    });
  }

  getMetaRecurso(form: MetaRecursoFormGroup): IMetaRecurso | NewMetaRecurso {
    return form.getRawValue() as IMetaRecurso | NewMetaRecurso;
  }

  resetForm(form: MetaRecursoFormGroup, metaRecurso: MetaRecursoFormGroupInput): void {
    const metaRecursoRawValue = { ...this.getFormDefaults(), ...metaRecurso };
    form.reset(
      {
        ...metaRecursoRawValue,
        id: { value: metaRecursoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): MetaRecursoFormDefaults {
    return {
      id: null,
      metas: [],
    };
  }
}
