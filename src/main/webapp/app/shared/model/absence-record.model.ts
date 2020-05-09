import { Moment } from 'moment';
import { IStudent } from 'app/shared/model/student.model';

export interface IAbsenceRecord {
  id?: number;
  absenceStartTime?: Moment;
  absenceEndTime?: Moment;
  absenceDays?: number;
  company?: string;
  remarks?: string;
  createdTime?: Moment;
  stuId?: IStudent;
}

export class AbsenceRecord implements IAbsenceRecord {
  constructor(
    public id?: number,
    public absenceStartTime?: Moment,
    public absenceEndTime?: Moment,
    public absenceDays?: number,
    public company?: string,
    public remarks?: string,
    public createdTime?: Moment,
    public stuId?: IStudent
  ) {}
}
