import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { MetaObjetivoComponent } from './list/meta-objetivo.component';
import { MetaObjetivoDetailComponent } from './detail/meta-objetivo-detail.component';
import { MetaObjetivoUpdateComponent } from './update/meta-objetivo-update.component';
import MetaObjetivoResolve from './route/meta-objetivo-routing-resolve.service';

const metaObjetivoRoute: Routes = [
  {
    path: '',
    component: MetaObjetivoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MetaObjetivoDetailComponent,
    resolve: {
      metaObjetivo: MetaObjetivoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MetaObjetivoUpdateComponent,
    resolve: {
      metaObjetivo: MetaObjetivoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MetaObjetivoUpdateComponent,
    resolve: {
      metaObjetivo: MetaObjetivoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default metaObjetivoRoute;
