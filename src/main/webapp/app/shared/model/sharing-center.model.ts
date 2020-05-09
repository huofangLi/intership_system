import { Moment } from 'moment';
import { IStudent } from 'app/shared/model/student.model';

export interface ISharingCenter {
  id?: number;
  fileName?: string;
  fileSize?: string;
  uploadedBy?: string;
  department?: string;
  operating?: string;
  createdTime?: Moment;
  modifyTime?: Moment;
  stuId?: IStudent;
}

export class SharingCenter implements ISharingCenter {
  constructor(
    public id?: number,
    public fileName?: string,
    public fileSize?: string,
    public uploadedBy?: string,
    public department?: string,
    public operating?: string,
    public createdTime?: Moment,
    public modifyTime?: Moment,
    public stuId?: IStudent
  ) {}
}
