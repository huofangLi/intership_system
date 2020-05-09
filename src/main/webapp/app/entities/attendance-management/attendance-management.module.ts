import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { IntershipSystemSharedModule } from 'app/shared';
import {
  AttendanceManagementComponent,
  AttendanceManagementDetailComponent,
  AttendanceManagementUpdateComponent,
  AttendanceManagementDeletePopupComponent,
  AttendanceManagementDeleteDialogComponent,
  attendanceManagementRoute,
  attendanceManagementPopupRoute
} from './';

const ENTITY_STATES = [...attendanceManagementRoute, ...attendanceManagementPopupRoute];

@NgModule({
  imports: [IntershipSystemSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AttendanceManagementComponent,
    AttendanceManagementDetailComponent,
    AttendanceManagementUpdateComponent,
    AttendanceManagementDeleteDialogComponent,
    AttendanceManagementDeletePopupComponent
  ],
  entryComponents: [
    AttendanceManagementComponent,
    AttendanceManagementUpdateComponent,
    AttendanceManagementDeleteDialogComponent,
    AttendanceManagementDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IntershipSystemAttendanceManagementModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
