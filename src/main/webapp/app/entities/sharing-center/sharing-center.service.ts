import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISharingCenter } from 'app/shared/model/sharing-center.model';

type EntityResponseType = HttpResponse<ISharingCenter>;
type EntityArrayResponseType = HttpResponse<ISharingCenter[]>;

@Injectable({ providedIn: 'root' })
export class SharingCenterService {
  public resourceUrl = SERVER_API_URL + 'api/sharing-centers';

  constructor(protected http: HttpClient) {}

  create(sharingCenter: ISharingCenter): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sharingCenter);
    return this.http
      .post<ISharingCenter>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(sharingCenter: ISharingCenter): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sharingCenter);
    return this.http
      .put<ISharingCenter>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISharingCenter>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISharingCenter[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(sharingCenter: ISharingCenter): ISharingCenter {
    const copy: ISharingCenter = Object.assign({}, sharingCenter, {
      createdTime: sharingCenter.createdTime != null && sharingCenter.createdTime.isValid() ? sharingCenter.createdTime.toJSON() : null,
      modifyTime: sharingCenter.modifyTime != null && sharingCenter.modifyTime.isValid() ? sharingCenter.modifyTime.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdTime = res.body.createdTime != null ? moment(res.body.createdTime) : null;
      res.body.modifyTime = res.body.modifyTime != null ? moment(res.body.modifyTime) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((sharingCenter: ISharingCenter) => {
        sharingCenter.createdTime = sharingCenter.createdTime != null ? moment(sharingCenter.createdTime) : null;
        sharingCenter.modifyTime = sharingCenter.modifyTime != null ? moment(sharingCenter.modifyTime) : null;
      });
    }
    return res;
  }
}
