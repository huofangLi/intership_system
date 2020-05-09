import { Moment } from 'moment';

export interface IStudent {
  id?: number;
  stuId?: number;
  gender?: boolean;
  stuClass?: string;
  stuDepartment?: string;
  stuProfession?: string;
  phone?: string;
  privince?: string;
  privinceCode?: string;
  city?: string;
  cityCode?: string;
  country?: string;
  countryCode?: string;
  address?: string;
  hobby?: string;
  skill?: string;
  selfEvaluation?: string;
  skin?: string;
  createdTime?: Moment;
  modifyTime?: Moment;
}

export class Student implements IStudent {
  constructor(
    public id?: number,
    public stuId?: number,
    public gender?: boolean,
    public stuClass?: string,
    public stuDepartment?: string,
    public stuProfession?: string,
    public phone?: string,
    public privince?: string,
    public privinceCode?: string,
    public city?: string,
    public cityCode?: string,
    public country?: string,
    public countryCode?: string,
    public address?: string,
    public hobby?: string,
    public skill?: string,
    public selfEvaluation?: string,
    public skin?: string,
    public createdTime?: Moment,
    public modifyTime?: Moment
  ) {
    this.gender = this.gender || false;
  }
}
