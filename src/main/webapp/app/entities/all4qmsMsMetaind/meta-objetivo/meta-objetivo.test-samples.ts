import { IMetaObjetivo, NewMetaObjetivo } from './meta-objetivo.model';

export const sampleWithRequiredData: IMetaObjetivo = {
  id: 27765,
};

export const sampleWithPartialData: IMetaObjetivo = {
  id: 196,
  politicaSGQ: 'apud',
};

export const sampleWithFullData: IMetaObjetivo = {
  id: 5381,
  politicaSGQ: 'after though zowie',
  desdobramentoSGQ: 'retrench alert',
  objetivoSGQ: 'barren where',
};

export const sampleWithNewData: NewMetaObjetivo = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
