import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'meta-objetivo',
    data: { pageTitle: 'MetaObjetivos' },
    loadChildren: () => import('./all4qmsMsMetaind/meta-objetivo/meta-objetivo.routes'),
  },
  {
    path: 'meta',
    data: { pageTitle: 'Metas' },
    loadChildren: () => import('./all4qmsMsMetaind/meta/meta.routes'),
  },
  {
    path: 'meta-resultado',
    data: { pageTitle: 'MetaResultados' },
    loadChildren: () => import('./all4qmsMsMetaind/meta-resultado/meta-resultado.routes'),
  },
  {
    path: 'meta-recurso',
    data: { pageTitle: 'MetaRecursos' },
    loadChildren: () => import('./all4qmsMsMetaind/meta-recurso/meta-recurso.routes'),
  },
  {
    path: 'meta-anexo',
    data: { pageTitle: 'MetaAnexos' },
    loadChildren: () => import('./all4qmsMsMetaind/meta-anexo/meta-anexo.routes'),
  },
  {
    path: 'indicador',
    data: { pageTitle: 'Indicadors' },
    loadChildren: () => import('./all4qmsMsMetaind/indicador/indicador.routes'),
  },
  {
    path: 'indicador-meta',
    data: { pageTitle: 'IndicadorMetas' },
    loadChildren: () => import('./all4qmsMsMetaind/indicador-meta/indicador-meta.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
