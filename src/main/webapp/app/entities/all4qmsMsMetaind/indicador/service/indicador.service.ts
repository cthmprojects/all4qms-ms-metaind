import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IIndicador, NewIndicador } from '../indicador.model';

export type PartialUpdateIndicador = Partial<IIndicador> & Pick<IIndicador, 'id'>;

export type EntityResponseType = HttpResponse<IIndicador>;
export type EntityArrayResponseType = HttpResponse<IIndicador[]>;

@Injectable({ providedIn: 'root' })
export class IndicadorService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/indicadors', 'all4qmsmsmetaind');

  create(indicador: NewIndicador): Observable<EntityResponseType> {
    return this.http.post<IIndicador>(this.resourceUrl, indicador, { observe: 'response' });
  }

  update(indicador: IIndicador): Observable<EntityResponseType> {
    return this.http.put<IIndicador>(`${this.resourceUrl}/${this.getIndicadorIdentifier(indicador)}`, indicador, { observe: 'response' });
  }

  partialUpdate(indicador: PartialUpdateIndicador): Observable<EntityResponseType> {
    return this.http.patch<IIndicador>(`${this.resourceUrl}/${this.getIndicadorIdentifier(indicador)}`, indicador, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIndicador>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIndicador[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getIndicadorIdentifier(indicador: Pick<IIndicador, 'id'>): number {
    return indicador.id;
  }

  compareIndicador(o1: Pick<IIndicador, 'id'> | null, o2: Pick<IIndicador, 'id'> | null): boolean {
    return o1 && o2 ? this.getIndicadorIdentifier(o1) === this.getIndicadorIdentifier(o2) : o1 === o2;
  }

  addIndicadorToCollectionIfMissing<Type extends Pick<IIndicador, 'id'>>(
    indicadorCollection: Type[],
    ...indicadorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const indicadors: Type[] = indicadorsToCheck.filter(isPresent);
    if (indicadors.length > 0) {
      const indicadorCollectionIdentifiers = indicadorCollection.map(indicadorItem => this.getIndicadorIdentifier(indicadorItem));
      const indicadorsToAdd = indicadors.filter(indicadorItem => {
        const indicadorIdentifier = this.getIndicadorIdentifier(indicadorItem);
        if (indicadorCollectionIdentifiers.includes(indicadorIdentifier)) {
          return false;
        }
        indicadorCollectionIdentifiers.push(indicadorIdentifier);
        return true;
      });
      return [...indicadorsToAdd, ...indicadorCollection];
    }
    return indicadorCollection;
  }
}
