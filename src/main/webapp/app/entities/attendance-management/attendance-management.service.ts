import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAttendanceManagement } from 'app/shared/model/attendance-management.model';

type EntityResponseType = HttpResponse<IAttendanceManagement>;
type EntityArrayResponseType = HttpResponse<IAttendanceManagement[]>;

@Injectable({ providedIn: 'root' })
export class AttendanceManagementService {
  public resourceUrl = SERVER_API_URL + 'api/attendance-managements';

  constructor(protected http: HttpClient) {}

  create(attendanceManagement: IAttendanceManagement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(attendanceManagement);
    return this.http
      .post<IAttendanceManagement>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(attendanceManagement: IAttendanceManagement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(attendanceManagement);
    return this.http
      .put<IAttendanceManagement>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAttendanceManagement>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAttendanceManagement[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(attendanceManagement: IAttendanceManagement): IAttendanceManagement {
    const copy: IAttendanceManagement = Object.assign({}, attendanceManagement, {
      signIn: attendanceManagement.signIn != null && attendanceManagement.signIn.isValid() ? attendanceManagement.signIn.toJSON() : null,
      createdTime:
        attendanceManagement.createdTime != null && attendanceManagement.createdTime.isValid()
          ? attendanceManagement.createdTime.toJSON()
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.signIn = res.body.signIn != null ? moment(res.body.signIn) : null;
      res.body.createdTime = res.body.createdTime != null ? moment(res.body.createdTime) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((attendanceManagement: IAttendanceManagement) => {
        attendanceManagement.signIn = attendanceManagement.signIn != null ? moment(attendanceManagement.signIn) : null;
        attendanceManagement.createdTime = attendanceManagement.createdTime != null ? moment(attendanceManagement.createdTime) : null;
      });
    }
    return res;
  }
}
