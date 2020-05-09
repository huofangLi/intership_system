import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { IntershipSystemSharedModule } from 'app/shared';
import {
  JobChangeRecordsComponent,
  JobChangeRecordsDetailComponent,
  JobChangeRecordsUpdateComponent,
  JobChangeRecordsDeletePopupComponent,
  JobChangeRecordsDeleteDialogComponent,
  jobChangeRecordsRoute,
  jobChangeRecordsPopupRoute
} from './';

const ENTITY_STATES = [...jobChangeRecordsRoute, ...jobChangeRecordsPopupRoute];

@NgModule({
  imports: [IntershipSystemSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    JobChangeRecordsComponent,
    JobChangeRecordsDetailComponent,
    JobChangeRecordsUpdateComponent,
    JobChangeRecordsDeleteDialogComponent,
    JobChangeRecordsDeletePopupComponent
  ],
  entryComponents: [
    JobChangeRecordsComponent,
    JobChangeRecordsUpdateComponent,
    JobChangeRecordsDeleteDialogComponent,
    JobChangeRecordsDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IntershipSystemJobChangeRecordsModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
