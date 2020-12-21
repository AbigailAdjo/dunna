import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface IUserExtended {
  id?: number;
  genderType?: Gender;
  phone?: string;
  responsible?: boolean;
  createdAt?: Moment;
  updatedAt?: Moment;
  createdBy?: string;
  updatedBy?: string;
  avatarContentType?: string;
  avatar?: any;
  lastLogin?: Moment;
  user?: IUser;
}

export class UserExtended implements IUserExtended {
  constructor(
    public id?: number,
    public genderType?: Gender,
    public phone?: string,
    public responsible?: boolean,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public createdBy?: string,
    public updatedBy?: string,
    public avatarContentType?: string,
    public avatar?: any,
    public lastLogin?: Moment,
    public user?: IUser
  ) {
    this.responsible = this.responsible || false;
  }
}
