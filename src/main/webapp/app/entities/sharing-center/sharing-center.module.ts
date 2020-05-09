import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { IntershipSystemSharedModule } from 'app/shared';
import {
  SharingCenterComponent,
  SharingCenterDetailComponent,
  SharingCenterUpdateComponent,
  SharingCenterDeletePopupComponent,
  SharingCenterDeleteDialogComponent,
  sharingCenterRoute,
  sharingCenterPopupRoute
} from './';

const ENTITY_STATES = [...sharingCenterRoute, ...sharingCenterPopupRoute];

@NgModule({
  imports: [IntershipSystemSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SharingCenterComponent,
    SharingCenterDetailComponent,
    SharingCenterUpdateComponent,
    SharingCenterDeleteDialogComponent,
    SharingCenterDeletePopupComponent
  ],
  entryComponents: [
    SharingCenterComponent,
    SharingCenterUpdateComponent,
    SharingCenterDeleteDialogComponent,
    SharingCenterDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IntershipSystemSharingCenterModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
