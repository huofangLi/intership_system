import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { IntershipSystemSharedModule } from 'app/shared';
import {
  InternshipReportComponent,
  InternshipReportDetailComponent,
  InternshipReportUpdateComponent,
  InternshipReportDeletePopupComponent,
  InternshipReportDeleteDialogComponent,
  internshipReportRoute,
  internshipReportPopupRoute
} from './';

const ENTITY_STATES = [...internshipReportRoute, ...internshipReportPopupRoute];

@NgModule({
  imports: [IntershipSystemSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    InternshipReportComponent,
    InternshipReportDetailComponent,
    InternshipReportUpdateComponent,
    InternshipReportDeleteDialogComponent,
    InternshipReportDeletePopupComponent
  ],
  entryComponents: [
    InternshipReportComponent,
    InternshipReportUpdateComponent,
    InternshipReportDeleteDialogComponent,
    InternshipReportDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IntershipSystemInternshipReportModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
