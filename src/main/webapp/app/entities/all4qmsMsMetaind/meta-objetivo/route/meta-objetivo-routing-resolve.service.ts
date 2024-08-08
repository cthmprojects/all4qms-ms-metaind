import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMetaObjetivo } from '../meta-objetivo.model';
import { MetaObjetivoService } from '../service/meta-objetivo.service';

const metaObjetivoResolve = (route: ActivatedRouteSnapshot): Observable<null | IMetaObjetivo> => {
  const id = route.params['id'];
  if (id) {
    return inject(MetaObjetivoService)
      .find(id)
      .pipe(
        mergeMap((metaObjetivo: HttpResponse<IMetaObjetivo>) => {
          if (metaObjetivo.body) {
            return of(metaObjetivo.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default metaObjetivoResolve;
