import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { MetaRecursoComponent } from './list/meta-recurso.component';
import { MetaRecursoDetailComponent } from './detail/meta-recurso-detail.component';
import { MetaRecursoUpdateComponent } from './update/meta-recurso-update.component';
import MetaRecursoResolve from './route/meta-recurso-routing-resolve.service';

const metaRecursoRoute: Routes = [
  {
    path: '',
    component: MetaRecursoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MetaRecursoDetailComponent,
    resolve: {
      metaRecurso: MetaRecursoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MetaRecursoUpdateComponent,
    resolve: {
      metaRecurso: MetaRecursoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MetaRecursoUpdateComponent,
    resolve: {
      metaRecurso: MetaRecursoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default metaRecursoRoute;
