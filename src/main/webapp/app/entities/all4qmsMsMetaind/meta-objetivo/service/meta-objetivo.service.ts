import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMetaObjetivo, NewMetaObjetivo } from '../meta-objetivo.model';

export type PartialUpdateMetaObjetivo = Partial<IMetaObjetivo> & Pick<IMetaObjetivo, 'id'>;

export type EntityResponseType = HttpResponse<IMetaObjetivo>;
export type EntityArrayResponseType = HttpResponse<IMetaObjetivo[]>;

@Injectable({ providedIn: 'root' })
export class MetaObjetivoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/meta-objetivos', 'all4qmsmsmetaind');

  create(metaObjetivo: NewMetaObjetivo): Observable<EntityResponseType> {
    return this.http.post<IMetaObjetivo>(this.resourceUrl, metaObjetivo, { observe: 'response' });
  }

  update(metaObjetivo: IMetaObjetivo): Observable<EntityResponseType> {
    return this.http.put<IMetaObjetivo>(`${this.resourceUrl}/${this.getMetaObjetivoIdentifier(metaObjetivo)}`, metaObjetivo, {
      observe: 'response',
    });
  }

  partialUpdate(metaObjetivo: PartialUpdateMetaObjetivo): Observable<EntityResponseType> {
    return this.http.patch<IMetaObjetivo>(`${this.resourceUrl}/${this.getMetaObjetivoIdentifier(metaObjetivo)}`, metaObjetivo, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMetaObjetivo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMetaObjetivo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMetaObjetivoIdentifier(metaObjetivo: Pick<IMetaObjetivo, 'id'>): number {
    return metaObjetivo.id;
  }

  compareMetaObjetivo(o1: Pick<IMetaObjetivo, 'id'> | null, o2: Pick<IMetaObjetivo, 'id'> | null): boolean {
    return o1 && o2 ? this.getMetaObjetivoIdentifier(o1) === this.getMetaObjetivoIdentifier(o2) : o1 === o2;
  }

  addMetaObjetivoToCollectionIfMissing<Type extends Pick<IMetaObjetivo, 'id'>>(
    metaObjetivoCollection: Type[],
    ...metaObjetivosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const metaObjetivos: Type[] = metaObjetivosToCheck.filter(isPresent);
    if (metaObjetivos.length > 0) {
      const metaObjetivoCollectionIdentifiers = metaObjetivoCollection.map(metaObjetivoItem =>
        this.getMetaObjetivoIdentifier(metaObjetivoItem),
      );
      const metaObjetivosToAdd = metaObjetivos.filter(metaObjetivoItem => {
        const metaObjetivoIdentifier = this.getMetaObjetivoIdentifier(metaObjetivoItem);
        if (metaObjetivoCollectionIdentifiers.includes(metaObjetivoIdentifier)) {
          return false;
        }
        metaObjetivoCollectionIdentifiers.push(metaObjetivoIdentifier);
        return true;
      });
      return [...metaObjetivosToAdd, ...metaObjetivoCollection];
    }
    return metaObjetivoCollection;
  }
}
