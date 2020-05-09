import { Moment } from 'moment';
import { IStudent } from 'app/shared/model/student.model';
import { ITeacher } from 'app/shared/model/teacher.model';

export interface IStuTea {
  id?: number;
  createTime?: Moment;
  stuId?: IStudent;
  teaId?: ITeacher;
}

export class StuTea implements IStuTea {
  constructor(public id?: number, public createTime?: Moment, public stuId?: IStudent, public teaId?: ITeacher) {}
}
