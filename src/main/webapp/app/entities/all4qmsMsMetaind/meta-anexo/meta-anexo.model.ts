import dayjs from 'dayjs/esm';
import { IMetaResultado } from 'app/entities/all4qmsMsMetaind/meta-resultado/meta-resultado.model';

export interface IMetaAnexo {
  id: number;
  nomeFisico?: string | null;
  nomeOriginal?: string | null;
  extensao?: string | null;
  caminho?: string | null;
  dataCriacao?: dayjs.Dayjs | null;
  metaResultado?: Pick<IMetaResultado, 'id'> | null;
}

export type NewMetaAnexo = Omit<IMetaAnexo, 'id'> & { id: null };
