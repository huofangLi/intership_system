import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGraduationProject } from 'app/shared/model/graduation-project.model';

type EntityResponseType = HttpResponse<IGraduationProject>;
type EntityArrayResponseType = HttpResponse<IGraduationProject[]>;

@Injectable({ providedIn: 'root' })
export class GraduationProjectService {
  public resourceUrl = SERVER_API_URL + 'api/graduation-projects';

  constructor(protected http: HttpClient) {}

  create(graduationProject: IGraduationProject): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(graduationProject);
    return this.http
      .post<IGraduationProject>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(graduationProject: IGraduationProject): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(graduationProject);
    return this.http
      .put<IGraduationProject>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IGraduationProject>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IGraduationProject[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(graduationProject: IGraduationProject): IGraduationProject {
    const copy: IGraduationProject = Object.assign({}, graduationProject, {
      createdTime:
        graduationProject.createdTime != null && graduationProject.createdTime.isValid() ? graduationProject.createdTime.toJSON() : null,
      modifyTime:
        graduationProject.modifyTime != null && graduationProject.modifyTime.isValid() ? graduationProject.modifyTime.toJSON() : null
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
      res.body.forEach((graduationProject: IGraduationProject) => {
        graduationProject.createdTime = graduationProject.createdTime != null ? moment(graduationProject.createdTime) : null;
        graduationProject.modifyTime = graduationProject.modifyTime != null ? moment(graduationProject.modifyTime) : null;
      });
    }
    return res;
  }
}
