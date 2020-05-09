import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStuTea } from 'app/shared/model/stu-tea.model';

type EntityResponseType = HttpResponse<IStuTea>;
type EntityArrayResponseType = HttpResponse<IStuTea[]>;

@Injectable({ providedIn: 'root' })
export class StuTeaService {
  public resourceUrl = SERVER_API_URL + 'api/stu-teas';

  constructor(protected http: HttpClient) {}

  create(stuTea: IStuTea): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stuTea);
    return this.http
      .post<IStuTea>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(stuTea: IStuTea): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stuTea);
    return this.http
      .put<IStuTea>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IStuTea>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStuTea[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(stuTea: IStuTea): IStuTea {
    const copy: IStuTea = Object.assign({}, stuTea, {
      createTime: stuTea.createTime != null && stuTea.createTime.isValid() ? stuTea.createTime.toJSON() : null
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
      res.body.forEach((stuTea: IStuTea) => {
        stuTea.createTime = stuTea.createTime != null ? moment(stuTea.createTime) : null;
      });
    }
    return res;
  }
}
