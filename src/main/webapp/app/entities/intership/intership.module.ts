import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { IntershipSystemSharedModule } from 'app/shared';
import {
  IntershipComponent,
  IntershipDetailComponent,
  IntershipUpdateComponent,
  IntershipDeletePopupComponent,
  IntershipDeleteDialogComponent,
  intershipRoute,
  intershipPopupRoute
} from './';

const ENTITY_STATES = [...intershipRoute, ...intershipPopupRoute];

@NgModule({
  imports: [IntershipSystemSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    IntershipComponent,
    IntershipDetailComponent,
    IntershipUpdateComponent,
    IntershipDeleteDialogComponent,
    IntershipDeletePopupComponent
  ],
  entryComponents: [IntershipComponent, IntershipUpdateComponent, IntershipDeleteDialogComponent, IntershipDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IntershipSystemIntershipModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
