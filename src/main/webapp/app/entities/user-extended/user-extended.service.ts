import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IUserExtended } from 'app/shared/model/user-extended.model';

type EntityResponseType = HttpResponse<IUserExtended>;
type EntityArrayResponseType = HttpResponse<IUserExtended[]>;

@Injectable({ providedIn: 'root' })
export class UserExtendedService {
  public resourceUrl = SERVER_API_URL + 'api/user-extendeds';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/user-extendeds';

  constructor(protected http: HttpClient) {}

  create(userExtended: IUserExtended): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userExtended);
    return this.http
      .post<IUserExtended>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userExtended: IUserExtended): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userExtended);
    return this.http
      .put<IUserExtended>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUserExtended>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserExtended[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserExtended[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(userExtended: IUserExtended): IUserExtended {
    const copy: IUserExtended = Object.assign({}, userExtended, {
      createdAt: userExtended.createdAt && userExtended.createdAt.isValid() ? userExtended.createdAt.toJSON() : undefined,
      updatedAt: userExtended.updatedAt && userExtended.updatedAt.isValid() ? userExtended.updatedAt.toJSON() : undefined,
      lastLogin: userExtended.lastLogin && userExtended.lastLogin.isValid() ? userExtended.lastLogin.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt ? moment(res.body.createdAt) : undefined;
      res.body.updatedAt = res.body.updatedAt ? moment(res.body.updatedAt) : undefined;
      res.body.lastLogin = res.body.lastLogin ? moment(res.body.lastLogin) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((userExtended: IUserExtended) => {
        userExtended.createdAt = userExtended.createdAt ? moment(userExtended.createdAt) : undefined;
        userExtended.updatedAt = userExtended.updatedAt ? moment(userExtended.updatedAt) : undefined;
        userExtended.lastLogin = userExtended.lastLogin ? moment(userExtended.lastLogin) : undefined;
      });
    }
    return res;
  }
}
