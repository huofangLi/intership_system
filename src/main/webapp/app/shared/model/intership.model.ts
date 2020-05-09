import { Moment } from 'moment';
import { IStudent } from 'app/shared/model/student.model';

export interface IIntership {
  id?: number;
  batchName?: string;
  practiceType?: string;
  course?: string;
  beginIntership?: Moment;
  endIntership?: Moment;
  tutorScore?: number;
  masterScore?: number;
  intershipScore?: number;
  companyCreditCode?: string;
  companyName?: string;
  internship?: string;
  companyContactName?: string;
  companyContactJob?: string;
  companyContactPhone?: string;
  masterContactName?: string;
  masterContactSkill?: string;
  masterContactPhone?: string;
  urgentContactName?: string;
  urgentContactPhone?: string;
  urgentContactAddress?: string;
  accommodationType?: string;
  accommodationAddress?: string;
  companyAddress?: string;
  companyDetailAddress?: string;
  companyNature?: string;
  scale?: string;
  industry?: string;
  companyProfile?: string;
  isInsurance?: string;
  insuranceCompanyAndPolicyNumber?: string;
  isInternshipAgreement?: boolean;
  annex?: string;
  createTime?: Moment;
  modifyTime?: Moment;
  stuId?: IStudent;
}

export class Intership implements IIntership {
  constructor(
    public id?: number,
    public batchName?: string,
    public practiceType?: string,
    public course?: string,
    public beginIntership?: Moment,
    public endIntership?: Moment,
    public tutorScore?: number,
    public masterScore?: number,
    public intershipScore?: number,
    public companyCreditCode?: string,
    public companyName?: string,
    public internship?: string,
    public companyContactName?: string,
    public companyContactJob?: string,
    public companyContactPhone?: string,
    public masterContactName?: string,
    public masterContactSkill?: string,
    public masterContactPhone?: string,
    public urgentContactName?: string,
    public urgentContactPhone?: string,
    public urgentContactAddress?: string,
    public accommodationType?: string,
    public accommodationAddress?: string,
    public companyAddress?: string,
    public companyDetailAddress?: string,
    public companyNature?: string,
    public scale?: string,
    public industry?: string,
    public companyProfile?: string,
    public isInsurance?: string,
    public insuranceCompanyAndPolicyNumber?: string,
    public isInternshipAgreement?: boolean,
    public annex?: string,
    public createTime?: Moment,
    public modifyTime?: Moment,
    public stuId?: IStudent
  ) {
    this.isInternshipAgreement = this.isInternshipAgreement || false;
  }
}
