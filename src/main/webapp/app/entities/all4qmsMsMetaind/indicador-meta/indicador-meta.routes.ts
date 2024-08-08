import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { IndicadorMetaComponent } from './list/indicador-meta.component';
import { IndicadorMetaDetailComponent } from './detail/indicador-meta-detail.component';
import { IndicadorMetaUpdateComponent } from './update/indicador-meta-update.component';
import IndicadorMetaResolve from './route/indicador-meta-routing-resolve.service';

const indicadorMetaRoute: Routes = [
  {
    path: '',
    component: IndicadorMetaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IndicadorMetaDetailComponent,
    resolve: {
      indicadorMeta: IndicadorMetaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IndicadorMetaUpdateComponent,
    resolve: {
      indicadorMeta: IndicadorMetaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IndicadorMetaUpdateComponent,
    resolve: {
      indicadorMeta: IndicadorMetaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default indicadorMetaRoute;
