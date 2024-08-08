import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IIndicadorMeta, NewIndicadorMeta } from '../indicador-meta.model';

export type PartialUpdateIndicadorMeta = Partial<IIndicadorMeta> & Pick<IIndicadorMeta, 'id'>;

export type EntityResponseType = HttpResponse<IIndicadorMeta>;
export type EntityArrayResponseType = HttpResponse<IIndicadorMeta[]>;

@Injectable({ providedIn: 'root' })
export class IndicadorMetaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/indicador-metas', 'all4qmsmsmetaind');

  create(indicadorMeta: NewIndicadorMeta): Observable<EntityResponseType> {
    return this.http.post<IIndicadorMeta>(this.resourceUrl, indicadorMeta, { observe: 'response' });
  }

  update(indicadorMeta: IIndicadorMeta): Observable<EntityResponseType> {
    return this.http.put<IIndicadorMeta>(`${this.resourceUrl}/${this.getIndicadorMetaIdentifier(indicadorMeta)}`, indicadorMeta, {
      observe: 'response',
    });
  }

  partialUpdate(indicadorMeta: PartialUpdateIndicadorMeta): Observable<EntityResponseType> {
    return this.http.patch<IIndicadorMeta>(`${this.resourceUrl}/${this.getIndicadorMetaIdentifier(indicadorMeta)}`, indicadorMeta, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIndicadorMeta>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIndicadorMeta[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getIndicadorMetaIdentifier(indicadorMeta: Pick<IIndicadorMeta, 'id'>): number {
    return indicadorMeta.id;
  }

  compareIndicadorMeta(o1: Pick<IIndicadorMeta, 'id'> | null, o2: Pick<IIndicadorMeta, 'id'> | null): boolean {
    return o1 && o2 ? this.getIndicadorMetaIdentifier(o1) === this.getIndicadorMetaIdentifier(o2) : o1 === o2;
  }

  addIndicadorMetaToCollectionIfMissing<Type extends Pick<IIndicadorMeta, 'id'>>(
    indicadorMetaCollection: Type[],
    ...indicadorMetasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const indicadorMetas: Type[] = indicadorMetasToCheck.filter(isPresent);
    if (indicadorMetas.length > 0) {
      const indicadorMetaCollectionIdentifiers = indicadorMetaCollection.map(indicadorMetaItem =>
        this.getIndicadorMetaIdentifier(indicadorMetaItem),
      );
      const indicadorMetasToAdd = indicadorMetas.filter(indicadorMetaItem => {
        const indicadorMetaIdentifier = this.getIndicadorMetaIdentifier(indicadorMetaItem);
        if (indicadorMetaCollectionIdentifiers.includes(indicadorMetaIdentifier)) {
          return false;
        }
        indicadorMetaCollectionIdentifiers.push(indicadorMetaIdentifier);
        return true;
      });
      return [...indicadorMetasToAdd, ...indicadorMetaCollection];
    }
    return indicadorMetaCollection;
  }
}
