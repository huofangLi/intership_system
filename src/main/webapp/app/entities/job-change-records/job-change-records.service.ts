import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IJobChangeRecords } from 'app/shared/model/job-change-records.model';

type EntityResponseType = HttpResponse<IJobChangeRecords>;
type EntityArrayResponseType = HttpResponse<IJobChangeRecords[]>;

@Injectable({ providedIn: 'root' })
export class JobChangeRecordsService {
  public resourceUrl = SERVER_API_URL + 'api/job-change-records';

  constructor(protected http: HttpClient) {}

  create(jobChangeRecords: IJobChangeRecords): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jobChangeRecords);
    return this.http
      .post<IJobChangeRecords>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(jobChangeRecords: IJobChangeRecords): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jobChangeRecords);
    return this.http
      .put<IJobChangeRecords>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IJobChangeRecords>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IJobChangeRecords[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(jobChangeRecords: IJobChangeRecords): IJobChangeRecords {
    const copy: IJobChangeRecords = Object.assign({}, jobChangeRecords, {
      createTime: jobChangeRecords.createTime != null && jobChangeRecords.createTime.isValid() ? jobChangeRecords.createTime.toJSON() : null
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
      res.body.forEach((jobChangeRecords: IJobChangeRecords) => {
        jobChangeRecords.createTime = jobChangeRecords.createTime != null ? moment(jobChangeRecords.createTime) : null;
      });
    }
    return res;
  }
}
