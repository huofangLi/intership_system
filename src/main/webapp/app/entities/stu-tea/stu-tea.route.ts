import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StuTea } from 'app/shared/model/stu-tea.model';
import { StuTeaService } from './stu-tea.service';
import { StuTeaComponent } from './stu-tea.component';
import { StuTeaDetailComponent } from './stu-tea-detail.component';
import { StuTeaUpdateComponent } from './stu-tea-update.component';
import { StuTeaDeletePopupComponent } from './stu-tea-delete-dialog.component';
import { IStuTea } from 'app/shared/model/stu-tea.model';

@Injectable({ providedIn: 'root' })
export class StuTeaResolve implements Resolve<IStuTea> {
  constructor(private service: StuTeaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStuTea> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<StuTea>) => response.ok),
        map((stuTea: HttpResponse<StuTea>) => stuTea.body)
      );
    }
    return of(new StuTea());
  }
}

export const stuTeaRoute: Routes = [
  {
    path: '',
    component: StuTeaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'intershipSystemApp.stuTea.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: StuTeaDetailComponent,
    resolve: {
      stuTea: StuTeaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.stuTea.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: StuTeaUpdateComponent,
    resolve: {
      stuTea: StuTeaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.stuTea.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: StuTeaUpdateComponent,
    resolve: {
      stuTea: StuTeaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.stuTea.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const stuTeaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: StuTeaDeletePopupComponent,
    resolve: {
      stuTea: StuTeaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.stuTea.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
