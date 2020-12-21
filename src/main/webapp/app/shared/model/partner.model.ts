import { Moment } from 'moment';
import { ICountry } from 'app/shared/model/country.model';
import { IUser } from 'app/core/user/user.model';
import { IVisitor } from 'app/shared/model/visitor.model';
import { PartnerType } from 'app/shared/model/enumerations/partner-type.model';

export interface IPartner {
  id?: number;
  name?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
  createdBy?: string;
  updatedBy?: string;
  email?: string;
  address?: string;
  phone?: string;
  partnerType?: PartnerType;
  countries?: ICountry[];
  users?: IUser[];
  visitor?: IVisitor;
}

export class Partner implements IPartner {
  constructor(
    public id?: number,
    public name?: string,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public createdBy?: string,
    public updatedBy?: string,
    public email?: string,
    public address?: string,
    public phone?: string,
    public partnerType?: PartnerType,
    public countries?: ICountry[],
    public users?: IUser[],
    public visitor?: IVisitor
  ) {}
}
