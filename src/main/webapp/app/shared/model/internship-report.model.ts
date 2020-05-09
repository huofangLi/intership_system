import { Moment } from 'moment';
import { IIntership } from 'app/shared/model/intership.model';

export interface IInternshipReport {
  id?: number;
  reportType?: string;
  projectName?: string;
  intershipStage?: string;
  annex?: string;
  reportContent?: string;
  createTime?: Moment;
  star?: string;
  status?: string;
  operating?: string;
  interId?: IIntership;
}

export class InternshipReport implements IInternshipReport {
  constructor(
    public id?: number,
    public reportType?: string,
    public projectName?: string,
    public intershipStage?: string,
    public annex?: string,
    public reportContent?: string,
    public createTime?: Moment,
    public star?: string,
    public status?: string,
    public operating?: string,
    public interId?: IIntership
  ) {}
}
