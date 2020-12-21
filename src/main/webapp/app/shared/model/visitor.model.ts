import { IPartner } from 'app/shared/model/partner.model';
import { ICountry } from 'app/shared/model/country.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { IdcardType } from 'app/shared/model/enumerations/idcard-type.model';

export interface IVisitor {
  id?: number;
  genderType?: Gender;
  name?: string;
  lastname?: string;
  idcardType?: IdcardType;
  cardExpiringDate?: string;
  birthDate?: string;
  cardNumber?: string;
  cardCountry?: number;
  nationality?: number;
  partners?: IPartner[];
  cs?: ICountry[];
  cs?: ICountry[];
}

export class Visitor implements IVisitor {
  constructor(
    public id?: number,
    public genderType?: Gender,
    public name?: string,
    public lastname?: string,
    public idcardType?: IdcardType,
    public cardExpiringDate?: string,
    public birthDate?: string,
    public cardNumber?: string,
    public cardCountry?: number,
    public nationality?: number,
    public partners?: IPartner[],
    public cs?: ICountry[],
    public cs?: ICountry[]
  ) {}
}
