import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInternshipTask } from 'app/shared/model/internship-task.model';

type EntityResponseType = HttpResponse<IInternshipTask>;
type EntityArrayResponseType = HttpResponse<IInternshipTask[]>;

@Injectable({ providedIn: 'root' })
export class InternshipTaskService {
  public resourceUrl = SERVER_API_URL + 'api/internship-tasks';

  constructor(protected http: HttpClient) {}

  create(internshipTask: IInternshipTask): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(internshipTask);
    return this.http
      .post<IInternshipTask>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(internshipTask: IInternshipTask): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(internshipTask);
    return this.http
      .put<IInternshipTask>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInternshipTask>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInternshipTask[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(internshipTask: IInternshipTask): IInternshipTask {
    const copy: IInternshipTask = Object.assign({}, internshipTask, {
      startTime: internshipTask.startTime != null && internshipTask.startTime.isValid() ? internshipTask.startTime.toJSON() : null,
      endTime: internshipTask.endTime != null && internshipTask.endTime.isValid() ? internshipTask.endTime.toJSON() : null,
      createTime: internshipTask.createTime != null && internshipTask.createTime.isValid() ? internshipTask.createTime.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startTime = res.body.startTime != null ? moment(res.body.startTime) : null;
      res.body.endTime = res.body.endTime != null ? moment(res.body.endTime) : null;
      res.body.createTime = res.body.createTime != null ? moment(res.body.createTime) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((internshipTask: IInternshipTask) => {
        internshipTask.startTime = internshipTask.startTime != null ? moment(internshipTask.startTime) : null;
        internshipTask.endTime = internshipTask.endTime != null ? moment(internshipTask.endTime) : null;
        internshipTask.createTime = internshipTask.createTime != null ? moment(internshipTask.createTime) : null;
      });
    }
    return res;
  }
}
