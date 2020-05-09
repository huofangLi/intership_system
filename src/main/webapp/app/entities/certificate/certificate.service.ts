import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICertificate } from 'app/shared/model/certificate.model';

type EntityResponseType = HttpResponse<ICertificate>;
type EntityArrayResponseType = HttpResponse<ICertificate[]>;

@Injectable({ providedIn: 'root' })
export class CertificateService {
  public resourceUrl = SERVER_API_URL + 'api/certificates';

  constructor(protected http: HttpClient) {}

  create(certificate: ICertificate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(certificate);
    return this.http
      .post<ICertificate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(certificate: ICertificate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(certificate);
    return this.http
      .put<ICertificate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICertificate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICertificate[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(certificate: ICertificate): ICertificate {
    const copy: ICertificate = Object.assign({}, certificate, {
      certificateAcquisitionTime:
        certificate.certificateAcquisitionTime != null && certificate.certificateAcquisitionTime.isValid()
          ? certificate.certificateAcquisitionTime.toJSON()
          : null,
      createdTime: certificate.createdTime != null && certificate.createdTime.isValid() ? certificate.createdTime.toJSON() : null,
      modifyTime: certificate.modifyTime != null && certificate.modifyTime.isValid() ? certificate.modifyTime.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.certificateAcquisitionTime =
        res.body.certificateAcquisitionTime != null ? moment(res.body.certificateAcquisitionTime) : null;
      res.body.createdTime = res.body.createdTime != null ? moment(res.body.createdTime) : null;
      res.body.modifyTime = res.body.modifyTime != null ? moment(res.body.modifyTime) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((certificate: ICertificate) => {
        certificate.certificateAcquisitionTime =
          certificate.certificateAcquisitionTime != null ? moment(certificate.certificateAcquisitionTime) : null;
        certificate.createdTime = certificate.createdTime != null ? moment(certificate.createdTime) : null;
        certificate.modifyTime = certificate.modifyTime != null ? moment(certificate.modifyTime) : null;
      });
    }
    return res;
  }
}
