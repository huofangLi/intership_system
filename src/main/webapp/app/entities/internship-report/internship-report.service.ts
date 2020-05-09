import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInternshipReport } from 'app/shared/model/internship-report.model';

type EntityResponseType = HttpResponse<IInternshipReport>;
type EntityArrayResponseType = HttpResponse<IInternshipReport[]>;

@Injectable({ providedIn: 'root' })
export class InternshipReportService {
  public resourceUrl = SERVER_API_URL + 'api/internship-reports';

  constructor(protected http: HttpClient) {}

  create(internshipReport: IInternshipReport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(internshipReport);
    return this.http
      .post<IInternshipReport>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(internshipReport: IInternshipReport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(internshipReport);
    return this.http
      .put<IInternshipReport>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInternshipReport>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInternshipReport[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(internshipReport: IInternshipReport): IInternshipReport {
    const copy: IInternshipReport = Object.assign({}, internshipReport, {
      createTime: internshipReport.createTime != null && internshipReport.createTime.isValid() ? internshipReport.createTime.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createTime = res.body.createTime != null ? moment(res.body.createTime) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((internshipReport: IInternshipReport) => {
        internshipReport.createTime = internshipReport.createTime != null ? moment(internshipReport.createTime) : null;
      });
    }
    return res;
  }
}
