import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMetaRecurso, NewMetaRecurso } from '../meta-recurso.model';

export type PartialUpdateMetaRecurso = Partial<IMetaRecurso> & Pick<IMetaRecurso, 'id'>;

export type EntityResponseType = HttpResponse<IMetaRecurso>;
export type EntityArrayResponseType = HttpResponse<IMetaRecurso[]>;

@Injectable({ providedIn: 'root' })
export class MetaRecursoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/meta-recursos', 'all4qmsmsmetaind');

  create(metaRecurso: NewMetaRecurso): Observable<EntityResponseType> {
    return this.http.post<IMetaRecurso>(this.resourceUrl, metaRecurso, { observe: 'response' });
  }

  update(metaRecurso: IMetaRecurso): Observable<EntityResponseType> {
    return this.http.put<IMetaRecurso>(`${this.resourceUrl}/${this.getMetaRecursoIdentifier(metaRecurso)}`, metaRecurso, {
      observe: 'response',
    });
  }

  partialUpdate(metaRecurso: PartialUpdateMetaRecurso): Observable<EntityResponseType> {
    return this.http.patch<IMetaRecurso>(`${this.resourceUrl}/${this.getMetaRecursoIdentifier(metaRecurso)}`, metaRecurso, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMetaRecurso>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMetaRecurso[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMetaRecursoIdentifier(metaRecurso: Pick<IMetaRecurso, 'id'>): number {
    return metaRecurso.id;
  }

  compareMetaRecurso(o1: Pick<IMetaRecurso, 'id'> | null, o2: Pick<IMetaRecurso, 'id'> | null): boolean {
    return o1 && o2 ? this.getMetaRecursoIdentifier(o1) === this.getMetaRecursoIdentifier(o2) : o1 === o2;
  }

  addMetaRecursoToCollectionIfMissing<Type extends Pick<IMetaRecurso, 'id'>>(
    metaRecursoCollection: Type[],
    ...metaRecursosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const metaRecursos: Type[] = metaRecursosToCheck.filter(isPresent);
    if (metaRecursos.length > 0) {
      const metaRecursoCollectionIdentifiers = metaRecursoCollection.map(metaRecursoItem => this.getMetaRecursoIdentifier(metaRecursoItem));
      const metaRecursosToAdd = metaRecursos.filter(metaRecursoItem => {
        const metaRecursoIdentifier = this.getMetaRecursoIdentifier(metaRecursoItem);
        if (metaRecursoCollectionIdentifiers.includes(metaRecursoIdentifier)) {
          return false;
        }
        metaRecursoCollectionIdentifiers.push(metaRecursoIdentifier);
        return true;
      });
      return [...metaRecursosToAdd, ...metaRecursoCollection];
    }
    return metaRecursoCollection;
  }
}
