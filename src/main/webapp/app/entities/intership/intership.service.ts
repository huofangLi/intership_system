import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IIntership } from 'app/shared/model/intership.model';

type EntityResponseType = HttpResponse<IIntership>;
type EntityArrayResponseType = HttpResponse<IIntership[]>;

@Injectable({ providedIn: 'root' })
export class IntershipService {
  public resourceUrl = SERVER_API_URL + 'api/interships';

  constructor(protected http: HttpClient) {}

  create(intership: IIntership): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(intership);
    return this.http
      .post<IIntership>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(intership: IIntership): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(intership);
    return this.http
      .put<IIntership>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IIntership>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IIntership[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(intership: IIntership): IIntership {
    const copy: IIntership = Object.assign({}, intership, {
      beginIntership: intership.beginIntership != null && intership.beginIntership.isValid() ? intership.beginIntership.toJSON() : null,
      endIntership: intership.endIntership != null && intership.endIntership.isValid() ? intership.endIntership.toJSON() : null,
      createTime: intership.createTime != null && intership.createTime.isValid() ? intership.createTime.toJSON() : null,
      modifyTime: intership.modifyTime != null && intership.modifyTime.isValid() ? intership.modifyTime.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.beginIntership = res.body.beginIntership != null ? moment(res.body.beginIntership) : null;
      res.body.endIntership = res.body.endIntership != null ? moment(res.body.endIntership) : null;
      res.body.createTime = res.body.createTime != null ? moment(res.body.createTime) : null;
      res.body.modifyTime = res.body.modifyTime != null ? moment(res.body.modifyTime) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((intership: IIntership) => {
        intership.beginIntership = intership.beginIntership != null ? moment(intership.beginIntership) : null;
        intership.endIntership = intership.endIntership != null ? moment(intership.endIntership) : null;
        intership.createTime = intership.createTime != null ? moment(intership.createTime) : null;
        intership.modifyTime = intership.modifyTime != null ? moment(intership.modifyTime) : null;
      });
    }
    return res;
  }
}
