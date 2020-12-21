import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IPartner } from 'app/shared/model/partner.model';

type EntityResponseType = HttpResponse<IPartner>;
type EntityArrayResponseType = HttpResponse<IPartner[]>;

@Injectable({ providedIn: 'root' })
export class PartnerService {
  public resourceUrl = SERVER_API_URL + 'api/partners';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/partners';

  constructor(protected http: HttpClient) {}

  create(partner: IPartner): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partner);
    return this.http
      .post<IPartner>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(partner: IPartner): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partner);
    return this.http
      .put<IPartner>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPartner>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPartner[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPartner[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(partner: IPartner): IPartner {
    const copy: IPartner = Object.assign({}, partner, {
      createdAt: partner.createdAt && partner.createdAt.isValid() ? partner.createdAt.toJSON() : undefined,
      updatedAt: partner.updatedAt && partner.updatedAt.isValid() ? partner.updatedAt.toJSON() : undefined,
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
      res.body.forEach((partner: IPartner) => {
        partner.createdAt = partner.createdAt ? moment(partner.createdAt) : undefined;
        partner.updatedAt = partner.updatedAt ? moment(partner.updatedAt) : undefined;
      });
    }
    return res;
  }
}
