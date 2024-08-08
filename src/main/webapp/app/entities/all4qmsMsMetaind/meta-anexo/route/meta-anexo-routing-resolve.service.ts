import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMetaAnexo } from '../meta-anexo.model';
import { MetaAnexoService } from '../service/meta-anexo.service';

const metaAnexoResolve = (route: ActivatedRouteSnapshot): Observable<null | IMetaAnexo> => {
  const id = route.params['id'];
  if (id) {
    return inject(MetaAnexoService)
      .find(id)
      .pipe(
        mergeMap((metaAnexo: HttpResponse<IMetaAnexo>) => {
          if (metaAnexo.body) {
            return of(metaAnexo.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default metaAnexoResolve;
