import { IIndicador } from 'app/entities/all4qmsMsMetaind/indicador/indicador.model';
import { EnumTemporal } from 'app/entities/enumerations/enum-temporal.model';

export interface IIndicadorMeta {
  id: number;
  frequencia?: keyof typeof EnumTemporal | null;
  anoIndicador?: string | null;
  meta01?: number | null;
  meta02?: number | null;
  meta03?: number | null;
  meta04?: number | null;
  meta05?: number | null;
  meta06?: number | null;
  meta07?: number | null;
  meta08?: number | null;
  meta09?: number | null;
  meta10?: number | null;
  meta11?: number | null;
  meta12?: number | null;
  medicao01?: number | null;
  medicao02?: number | null;
  medicao03?: number | null;
  medicao04?: number | null;
  medicao05?: number | null;
  medicao06?: number | null;
  medicao07?: number | null;
  medicao08?: number | null;
  medicao09?: number | null;
  medicao10?: number | null;
  medicao11?: number | null;
  medicao12?: number | null;
  indicador?: Pick<IIndicador, 'id'> | null;
}

export type NewIndicadorMeta = Omit<IIndicadorMeta, 'id'> & { id: null };
