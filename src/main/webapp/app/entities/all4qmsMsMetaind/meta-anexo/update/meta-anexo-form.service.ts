import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IMetaAnexo, NewMetaAnexo } from '../meta-anexo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMetaAnexo for edit and NewMetaAnexoFormGroupInput for create.
 */
type MetaAnexoFormGroupInput = IMetaAnexo | PartialWithRequiredKeyOf<NewMetaAnexo>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IMetaAnexo | NewMetaAnexo> = Omit<T, 'dataCriacao'> & {
  dataCriacao?: string | null;
};

type MetaAnexoFormRawValue = FormValueOf<IMetaAnexo>;

type NewMetaAnexoFormRawValue = FormValueOf<NewMetaAnexo>;

type MetaAnexoFormDefaults = Pick<NewMetaAnexo, 'id' | 'dataCriacao'>;

type MetaAnexoFormGroupContent = {
  id: FormControl<MetaAnexoFormRawValue['id'] | NewMetaAnexo['id']>;
  nomeFisico: FormControl<MetaAnexoFormRawValue['nomeFisico']>;
  nomeOriginal: FormControl<MetaAnexoFormRawValue['nomeOriginal']>;
  extensao: FormControl<MetaAnexoFormRawValue['extensao']>;
  caminho: FormControl<MetaAnexoFormRawValue['caminho']>;
  dataCriacao: FormControl<MetaAnexoFormRawValue['dataCriacao']>;
  metaResultado: FormControl<MetaAnexoFormRawValue['metaResultado']>;
};

export type MetaAnexoFormGroup = FormGroup<MetaAnexoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MetaAnexoFormService {
  createMetaAnexoFormGroup(metaAnexo: MetaAnexoFormGroupInput = { id: null }): MetaAnexoFormGroup {
    const metaAnexoRawValue = this.convertMetaAnexoToMetaAnexoRawValue({
      ...this.getFormDefaults(),
      ...metaAnexo,
    });
    return new FormGroup<MetaAnexoFormGroupContent>({
      id: new FormControl(
        { value: metaAnexoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nomeFisico: new FormControl(metaAnexoRawValue.nomeFisico),
      nomeOriginal: new FormControl(metaAnexoRawValue.nomeOriginal),
      extensao: new FormControl(metaAnexoRawValue.extensao),
      caminho: new FormControl(metaAnexoRawValue.caminho),
      dataCriacao: new FormControl(metaAnexoRawValue.dataCriacao),
      metaResultado: new FormControl(metaAnexoRawValue.metaResultado),
    });
  }

  getMetaAnexo(form: MetaAnexoFormGroup): IMetaAnexo | NewMetaAnexo {
    return this.convertMetaAnexoRawValueToMetaAnexo(form.getRawValue() as MetaAnexoFormRawValue | NewMetaAnexoFormRawValue);
  }

  resetForm(form: MetaAnexoFormGroup, metaAnexo: MetaAnexoFormGroupInput): void {
    const metaAnexoRawValue = this.convertMetaAnexoToMetaAnexoRawValue({ ...this.getFormDefaults(), ...metaAnexo });
    form.reset(
      {
        ...metaAnexoRawValue,
        id: { value: metaAnexoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): MetaAnexoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataCriacao: currentTime,
    };
  }

  private convertMetaAnexoRawValueToMetaAnexo(rawMetaAnexo: MetaAnexoFormRawValue | NewMetaAnexoFormRawValue): IMetaAnexo | NewMetaAnexo {
    return {
      ...rawMetaAnexo,
      dataCriacao: dayjs(rawMetaAnexo.dataCriacao, DATE_TIME_FORMAT),
    };
  }

  private convertMetaAnexoToMetaAnexoRawValue(
    metaAnexo: IMetaAnexo | (Partial<NewMetaAnexo> & MetaAnexoFormDefaults),
  ): MetaAnexoFormRawValue | PartialWithRequiredKeyOf<NewMetaAnexoFormRawValue> {
    return {
      ...metaAnexo,
      dataCriacao: metaAnexo.dataCriacao ? metaAnexo.dataCriacao.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
