import { Moment } from 'moment';
import { IIntership } from 'app/shared/model/intership.model';

export interface IInternshipTask {
  id?: number;
  taskTitle?: string;
  founder?: string;
  startTime?: Moment;
  endTime?: Moment;
  importance?: string;
  complexity?: string;
  taskAnnex?: string;
  star?: string;
  status?: string;
  operating?: string;
  createTime?: Moment;
  interId?: IIntership;
}

export class InternshipTask implements IInternshipTask {
  constructor(
    public id?: number,
    public taskTitle?: string,
    public founder?: string,
    public startTime?: Moment,
    public endTime?: Moment,
    public importance?: string,
    public complexity?: string,
    public taskAnnex?: string,
    public star?: string,
    public status?: string,
    public operating?: string,
    public createTime?: Moment,
    public interId?: IIntership
  ) {}
}
