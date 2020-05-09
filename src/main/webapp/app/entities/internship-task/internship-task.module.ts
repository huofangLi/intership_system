import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { IntershipSystemSharedModule } from 'app/shared';
import {
  InternshipTaskComponent,
  InternshipTaskDetailComponent,
  InternshipTaskUpdateComponent,
  InternshipTaskDeletePopupComponent,
  InternshipTaskDeleteDialogComponent,
  internshipTaskRoute,
  internshipTaskPopupRoute
} from './';

const ENTITY_STATES = [...internshipTaskRoute, ...internshipTaskPopupRoute];

@NgModule({
  imports: [IntershipSystemSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    InternshipTaskComponent,
    InternshipTaskDetailComponent,
    InternshipTaskUpdateComponent,
    InternshipTaskDeleteDialogComponent,
    InternshipTaskDeletePopupComponent
  ],
  entryComponents: [
    InternshipTaskComponent,
    InternshipTaskUpdateComponent,
    InternshipTaskDeleteDialogComponent,
    InternshipTaskDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IntershipSystemInternshipTaskModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
