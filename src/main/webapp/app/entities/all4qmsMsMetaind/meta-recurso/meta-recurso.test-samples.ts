import { IMetaRecurso, NewMetaRecurso } from './meta-recurso.model';

export const sampleWithRequiredData: IMetaRecurso = {
  id: 12076,
};

export const sampleWithPartialData: IMetaRecurso = {
  id: 10501,
  recursoNome: 'upon',
};

export const sampleWithFullData: IMetaRecurso = {
  id: 18062,
  recursoNome: 'kitchen',
};

export const sampleWithNewData: NewMetaRecurso = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
