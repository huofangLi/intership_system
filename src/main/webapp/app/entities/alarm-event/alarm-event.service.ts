import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAlarmEvent } from 'app/shared/model/alarm-event.model';

type EntityResponseType = HttpResponse<IAlarmEvent>;
type EntityArrayResponseType = HttpResponse<IAlarmEvent[]>;

@Injectable({ providedIn: 'root' })
export class AlarmEventService {
  public resourceUrl = SERVER_API_URL + 'api/alarm-events';

  constructor(protected http: HttpClient) {}

  create(alarmEvent: IAlarmEvent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(alarmEvent);
    return this.http
      .post<IAlarmEvent>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(alarmEvent: IAlarmEvent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(alarmEvent);
    return this.http
      .put<IAlarmEvent>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAlarmEvent>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAlarmEvent[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(alarmEvent: IAlarmEvent): IAlarmEvent {
    const copy: IAlarmEvent = Object.assign({}, alarmEvent, {
      createdTime: alarmEvent.createdTime != null && alarmEvent.createdTime.isValid() ? alarmEvent.createdTime.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdTime = res.body.createdTime != null ? moment(res.body.createdTime) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((alarmEvent: IAlarmEvent) => {
        alarmEvent.createdTime = alarmEvent.createdTime != null ? moment(alarmEvent.createdTime) : null;
      });
    }
    return res;
  }
}
