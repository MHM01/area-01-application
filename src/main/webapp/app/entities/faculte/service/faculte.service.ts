import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFaculte, getFaculteIdentifier } from '../faculte.model';

export type EntityResponseType = HttpResponse<IFaculte>;
export type EntityArrayResponseType = HttpResponse<IFaculte[]>;

@Injectable({ providedIn: 'root' })
export class FaculteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/facultes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(faculte: IFaculte): Observable<EntityResponseType> {
    return this.http.post<IFaculte>(this.resourceUrl, faculte, { observe: 'response' });
  }

  update(faculte: IFaculte): Observable<EntityResponseType> {
    return this.http.put<IFaculte>(`${this.resourceUrl}/${getFaculteIdentifier(faculte) as number}`, faculte, { observe: 'response' });
  }

  partialUpdate(faculte: IFaculte): Observable<EntityResponseType> {
    return this.http.patch<IFaculte>(`${this.resourceUrl}/${getFaculteIdentifier(faculte) as number}`, faculte, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFaculte>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFaculte[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFaculteToCollectionIfMissing(faculteCollection: IFaculte[], ...facultesToCheck: (IFaculte | null | undefined)[]): IFaculte[] {
    const facultes: IFaculte[] = facultesToCheck.filter(isPresent);
    if (facultes.length > 0) {
      const faculteCollectionIdentifiers = faculteCollection.map(faculteItem => getFaculteIdentifier(faculteItem)!);
      const facultesToAdd = facultes.filter(faculteItem => {
        const faculteIdentifier = getFaculteIdentifier(faculteItem);
        if (faculteIdentifier == null || faculteCollectionIdentifiers.includes(faculteIdentifier)) {
          return false;
        }
        faculteCollectionIdentifiers.push(faculteIdentifier);
        return true;
      });
      return [...facultesToAdd, ...faculteCollection];
    }
    return faculteCollection;
  }
}
