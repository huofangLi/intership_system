import { Moment } from 'moment';
import { IStudent } from 'app/shared/model/student.model';

export interface IGraduationProject {
  id?: number;
  graduationProjectTitle?: string;
  source?: string;
  taskBook?: string;
  taskBookCheck?: boolean;
  taskBookReviews?: string;
  openingReport?: string;
  openingReportCheck?: boolean;
  openingReportReviews?: string;
  midTermInspection?: string;
  midTermInspectionCheck?: boolean;
  midTermInspectionReviews?: string;
  firstDraft?: string;
  firstDraftCheck?: boolean;
  firstDraftReviews?: string;
  finalDraft?: string;
  finalDraftCheck?: boolean;
  finalDraftReviews?: string;
  createdTime?: Moment;
  modifyTime?: Moment;
  stuId?: IStudent;
}

export class GraduationProject implements IGraduationProject {
  constructor(
    public id?: number,
    public graduationProjectTitle?: string,
    public source?: string,
    public taskBook?: string,
    public taskBookCheck?: boolean,
    public taskBookReviews?: string,
    public openingReport?: string,
    public openingReportCheck?: boolean,
    public openingReportReviews?: string,
    public midTermInspection?: string,
    public midTermInspectionCheck?: boolean,
    public midTermInspectionReviews?: string,
    public firstDraft?: string,
    public firstDraftCheck?: boolean,
    public firstDraftReviews?: string,
    public finalDraft?: string,
    public finalDraftCheck?: boolean,
    public finalDraftReviews?: string,
    public createdTime?: Moment,
    public modifyTime?: Moment,
    public stuId?: IStudent
  ) {
    this.taskBookCheck = this.taskBookCheck || false;
    this.openingReportCheck = this.openingReportCheck || false;
    this.midTermInspectionCheck = this.midTermInspectionCheck || false;
    this.firstDraftCheck = this.firstDraftCheck || false;
    this.finalDraftCheck = this.finalDraftCheck || false;
  }
}
