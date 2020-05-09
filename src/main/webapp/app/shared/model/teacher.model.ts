import { Moment } from 'moment';

export interface ITeacher {
  id?: number;
  teaId?: number;
  teaName?: string;
  gender?: boolean;
  teaDepartment?: string;
  skin?: string;
  createdTime?: Moment;
  modifyTime?: Moment;
}

export class Teacher implements ITeacher {
  constructor(
    public id?: number,
    public teaId?: number,
    public teaName?: string,
    public gender?: boolean,
    public teaDepartment?: string,
    public skin?: string,
    public createdTime?: Moment,
    public modifyTime?: Moment
  ) {
    this.gender = this.gender || false;
  }
}
