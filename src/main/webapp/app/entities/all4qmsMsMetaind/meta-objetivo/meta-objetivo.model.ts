export interface IMetaObjetivo {
  id: number;
  politicaSGQ?: string | null;
  desdobramentoSGQ?: string | null;
  objetivoSGQ?: string | null;
}

export type NewMetaObjetivo = Omit<IMetaObjetivo, 'id'> & { id: null };
