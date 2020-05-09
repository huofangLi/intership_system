import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { IntershipSystemSharedModule } from 'app/shared';
import {
  AttendanceRecordComponent,
  AttendanceRecordDetailComponent,
  AttendanceRecordUpdateComponent,
  AttendanceRecordDeletePopupComponent,
  AttendanceRecordDeleteDialogComponent,
  attendanceRecordRoute,
  attendanceRecordPopupRoute
} from './';

const ENTITY_STATES = [...attendanceRecordRoute, ...attendanceRecordPopupRoute];

@NgModule({
  imports: [IntershipSystemSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AttendanceRecordComponent,
    AttendanceRecordDetailComponent,
    AttendanceRecordUpdateComponent,
    AttendanceRecordDeleteDialogComponent,
    AttendanceRecordDeletePopupComponent
  ],
  entryComponents: [
    AttendanceRecordComponent,
    AttendanceRecordUpdateComponent,
    AttendanceRecordDeleteDialogComponent,
    AttendanceRecordDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IntershipSystemAttendanceRecordModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
