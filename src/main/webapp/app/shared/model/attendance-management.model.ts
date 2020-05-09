import { Moment } from 'moment';
import { IStudent } from 'app/shared/model/student.model';

export interface IAttendanceManagement {
  id?: number;
  signIn?: Moment;
  position?: string;
  coordinate?: string;
  company?: string;
  createdTime?: Moment;
  stuId?: IStudent;
}

export class AttendanceManagement implements IAttendanceManagement {
  constructor(
    public id?: number,
    public signIn?: Moment,
    public position?: string,
    public coordinate?: string,
    public company?: string,
    public createdTime?: Moment,
    public stuId?: IStudent
  ) {}
}
