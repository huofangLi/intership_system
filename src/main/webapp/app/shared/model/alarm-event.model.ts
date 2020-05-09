import { Moment } from 'moment';
import { IStudent } from 'app/shared/model/student.model';

export interface IAlarmEvent {
  id?: number;
  title?: string;
  content?: string;
  remarks?: string;
  createdTime?: Moment;
  stuId?: IStudent;
}

export class AlarmEvent implements IAlarmEvent {
  constructor(
    public id?: number,
    public title?: string,
    public content?: string,
    public remarks?: string,
    public createdTime?: Moment,
    public stuId?: IStudent
  ) {}
}
