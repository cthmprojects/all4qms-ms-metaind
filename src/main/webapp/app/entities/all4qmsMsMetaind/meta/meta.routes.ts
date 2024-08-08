import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { MetaComponent } from './list/meta.component';
import { MetaDetailComponent } from './detail/meta-detail.component';
import { MetaUpdateComponent } from './update/meta-update.component';
import MetaResolve from './route/meta-routing-resolve.service';

const metaRoute: Routes = [
  {
    path: '',
    component: MetaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MetaDetailComponent,
    resolve: {
      meta: MetaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MetaUpdateComponent,
    resolve: {
      meta: MetaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MetaUpdateComponent,
    resolve: {
      meta: MetaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default metaRoute;
