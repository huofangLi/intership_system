import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAbsenceRecord } from 'app/shared/model/absence-record.model';

type EntityResponseType = HttpResponse<IAbsenceRecord>;
type EntityArrayResponseType = HttpResponse<IAbsenceRecord[]>;

@Injectable({ providedIn: 'root' })
export class AbsenceRecordService {
  public resourceUrl = SERVER_API_URL + 'api/absence-records';

  constructor(protected http: HttpClient) {}

  create(absenceRecord: IAbsenceRecord): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(absenceRecord);
    return this.http
      .post<IAbsenceRecord>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(absenceRecord: IAbsenceRecord): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(absenceRecord);
    return this.http
      .put<IAbsenceRecord>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAbsenceRecord>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAbsenceRecord[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(absenceRecord: IAbsenceRecord): IAbsenceRecord {
    const copy: IAbsenceRecord = Object.assign({}, absenceRecord, {
      absenceStartTime:
        absenceRecord.absenceStartTime != null && absenceRecord.absenceStartTime.isValid() ? absenceRecord.absenceStartTime.toJSON() : null,
      absenceEndTime:
        absenceRecord.absenceEndTime != null && absenceRecord.absenceEndTime.isValid() ? absenceRecord.absenceEndTime.toJSON() : null,
      createdTime: absenceRecord.createdTime != null && absenceRecord.createdTime.isValid() ? absenceRecord.createdTime.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.absenceStartTime = res.body.absenceStartTime != null ? moment(res.body.absenceStartTime) : null;
      res.body.absenceEndTime = res.body.absenceEndTime != null ? moment(res.body.absenceEndTime) : null;
      res.body.createdTime = res.body.createdTime != null ? moment(res.body.createdTime) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((absenceRecord: IAbsenceRecord) => {
        absenceRecord.absenceStartTime = absenceRecord.absenceStartTime != null ? moment(absenceRecord.absenceStartTime) : null;
        absenceRecord.absenceEndTime = absenceRecord.absenceEndTime != null ? moment(absenceRecord.absenceEndTime) : null;
        absenceRecord.createdTime = absenceRecord.createdTime != null ? moment(absenceRecord.createdTime) : null;
      });
    }
    return res;
  }
}
