import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { IntershipSystemSharedModule } from 'app/shared';
import {
  AbsenceRecordComponent,
  AbsenceRecordDetailComponent,
  AbsenceRecordUpdateComponent,
  AbsenceRecordDeletePopupComponent,
  AbsenceRecordDeleteDialogComponent,
  absenceRecordRoute,
  absenceRecordPopupRoute
} from './';

const ENTITY_STATES = [...absenceRecordRoute, ...absenceRecordPopupRoute];

@NgModule({
  imports: [IntershipSystemSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AbsenceRecordComponent,
    AbsenceRecordDetailComponent,
    AbsenceRecordUpdateComponent,
    AbsenceRecordDeleteDialogComponent,
    AbsenceRecordDeletePopupComponent
  ],
  entryComponents: [
    AbsenceRecordComponent,
    AbsenceRecordUpdateComponent,
    AbsenceRecordDeleteDialogComponent,
    AbsenceRecordDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IntershipSystemAbsenceRecordModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
