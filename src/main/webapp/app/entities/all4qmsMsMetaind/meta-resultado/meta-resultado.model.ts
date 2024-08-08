import dayjs from 'dayjs/esm';
import { IMeta } from 'app/entities/all4qmsMsMetaind/meta/meta.model';

export interface IMetaResultado {
  id: number;
  lancadoEm?: dayjs.Dayjs | null;
  parcial?: boolean | null;
  metaAtingida?: boolean | null;
  periodo?: dayjs.Dayjs | null;
  avaliacao?: string | null;
  analise?: string | null;
  meta?: Pick<IMeta, 'id'> | null;
}

export type NewMetaResultado = Omit<IMetaResultado, 'id'> & { id: null };
