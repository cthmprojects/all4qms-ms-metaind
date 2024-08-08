import { IIndicadorMeta, NewIndicadorMeta } from './indicador-meta.model';

export const sampleWithRequiredData: IIndicadorMeta = {
  id: 23980,
};

export const sampleWithPartialData: IIndicadorMeta = {
  id: 10685,
  anoIndicador: 'fatally doodle duh',
  meta01: 9456.38,
  meta02: 7009.95,
  meta04: 22626.37,
  meta05: 28307.19,
  meta06: 26122.19,
  meta07: 25607.15,
  meta08: 17844.21,
  meta10: 2454.85,
  meta11: 1999.15,
  meta12: 424.13,
  medicao01: 32531.15,
  medicao03: 32407.34,
  medicao04: 3663.54,
  medicao05: 31655.07,
  medicao07: 14894.42,
  medicao08: 32319.32,
  medicao10: 22493.59,
  medicao11: 5241.15,
};

export const sampleWithFullData: IIndicadorMeta = {
  id: 23038,
  frequencia: 'TRIMESTRAL',
  anoIndicador: 'geez mist',
  meta01: 7046.31,
  meta02: 294.52,
  meta03: 30471.98,
  meta04: 25774.76,
  meta05: 18903.04,
  meta06: 13882.73,
  meta07: 9772.79,
  meta08: 10033.17,
  meta09: 29735.32,
  meta10: 17632.7,
  meta11: 24136.57,
  meta12: 26088,
  medicao01: 31314.79,
  medicao02: 9444.52,
  medicao03: 15348.52,
  medicao04: 7987.35,
  medicao05: 13723.36,
  medicao06: 6466.6,
  medicao07: 3469.89,
  medicao08: 14008.75,
  medicao09: 1968.12,
  medicao10: 12301.51,
  medicao11: 21356.57,
  medicao12: 6635.47,
};

export const sampleWithNewData: NewIndicadorMeta = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
