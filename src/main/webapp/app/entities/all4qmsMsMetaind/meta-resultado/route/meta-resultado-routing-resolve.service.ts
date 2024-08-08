import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMetaResultado } from '../meta-resultado.model';
import { MetaResultadoService } from '../service/meta-resultado.service';

const metaResultadoResolve = (route: ActivatedRouteSnapshot): Observable<null | IMetaResultado> => {
  const id = route.params['id'];
  if (id) {
    return inject(MetaResultadoService)
      .find(id)
      .pipe(
        mergeMap((metaResultado: HttpResponse<IMetaResultado>) => {
          if (metaResultado.body) {
            return of(metaResultado.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default metaResultadoResolve;
