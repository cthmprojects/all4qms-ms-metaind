import { IMeta } from 'app/entities/all4qmsMsMetaind/meta/meta.model';

export interface IMetaRecurso {
  id: number;
  recursoNome?: string | null;
  metas?: Pick<IMeta, 'id'>[] | null;
}

export type NewMetaRecurso = Omit<IMetaRecurso, 'id'> & { id: null };
