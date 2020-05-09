import { Moment } from 'moment';

export interface ICertificate {
  id?: number;
  certificateName?: string;
  certificateType?: string;
  certificateLevel?: string;
  certificateAcquisitionTime?: Moment;
  certificatePhoto?: string;
  createdTime?: Moment;
  modifyTime?: Moment;
}

export class Certificate implements ICertificate {
  constructor(
    public id?: number,
    public certificateName?: string,
    public certificateType?: string,
    public certificateLevel?: string,
    public certificateAcquisitionTime?: Moment,
    public certificatePhoto?: string,
    public createdTime?: Moment,
    public modifyTime?: Moment
  ) {}
}
