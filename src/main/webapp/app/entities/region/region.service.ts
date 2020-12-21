import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IRegion } from 'app/shared/model/region.model';

type EntityResponseType = HttpResponse<IRegion>;
type EntityArrayResponseType = HttpResponse<IRegion[]>;

@Injectable({ providedIn: 'root' })
export class RegionService {
  public resourceUrl = SERVER_API_URL + 'api/regions';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/regions';

  constructor(protected http: HttpClient) {}

  create(region: IRegion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(region);
    return this.http
      .post<IRegion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(region: IRegion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(region);
    return this.http
      .put<IRegion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRegion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRegion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRegion[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(region: IRegion): IRegion {
    const copy: IRegion = Object.assign({}, region, {
      createdAt: region.createdAt && region.createdAt.isValid() ? region.createdAt.toJSON() : undefined,
      updatedAt: region.updatedAt && region.updatedAt.isValid() ? region.updatedAt.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt ? moment(res.body.createdAt) : undefined;
      res.body.updatedAt = res.body.updatedAt ? moment(res.body.updatedAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((region: IRegion) => {
        region.createdAt = region.createdAt ? moment(region.createdAt) : undefined;
        region.updatedAt = region.updatedAt ? moment(region.updatedAt) : undefined;
      });
    }
    return res;
  }
}
