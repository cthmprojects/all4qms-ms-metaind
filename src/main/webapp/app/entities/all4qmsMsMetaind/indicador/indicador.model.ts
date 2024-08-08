import { EnumUnidadeMedida } from 'app/entities/enumerations/enum-unidade-medida.model';
import { EnumTendencia } from 'app/entities/enumerations/enum-tendencia.model';

export interface IIndicador {
  id: number;
  codigoIndicador?: string | null;
  nomeIndicador?: string | null;
  descricaoIndicador?: string | null;
  unidade?: keyof typeof EnumUnidadeMedida | null;
  tendencia?: keyof typeof EnumTendencia | null;
  idProcesso?: number | null;
  idMetaIndicador?: number | null;
}

export type NewIndicador = Omit<IIndicador, 'id'> & { id: null };
