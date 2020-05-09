import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AttendanceRecord } from 'app/shared/model/attendance-record.model';
import { AttendanceRecordService } from './attendance-record.service';
import { AttendanceRecordComponent } from './attendance-record.component';
import { AttendanceRecordDetailComponent } from './attendance-record-detail.component';
import { AttendanceRecordUpdateComponent } from './attendance-record-update.component';
import { AttendanceRecordDeletePopupComponent } from './attendance-record-delete-dialog.component';
import { IAttendanceRecord } from 'app/shared/model/attendance-record.model';

@Injectable({ providedIn: 'root' })
export class AttendanceRecordResolve implements Resolve<IAttendanceRecord> {
  constructor(private service: AttendanceRecordService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAttendanceRecord> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AttendanceRecord>) => response.ok),
        map((attendanceRecord: HttpResponse<AttendanceRecord>) => attendanceRecord.body)
      );
    }
    return of(new AttendanceRecord());
  }
}

export const attendanceRecordRoute: Routes = [
  {
    path: '',
    component: AttendanceRecordComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'intershipSystemApp.attendanceRecord.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AttendanceRecordDetailComponent,
    resolve: {
      attendanceRecord: AttendanceRecordResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.attendanceRecord.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AttendanceRecordUpdateComponent,
    resolve: {
      attendanceRecord: AttendanceRecordResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.attendanceRecord.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AttendanceRecordUpdateComponent,
    resolve: {
      attendanceRecord: AttendanceRecordResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.attendanceRecord.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const attendanceRecordPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AttendanceRecordDeletePopupComponent,
    resolve: {
      attendanceRecord: AttendanceRecordResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.attendanceRecord.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
