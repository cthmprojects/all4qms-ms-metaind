import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IIndicador } from '../indicador.model';
import { IndicadorService } from '../service/indicador.service';

const indicadorResolve = (route: ActivatedRouteSnapshot): Observable<null | IIndicador> => {
  const id = route.params['id'];
  if (id) {
    return inject(IndicadorService)
      .find(id)
      .pipe(
        mergeMap((indicador: HttpResponse<IIndicador>) => {
          if (indicador.body) {
            return of(indicador.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default indicadorResolve;
