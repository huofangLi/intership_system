import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SharingCenter } from 'app/shared/model/sharing-center.model';
import { SharingCenterService } from './sharing-center.service';
import { SharingCenterComponent } from './sharing-center.component';
import { SharingCenterDetailComponent } from './sharing-center-detail.component';
import { SharingCenterUpdateComponent } from './sharing-center-update.component';
import { SharingCenterDeletePopupComponent } from './sharing-center-delete-dialog.component';
import { ISharingCenter } from 'app/shared/model/sharing-center.model';

@Injectable({ providedIn: 'root' })
export class SharingCenterResolve implements Resolve<ISharingCenter> {
  constructor(private service: SharingCenterService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISharingCenter> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SharingCenter>) => response.ok),
        map((sharingCenter: HttpResponse<SharingCenter>) => sharingCenter.body)
      );
    }
    return of(new SharingCenter());
  }
}

export const sharingCenterRoute: Routes = [
  {
    path: '',
    component: SharingCenterComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'intershipSystemApp.sharingCenter.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SharingCenterDetailComponent,
    resolve: {
      sharingCenter: SharingCenterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.sharingCenter.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SharingCenterUpdateComponent,
    resolve: {
      sharingCenter: SharingCenterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.sharingCenter.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SharingCenterUpdateComponent,
    resolve: {
      sharingCenter: SharingCenterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.sharingCenter.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const sharingCenterPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SharingCenterDeletePopupComponent,
    resolve: {
      sharingCenter: SharingCenterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.sharingCenter.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
