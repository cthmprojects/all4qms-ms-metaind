import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMetaRecurso } from '../meta-recurso.model';
import { MetaRecursoService } from '../service/meta-recurso.service';

const metaRecursoResolve = (route: ActivatedRouteSnapshot): Observable<null | IMetaRecurso> => {
  const id = route.params['id'];
  if (id) {
    return inject(MetaRecursoService)
      .find(id)
      .pipe(
        mergeMap((metaRecurso: HttpResponse<IMetaRecurso>) => {
          if (metaRecurso.body) {
            return of(metaRecurso.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default metaRecursoResolve;
