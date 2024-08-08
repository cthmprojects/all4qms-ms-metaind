import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { MetaResultadoComponent } from './list/meta-resultado.component';
import { MetaResultadoDetailComponent } from './detail/meta-resultado-detail.component';
import { MetaResultadoUpdateComponent } from './update/meta-resultado-update.component';
import MetaResultadoResolve from './route/meta-resultado-routing-resolve.service';

const metaResultadoRoute: Routes = [
  {
    path: '',
    component: MetaResultadoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MetaResultadoDetailComponent,
    resolve: {
      metaResultado: MetaResultadoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MetaResultadoUpdateComponent,
    resolve: {
      metaResultado: MetaResultadoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MetaResultadoUpdateComponent,
    resolve: {
      metaResultado: MetaResultadoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default metaResultadoRoute;
