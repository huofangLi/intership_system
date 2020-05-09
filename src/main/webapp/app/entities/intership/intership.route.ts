import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Intership } from 'app/shared/model/intership.model';
import { IntershipService } from './intership.service';
import { IntershipComponent } from './intership.component';
import { IntershipDetailComponent } from './intership-detail.component';
import { IntershipUpdateComponent } from './intership-update.component';
import { IntershipDeletePopupComponent } from './intership-delete-dialog.component';
import { IIntership } from 'app/shared/model/intership.model';

@Injectable({ providedIn: 'root' })
export class IntershipResolve implements Resolve<IIntership> {
  constructor(private service: IntershipService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IIntership> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Intership>) => response.ok),
        map((intership: HttpResponse<Intership>) => intership.body)
      );
    }
    return of(new Intership());
  }
}

export const intershipRoute: Routes = [
  {
    path: '',
    component: IntershipComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'intershipSystemApp.intership.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: IntershipDetailComponent,
    resolve: {
      intership: IntershipResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.intership.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: IntershipUpdateComponent,
    resolve: {
      intership: IntershipResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.intership.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: IntershipUpdateComponent,
    resolve: {
      intership: IntershipResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.intership.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const intershipPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: IntershipDeletePopupComponent,
    resolve: {
      intership: IntershipResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.intership.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
