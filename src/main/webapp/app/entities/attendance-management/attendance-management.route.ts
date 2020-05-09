import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AttendanceManagement } from 'app/shared/model/attendance-management.model';
import { AttendanceManagementService } from './attendance-management.service';
import { AttendanceManagementComponent } from './attendance-management.component';
import { AttendanceManagementDetailComponent } from './attendance-management-detail.component';
import { AttendanceManagementUpdateComponent } from './attendance-management-update.component';
import { AttendanceManagementDeletePopupComponent } from './attendance-management-delete-dialog.component';
import { IAttendanceManagement } from 'app/shared/model/attendance-management.model';

@Injectable({ providedIn: 'root' })
export class AttendanceManagementResolve implements Resolve<IAttendanceManagement> {
  constructor(private service: AttendanceManagementService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAttendanceManagement> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AttendanceManagement>) => response.ok),
        map((attendanceManagement: HttpResponse<AttendanceManagement>) => attendanceManagement.body)
      );
    }
    return of(new AttendanceManagement());
  }
}

export const attendanceManagementRoute: Routes = [
  {
    path: '',
    component: AttendanceManagementComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'intershipSystemApp.attendanceManagement.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AttendanceManagementDetailComponent,
    resolve: {
      attendanceManagement: AttendanceManagementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.attendanceManagement.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AttendanceManagementUpdateComponent,
    resolve: {
      attendanceManagement: AttendanceManagementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.attendanceManagement.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AttendanceManagementUpdateComponent,
    resolve: {
      attendanceManagement: AttendanceManagementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.attendanceManagement.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const attendanceManagementPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AttendanceManagementDeletePopupComponent,
    resolve: {
      attendanceManagement: AttendanceManagementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.attendanceManagement.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
