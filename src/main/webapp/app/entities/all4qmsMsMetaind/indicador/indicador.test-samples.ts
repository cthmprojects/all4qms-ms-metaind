import { IIndicador, NewIndicador } from './indicador.model';

export const sampleWithRequiredData: IIndicador = {
  id: 24363,
};

export const sampleWithPartialData: IIndicador = {
  id: 14114,
  nomeIndicador: 'lovely',
  unidade: 'DECIMAL',
  tendencia: 'MENOR',
  idMetaIndicador: 10943,
};

export const sampleWithFullData: IIndicador = {
  id: 26283,
  codigoIndicador: 'feign debar octopus',
  nomeIndicador: 'fortunate now',
  descricaoIndicador: 'what',
  unidade: 'PERCENTUAL',
  tendencia: 'MENOR',
  idProcesso: 17339,
  idMetaIndicador: 21892,
};

export const sampleWithNewData: NewIndicador = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
