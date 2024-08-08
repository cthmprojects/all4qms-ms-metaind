import dayjs from 'dayjs/esm';

import { IMetaResultado, NewMetaResultado } from './meta-resultado.model';

export const sampleWithRequiredData: IMetaResultado = {
  id: 25339,
};

export const sampleWithPartialData: IMetaResultado = {
  id: 21077,
  lancadoEm: dayjs('2024-08-07T15:56'),
  metaAtingida: true,
  periodo: dayjs('2024-08-07T23:26'),
  analise: 'excepting',
};

export const sampleWithFullData: IMetaResultado = {
  id: 9872,
  lancadoEm: dayjs('2024-08-07T20:11'),
  parcial: false,
  metaAtingida: false,
  periodo: dayjs('2024-08-07T23:05'),
  avaliacao: 'intently ouch whereas',
  analise: 'during',
};

export const sampleWithNewData: NewMetaResultado = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
