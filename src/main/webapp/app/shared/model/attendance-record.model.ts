import { Moment } from 'moment';
import { IStudent } from 'app/shared/model/student.model';

export interface IAttendanceRecord {
  id?: number;
  internshipBatch?: string;
  punchTime?: Moment;
  punchLocation?: string;
  attendanceStatus?: string;
  createdTime?: Moment;
  modifyTime?: Moment;
  stuId?: IStudent;
}

export class AttendanceRecord implements IAttendanceRecord {
  constructor(
    public id?: number,
    public internshipBatch?: string,
    public punchTime?: Moment,
    public punchLocation?: string,
    public attendanceStatus?: string,
    public createdTime?: Moment,
    public modifyTime?: Moment,
    public stuId?: IStudent
  ) {}
}
