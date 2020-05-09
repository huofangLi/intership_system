import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JobChangeRecords } from 'app/shared/model/job-change-records.model';
import { JobChangeRecordsService } from './job-change-records.service';
import { JobChangeRecordsComponent } from './job-change-records.component';
import { JobChangeRecordsDetailComponent } from './job-change-records-detail.component';
import { JobChangeRecordsUpdateComponent } from './job-change-records-update.component';
import { JobChangeRecordsDeletePopupComponent } from './job-change-records-delete-dialog.component';
import { IJobChangeRecords } from 'app/shared/model/job-change-records.model';

@Injectable({ providedIn: 'root' })
export class JobChangeRecordsResolve implements Resolve<IJobChangeRecords> {
  constructor(private service: JobChangeRecordsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IJobChangeRecords> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<JobChangeRecords>) => response.ok),
        map((jobChangeRecords: HttpResponse<JobChangeRecords>) => jobChangeRecords.body)
      );
    }
    return of(new JobChangeRecords());
  }
}

export const jobChangeRecordsRoute: Routes = [
  {
    path: '',
    component: JobChangeRecordsComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'intershipSystemApp.jobChangeRecords.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: JobChangeRecordsDetailComponent,
    resolve: {
      jobChangeRecords: JobChangeRecordsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.jobChangeRecords.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: JobChangeRecordsUpdateComponent,
    resolve: {
      jobChangeRecords: JobChangeRecordsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.jobChangeRecords.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: JobChangeRecordsUpdateComponent,
    resolve: {
      jobChangeRecords: JobChangeRecordsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.jobChangeRecords.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const jobChangeRecordsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: JobChangeRecordsDeletePopupComponent,
    resolve: {
      jobChangeRecords: JobChangeRecordsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.jobChangeRecords.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
