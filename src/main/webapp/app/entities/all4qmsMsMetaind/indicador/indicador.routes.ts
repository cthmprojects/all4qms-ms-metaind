import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { IndicadorComponent } from './list/indicador.component';
import { IndicadorDetailComponent } from './detail/indicador-detail.component';
import { IndicadorUpdateComponent } from './update/indicador-update.component';
import IndicadorResolve from './route/indicador-routing-resolve.service';

const indicadorRoute: Routes = [
  {
    path: '',
    component: IndicadorComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IndicadorDetailComponent,
    resolve: {
      indicador: IndicadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IndicadorUpdateComponent,
    resolve: {
      indicador: IndicadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IndicadorUpdateComponent,
    resolve: {
      indicador: IndicadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default indicadorRoute;
