import { Moment } from 'moment';
import { IRegion } from 'app/shared/model/region.model';
import { IVisitor } from 'app/shared/model/visitor.model';
import { IPartner } from 'app/shared/model/partner.model';

export interface ICountry {
  id?: number;
  countryName?: string;
  countryCodeAlpha?: string;
  countryCode?: string;
  countryFlag?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
  createdBy?: string;
  updatedBy?: string;
  region?: IRegion;
  v?: IVisitor;
  v?: IVisitor;
  partner?: IPartner;
}

export class Country implements ICountry {
  constructor(
    public id?: number,
    public countryName?: string,
    public countryCodeAlpha?: string,
    public countryCode?: string,
    public countryFlag?: string,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public createdBy?: string,
    public updatedBy?: string,
    public region?: IRegion,
    public v?: IVisitor,
    public v?: IVisitor,
    public partner?: IPartner
  ) {}
}
