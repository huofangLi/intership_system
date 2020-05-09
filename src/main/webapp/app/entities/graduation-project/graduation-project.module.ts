import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { IntershipSystemSharedModule } from 'app/shared';
import {
  GraduationProjectComponent,
  GraduationProjectDetailComponent,
  GraduationProjectUpdateComponent,
  GraduationProjectDeletePopupComponent,
  GraduationProjectDeleteDialogComponent,
  graduationProjectRoute,
  graduationProjectPopupRoute
} from './';

const ENTITY_STATES = [...graduationProjectRoute, ...graduationProjectPopupRoute];

@NgModule({
  imports: [IntershipSystemSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    GraduationProjectComponent,
    GraduationProjectDetailComponent,
    GraduationProjectUpdateComponent,
    GraduationProjectDeleteDialogComponent,
    GraduationProjectDeletePopupComponent
  ],
  entryComponents: [
    GraduationProjectComponent,
    GraduationProjectUpdateComponent,
    GraduationProjectDeleteDialogComponent,
    GraduationProjectDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IntershipSystemGraduationProjectModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
