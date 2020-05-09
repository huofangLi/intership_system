import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { InternshipReport } from 'app/shared/model/internship-report.model';
import { InternshipReportService } from './internship-report.service';
import { InternshipReportComponent } from './internship-report.component';
import { InternshipReportDetailComponent } from './internship-report-detail.component';
import { InternshipReportUpdateComponent } from './internship-report-update.component';
import { InternshipReportDeletePopupComponent } from './internship-report-delete-dialog.component';
import { IInternshipReport } from 'app/shared/model/internship-report.model';

@Injectable({ providedIn: 'root' })
export class InternshipReportResolve implements Resolve<IInternshipReport> {
  constructor(private service: InternshipReportService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IInternshipReport> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<InternshipReport>) => response.ok),
        map((internshipReport: HttpResponse<InternshipReport>) => internshipReport.body)
      );
    }
    return of(new InternshipReport());
  }
}

export const internshipReportRoute: Routes = [
  {
    path: '',
    component: InternshipReportComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'intershipSystemApp.internshipReport.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InternshipReportDetailComponent,
    resolve: {
      internshipReport: InternshipReportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.internshipReport.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InternshipReportUpdateComponent,
    resolve: {
      internshipReport: InternshipReportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.internshipReport.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InternshipReportUpdateComponent,
    resolve: {
      internshipReport: InternshipReportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.internshipReport.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const internshipReportPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: InternshipReportDeletePopupComponent,
    resolve: {
      internshipReport: InternshipReportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.internshipReport.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
