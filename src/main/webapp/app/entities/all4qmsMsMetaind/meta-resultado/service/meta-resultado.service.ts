import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMetaResultado, NewMetaResultado } from '../meta-resultado.model';

export type PartialUpdateMetaResultado = Partial<IMetaResultado> & Pick<IMetaResultado, 'id'>;

type RestOf<T extends IMetaResultado | NewMetaResultado> = Omit<T, 'lancadoEm' | 'periodo'> & {
  lancadoEm?: string | null;
  periodo?: string | null;
};

export type RestMetaResultado = RestOf<IMetaResultado>;

export type NewRestMetaResultado = RestOf<NewMetaResultado>;

export type PartialUpdateRestMetaResultado = RestOf<PartialUpdateMetaResultado>;

export type EntityResponseType = HttpResponse<IMetaResultado>;
export type EntityArrayResponseType = HttpResponse<IMetaResultado[]>;

@Injectable({ providedIn: 'root' })
export class MetaResultadoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/meta-resultados', 'all4qmsmsmetaind');

  create(metaResultado: NewMetaResultado): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(metaResultado);
    return this.http
      .post<RestMetaResultado>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(metaResultado: IMetaResultado): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(metaResultado);
    return this.http
      .put<RestMetaResultado>(`${this.resourceUrl}/${this.getMetaResultadoIdentifier(metaResultado)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(metaResultado: PartialUpdateMetaResultado): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(metaResultado);
    return this.http
      .patch<RestMetaResultado>(`${this.resourceUrl}/${this.getMetaResultadoIdentifier(metaResultado)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestMetaResultado>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestMetaResultado[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMetaResultadoIdentifier(metaResultado: Pick<IMetaResultado, 'id'>): number {
    return metaResultado.id;
  }

  compareMetaResultado(o1: Pick<IMetaResultado, 'id'> | null, o2: Pick<IMetaResultado, 'id'> | null): boolean {
    return o1 && o2 ? this.getMetaResultadoIdentifier(o1) === this.getMetaResultadoIdentifier(o2) : o1 === o2;
  }

  addMetaResultadoToCollectionIfMissing<Type extends Pick<IMetaResultado, 'id'>>(
    metaResultadoCollection: Type[],
    ...metaResultadosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const metaResultados: Type[] = metaResultadosToCheck.filter(isPresent);
    if (metaResultados.length > 0) {
      const metaResultadoCollectionIdentifiers = metaResultadoCollection.map(metaResultadoItem =>
        this.getMetaResultadoIdentifier(metaResultadoItem),
      );
      const metaResultadosToAdd = metaResultados.filter(metaResultadoItem => {
        const metaResultadoIdentifier = this.getMetaResultadoIdentifier(metaResultadoItem);
        if (metaResultadoCollectionIdentifiers.includes(metaResultadoIdentifier)) {
          return false;
        }
        metaResultadoCollectionIdentifiers.push(metaResultadoIdentifier);
        return true;
      });
      return [...metaResultadosToAdd, ...metaResultadoCollection];
    }
    return metaResultadoCollection;
  }

  protected convertDateFromClient<T extends IMetaResultado | NewMetaResultado | PartialUpdateMetaResultado>(metaResultado: T): RestOf<T> {
    return {
      ...metaResultado,
      lancadoEm: metaResultado.lancadoEm?.toJSON() ?? null,
      periodo: metaResultado.periodo?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restMetaResultado: RestMetaResultado): IMetaResultado {
    return {
      ...restMetaResultado,
      lancadoEm: restMetaResultado.lancadoEm ? dayjs(restMetaResultado.lancadoEm) : undefined,
      periodo: restMetaResultado.periodo ? dayjs(restMetaResultado.periodo) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestMetaResultado>): HttpResponse<IMetaResultado> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestMetaResultado[]>): HttpResponse<IMetaResultado[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
