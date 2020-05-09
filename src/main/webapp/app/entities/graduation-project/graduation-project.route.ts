import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { GraduationProject } from 'app/shared/model/graduation-project.model';
import { GraduationProjectService } from './graduation-project.service';
import { GraduationProjectComponent } from './graduation-project.component';
import { GraduationProjectDetailComponent } from './graduation-project-detail.component';
import { GraduationProjectUpdateComponent } from './graduation-project-update.component';
import { GraduationProjectDeletePopupComponent } from './graduation-project-delete-dialog.component';
import { IGraduationProject } from 'app/shared/model/graduation-project.model';

@Injectable({ providedIn: 'root' })
export class GraduationProjectResolve implements Resolve<IGraduationProject> {
  constructor(private service: GraduationProjectService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IGraduationProject> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<GraduationProject>) => response.ok),
        map((graduationProject: HttpResponse<GraduationProject>) => graduationProject.body)
      );
    }
    return of(new GraduationProject());
  }
}

export const graduationProjectRoute: Routes = [
  {
    path: '',
    component: GraduationProjectComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'intershipSystemApp.graduationProject.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GraduationProjectDetailComponent,
    resolve: {
      graduationProject: GraduationProjectResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.graduationProject.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GraduationProjectUpdateComponent,
    resolve: {
      graduationProject: GraduationProjectResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.graduationProject.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GraduationProjectUpdateComponent,
    resolve: {
      graduationProject: GraduationProjectResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.graduationProject.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const graduationProjectPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: GraduationProjectDeletePopupComponent,
    resolve: {
      graduationProject: GraduationProjectResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.graduationProject.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
