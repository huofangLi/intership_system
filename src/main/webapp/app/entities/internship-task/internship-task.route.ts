import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { InternshipTask } from 'app/shared/model/internship-task.model';
import { InternshipTaskService } from './internship-task.service';
import { InternshipTaskComponent } from './internship-task.component';
import { InternshipTaskDetailComponent } from './internship-task-detail.component';
import { InternshipTaskUpdateComponent } from './internship-task-update.component';
import { InternshipTaskDeletePopupComponent } from './internship-task-delete-dialog.component';
import { IInternshipTask } from 'app/shared/model/internship-task.model';

@Injectable({ providedIn: 'root' })
export class InternshipTaskResolve implements Resolve<IInternshipTask> {
  constructor(private service: InternshipTaskService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IInternshipTask> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<InternshipTask>) => response.ok),
        map((internshipTask: HttpResponse<InternshipTask>) => internshipTask.body)
      );
    }
    return of(new InternshipTask());
  }
}

export const internshipTaskRoute: Routes = [
  {
    path: '',
    component: InternshipTaskComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'intershipSystemApp.internshipTask.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InternshipTaskDetailComponent,
    resolve: {
      internshipTask: InternshipTaskResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.internshipTask.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InternshipTaskUpdateComponent,
    resolve: {
      internshipTask: InternshipTaskResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.internshipTask.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InternshipTaskUpdateComponent,
    resolve: {
      internshipTask: InternshipTaskResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.internshipTask.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const internshipTaskPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: InternshipTaskDeletePopupComponent,
    resolve: {
      internshipTask: InternshipTaskResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.internshipTask.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
