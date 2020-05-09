import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAttendanceRecord } from 'app/shared/model/attendance-record.model';

type EntityResponseType = HttpResponse<IAttendanceRecord>;
type EntityArrayResponseType = HttpResponse<IAttendanceRecord[]>;

@Injectable({ providedIn: 'root' })
export class AttendanceRecordService {
  public resourceUrl = SERVER_API_URL + 'api/attendance-records';

  constructor(protected http: HttpClient) {}

  create(attendanceRecord: IAttendanceRecord): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(attendanceRecord);
    return this.http
      .post<IAttendanceRecord>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(attendanceRecord: IAttendanceRecord): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(attendanceRecord);
    return this.http
      .put<IAttendanceRecord>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAttendanceRecord>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAttendanceRecord[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(attendanceRecord: IAttendanceRecord): IAttendanceRecord {
    const copy: IAttendanceRecord = Object.assign({}, attendanceRecord, {
      punchTime: attendanceRecord.punchTime != null && attendanceRecord.punchTime.isValid() ? attendanceRecord.punchTime.toJSON() : null,
      createdTime:
        attendanceRecord.createdTime != null && attendanceRecord.createdTime.isValid() ? attendanceRecord.createdTime.toJSON() : null,
      modifyTime: attendanceRecord.modifyTime != null && attendanceRecord.modifyTime.isValid() ? attendanceRecord.modifyTime.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.punchTime = res.body.punchTime != null ? moment(res.body.punchTime) : null;
      res.body.createdTime = res.body.createdTime != null ? moment(res.body.createdTime) : null;
      res.body.modifyTime = res.body.modifyTime != null ? moment(res.body.modifyTime) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((attendanceRecord: IAttendanceRecord) => {
        attendanceRecord.punchTime = attendanceRecord.punchTime != null ? moment(attendanceRecord.punchTime) : null;
        attendanceRecord.createdTime = attendanceRecord.createdTime != null ? moment(attendanceRecord.createdTime) : null;
        attendanceRecord.modifyTime = attendanceRecord.modifyTime != null ? moment(attendanceRecord.modifyTime) : null;
      });
    }
    return res;
  }
}
