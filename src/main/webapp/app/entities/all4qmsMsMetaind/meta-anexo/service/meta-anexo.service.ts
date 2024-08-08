import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMetaAnexo, NewMetaAnexo } from '../meta-anexo.model';

export type PartialUpdateMetaAnexo = Partial<IMetaAnexo> & Pick<IMetaAnexo, 'id'>;

type RestOf<T extends IMetaAnexo | NewMetaAnexo> = Omit<T, 'dataCriacao'> & {
  dataCriacao?: string | null;
};

export type RestMetaAnexo = RestOf<IMetaAnexo>;

export type NewRestMetaAnexo = RestOf<NewMetaAnexo>;

export type PartialUpdateRestMetaAnexo = RestOf<PartialUpdateMetaAnexo>;

export type EntityResponseType = HttpResponse<IMetaAnexo>;
export type EntityArrayResponseType = HttpResponse<IMetaAnexo[]>;

@Injectable({ providedIn: 'root' })
export class MetaAnexoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/meta-anexos', 'all4qmsmsmetaind');

  create(metaAnexo: NewMetaAnexo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(metaAnexo);
    return this.http
      .post<RestMetaAnexo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(metaAnexo: IMetaAnexo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(metaAnexo);
    return this.http
      .put<RestMetaAnexo>(`${this.resourceUrl}/${this.getMetaAnexoIdentifier(metaAnexo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(metaAnexo: PartialUpdateMetaAnexo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(metaAnexo);
    return this.http
      .patch<RestMetaAnexo>(`${this.resourceUrl}/${this.getMetaAnexoIdentifier(metaAnexo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestMetaAnexo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestMetaAnexo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMetaAnexoIdentifier(metaAnexo: Pick<IMetaAnexo, 'id'>): number {
    return metaAnexo.id;
  }

  compareMetaAnexo(o1: Pick<IMetaAnexo, 'id'> | null, o2: Pick<IMetaAnexo, 'id'> | null): boolean {
    return o1 && o2 ? this.getMetaAnexoIdentifier(o1) === this.getMetaAnexoIdentifier(o2) : o1 === o2;
  }

  addMetaAnexoToCollectionIfMissing<Type extends Pick<IMetaAnexo, 'id'>>(
    metaAnexoCollection: Type[],
    ...metaAnexosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const metaAnexos: Type[] = metaAnexosToCheck.filter(isPresent);
    if (metaAnexos.length > 0) {
      const metaAnexoCollectionIdentifiers = metaAnexoCollection.map(metaAnexoItem => this.getMetaAnexoIdentifier(metaAnexoItem));
      const metaAnexosToAdd = metaAnexos.filter(metaAnexoItem => {
        const metaAnexoIdentifier = this.getMetaAnexoIdentifier(metaAnexoItem);
        if (metaAnexoCollectionIdentifiers.includes(metaAnexoIdentifier)) {
          return false;
        }
        metaAnexoCollectionIdentifiers.push(metaAnexoIdentifier);
        return true;
      });
      return [...metaAnexosToAdd, ...metaAnexoCollection];
    }
    return metaAnexoCollection;
  }

  protected convertDateFromClient<T extends IMetaAnexo | NewMetaAnexo | PartialUpdateMetaAnexo>(metaAnexo: T): RestOf<T> {
    return {
      ...metaAnexo,
      dataCriacao: metaAnexo.dataCriacao?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restMetaAnexo: RestMetaAnexo): IMetaAnexo {
    return {
      ...restMetaAnexo,
      dataCriacao: restMetaAnexo.dataCriacao ? dayjs(restMetaAnexo.dataCriacao) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestMetaAnexo>): HttpResponse<IMetaAnexo> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestMetaAnexo[]>): HttpResponse<IMetaAnexo[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
