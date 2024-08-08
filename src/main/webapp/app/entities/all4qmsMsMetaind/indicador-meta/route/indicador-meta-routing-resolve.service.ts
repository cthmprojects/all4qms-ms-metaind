import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IIndicadorMeta } from '../indicador-meta.model';
import { IndicadorMetaService } from '../service/indicador-meta.service';

const indicadorMetaResolve = (route: ActivatedRouteSnapshot): Observable<null | IIndicadorMeta> => {
  const id = route.params['id'];
  if (id) {
    return inject(IndicadorMetaService)
      .find(id)
      .pipe(
        mergeMap((indicadorMeta: HttpResponse<IIndicadorMeta>) => {
          if (indicadorMeta.body) {
            return of(indicadorMeta.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default indicadorMetaResolve;
