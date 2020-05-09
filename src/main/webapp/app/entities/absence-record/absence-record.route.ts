import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AbsenceRecord } from 'app/shared/model/absence-record.model';
import { AbsenceRecordService } from './absence-record.service';
import { AbsenceRecordComponent } from './absence-record.component';
import { AbsenceRecordDetailComponent } from './absence-record-detail.component';
import { AbsenceRecordUpdateComponent } from './absence-record-update.component';
import { AbsenceRecordDeletePopupComponent } from './absence-record-delete-dialog.component';
import { IAbsenceRecord } from 'app/shared/model/absence-record.model';

@Injectable({ providedIn: 'root' })
export class AbsenceRecordResolve implements Resolve<IAbsenceRecord> {
  constructor(private service: AbsenceRecordService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAbsenceRecord> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AbsenceRecord>) => response.ok),
        map((absenceRecord: HttpResponse<AbsenceRecord>) => absenceRecord.body)
      );
    }
    return of(new AbsenceRecord());
  }
}

export const absenceRecordRoute: Routes = [
  {
    path: '',
    component: AbsenceRecordComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'intershipSystemApp.absenceRecord.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AbsenceRecordDetailComponent,
    resolve: {
      absenceRecord: AbsenceRecordResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.absenceRecord.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AbsenceRecordUpdateComponent,
    resolve: {
      absenceRecord: AbsenceRecordResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.absenceRecord.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AbsenceRecordUpdateComponent,
    resolve: {
      absenceRecord: AbsenceRecordResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.absenceRecord.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const absenceRecordPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AbsenceRecordDeletePopupComponent,
    resolve: {
      absenceRecord: AbsenceRecordResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.absenceRecord.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
