import { Moment } from 'moment';
import { IIntership } from 'app/shared/model/intership.model';

export interface IJobChangeRecords {
  id?: number;
  unitChange?: string;
  positionChange?: string;
  createTime?: Moment;
  interId?: IIntership;
}

export class JobChangeRecords implements IJobChangeRecords {
  constructor(
    public id?: number,
    public unitChange?: string,
    public positionChange?: string,
    public createTime?: Moment,
    public interId?: IIntership
  ) {}
}
