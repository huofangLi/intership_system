import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILeave } from 'app/shared/model/leave.model';

type EntityResponseType = HttpResponse<ILeave>;
type EntityArrayResponseType = HttpResponse<ILeave[]>;

@Injectable({ providedIn: 'root' })
export class LeaveService {
  public resourceUrl = SERVER_API_URL + 'api/leaves';

  constructor(protected http: HttpClient) {}

  create(leave: ILeave): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(leave);
    return this.http
      .post<ILeave>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(leave: ILeave): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(leave);
    return this.http
      .put<ILeave>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILeave>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILeave[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(leave: ILeave): ILeave {
    const copy: ILeave = Object.assign({}, leave, {
      submitTime: leave.submitTime != null && leave.submitTime.isValid() ? leave.submitTime.toJSON() : null,
      leaveTime: leave.leaveTime != null && leave.leaveTime.isValid() ? leave.leaveTime.toJSON() : null,
      createdTime: leave.createdTime != null && leave.createdTime.isValid() ? leave.createdTime.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.submitTime = res.body.submitTime != null ? moment(res.body.submitTime) : null;
      res.body.leaveTime = res.body.leaveTime != null ? moment(res.body.leaveTime) : null;
      res.body.createdTime = res.body.createdTime != null ? moment(res.body.createdTime) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((leave: ILeave) => {
        leave.submitTime = leave.submitTime != null ? moment(leave.submitTime) : null;
        leave.leaveTime = leave.leaveTime != null ? moment(leave.leaveTime) : null;
        leave.createdTime = leave.createdTime != null ? moment(leave.createdTime) : null;
      });
    }
    return res;
  }
}
