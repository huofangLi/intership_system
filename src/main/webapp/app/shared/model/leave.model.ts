import { Moment } from 'moment';
import { IStudent } from 'app/shared/model/student.model';

export interface ILeave {
  id?: number;
  submitTime?: Moment;
  leaveTime?: Moment;
  leaveDays?: number;
  company?: string;
  leaveReason?: string;
  status?: boolean;
  createdTime?: Moment;
  stuId?: IStudent;
}

export class Leave implements ILeave {
  constructor(
    public id?: number,
    public submitTime?: Moment,
    public leaveTime?: Moment,
    public leaveDays?: number,
    public company?: string,
    public leaveReason?: string,
    public status?: boolean,
    public createdTime?: Moment,
    public stuId?: IStudent
  ) {
    this.status = this.status || false;
  }
}
