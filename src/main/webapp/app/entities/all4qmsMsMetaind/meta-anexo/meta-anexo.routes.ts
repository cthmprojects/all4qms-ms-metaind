import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { MetaAnexoComponent } from './list/meta-anexo.component';
import { MetaAnexoDetailComponent } from './detail/meta-anexo-detail.component';
import { MetaAnexoUpdateComponent } from './update/meta-anexo-update.component';
import MetaAnexoResolve from './route/meta-anexo-routing-resolve.service';

const metaAnexoRoute: Routes = [
  {
    path: '',
    component: MetaAnexoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MetaAnexoDetailComponent,
    resolve: {
      metaAnexo: MetaAnexoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MetaAnexoUpdateComponent,
    resolve: {
      metaAnexo: MetaAnexoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MetaAnexoUpdateComponent,
    resolve: {
      metaAnexo: MetaAnexoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default metaAnexoRoute;
