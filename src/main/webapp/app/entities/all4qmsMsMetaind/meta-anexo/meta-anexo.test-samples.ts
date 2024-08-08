import dayjs from 'dayjs/esm';

import { IMetaAnexo, NewMetaAnexo } from './meta-anexo.model';

export const sampleWithRequiredData: IMetaAnexo = {
  id: 14678,
};

export const sampleWithPartialData: IMetaAnexo = {
  id: 2388,
  nomeFisico: 'mute',
  nomeOriginal: 'arrogantly apple liven',
  extensao: 'painfully panic next',
  caminho: 'without riddle',
  dataCriacao: dayjs('2024-08-07T15:20'),
};

export const sampleWithFullData: IMetaAnexo = {
  id: 24447,
  nomeFisico: 'silently wearily',
  nomeOriginal: 'next when toward',
  extensao: 'that',
  caminho: 'headline',
  dataCriacao: dayjs('2024-08-08T00:39'),
};

export const sampleWithNewData: NewMetaAnexo = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
