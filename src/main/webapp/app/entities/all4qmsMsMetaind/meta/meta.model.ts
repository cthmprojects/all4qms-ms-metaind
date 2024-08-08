import { IMetaRecurso } from 'app/entities/all4qmsMsMetaind/meta-recurso/meta-recurso.model';
import { IMetaObjetivo } from 'app/entities/all4qmsMsMetaind/meta-objetivo/meta-objetivo.model';
import { EnumTemporal } from 'app/entities/enumerations/enum-temporal.model';

export interface IMeta {
  id: number;
  descricao?: string | null;
  indicador?: string | null;
  medicao?: string | null;
  acao?: string | null;
  avaliacaoResultado?: string | null;
  idProcesso?: number | null;
  monitoramento?: keyof typeof EnumTemporal | null;
  periodo?: keyof typeof EnumTemporal | null;
  recursos?: Pick<IMetaRecurso, 'id'>[] | null;
  metaObjetivo?: Pick<IMetaObjetivo, 'id'> | null;
}

export type NewMeta = Omit<IMeta, 'id'> & { id: null };
