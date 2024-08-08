import { IMeta, NewMeta } from './meta.model';

export const sampleWithRequiredData: IMeta = {
  id: 16788,
};

export const sampleWithPartialData: IMeta = {
  id: 26941,
  descricao: 'forearm micturate productive',
  medicao: 'up pace necessitate',
  acao: 'since',
  avaliacaoResultado: 'startle bout',
  monitoramento: 'ANUAL',
};

export const sampleWithFullData: IMeta = {
  id: 15407,
  descricao: 'backdrop other pfft',
  indicador: 'ready duh vet',
  medicao: 'extroverted',
  acao: 'traverse wander knavishly',
  avaliacaoResultado: 'tensely fat',
  idProcesso: 7108,
  monitoramento: 'TRIMESTRAL',
  periodo: 'MENSAL',
};

export const sampleWithNewData: NewMeta = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
