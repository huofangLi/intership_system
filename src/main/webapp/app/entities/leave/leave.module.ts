import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { IntershipSystemSharedModule } from 'app/shared';
import {
  LeaveComponent,
  LeaveDetailComponent,
  LeaveUpdateComponent,
  LeaveDeletePopupComponent,
  LeaveDeleteDialogComponent,
  leaveRoute,
  leavePopupRoute
} from './';

const ENTITY_STATES = [...leaveRoute, ...leavePopupRoute];

@NgModule({
  imports: [IntershipSystemSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [LeaveComponent, LeaveDetailComponent, LeaveUpdateComponent, LeaveDeleteDialogComponent, LeaveDeletePopupComponent],
  entryComponents: [LeaveComponent, LeaveUpdateComponent, LeaveDeleteDialogComponent, LeaveDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IntershipSystemLeaveModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
