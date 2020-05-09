import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AlarmEvent } from 'app/shared/model/alarm-event.model';
import { AlarmEventService } from './alarm-event.service';
import { AlarmEventComponent } from './alarm-event.component';
import { AlarmEventDetailComponent } from './alarm-event-detail.component';
import { AlarmEventUpdateComponent } from './alarm-event-update.component';
import { AlarmEventDeletePopupComponent } from './alarm-event-delete-dialog.component';
import { IAlarmEvent } from 'app/shared/model/alarm-event.model';

@Injectable({ providedIn: 'root' })
export class AlarmEventResolve implements Resolve<IAlarmEvent> {
  constructor(private service: AlarmEventService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAlarmEvent> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AlarmEvent>) => response.ok),
        map((alarmEvent: HttpResponse<AlarmEvent>) => alarmEvent.body)
      );
    }
    return of(new AlarmEvent());
  }
}

export const alarmEventRoute: Routes = [
  {
    path: '',
    component: AlarmEventComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'intershipSystemApp.alarmEvent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AlarmEventDetailComponent,
    resolve: {
      alarmEvent: AlarmEventResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.alarmEvent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AlarmEventUpdateComponent,
    resolve: {
      alarmEvent: AlarmEventResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.alarmEvent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AlarmEventUpdateComponent,
    resolve: {
      alarmEvent: AlarmEventResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.alarmEvent.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const alarmEventPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AlarmEventDeletePopupComponent,
    resolve: {
      alarmEvent: AlarmEventResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'intershipSystemApp.alarmEvent.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
