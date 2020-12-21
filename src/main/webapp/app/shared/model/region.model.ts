import { Moment } from 'moment';
import { ICountry } from 'app/shared/model/country.model';

export interface IRegion {
  id?: number;
  regionName?: string;
  regionCode?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
  createdBy?: string;
  updatedBy?: string;
  countries?: ICountry[];
}

export class Region implements IRegion {
  constructor(
    public id?: number,
    public regionName?: string,
    public regionCode?: string,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public createdBy?: string,
    public updatedBy?: string,
    public countries?: ICountry[]
  ) {}
}
