import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { IntershipSystemSharedModule } from 'app/shared';
import {
  CertificateComponent,
  CertificateDetailComponent,
  CertificateUpdateComponent,
  CertificateDeletePopupComponent,
  CertificateDeleteDialogComponent,
  certificateRoute,
  certificatePopupRoute
} from './';

const ENTITY_STATES = [...certificateRoute, ...certificatePopupRoute];

@NgModule({
  imports: [IntershipSystemSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CertificateComponent,
    CertificateDetailComponent,
    CertificateUpdateComponent,
    CertificateDeleteDialogComponent,
    CertificateDeletePopupComponent
  ],
  entryComponents: [CertificateComponent, CertificateUpdateComponent, CertificateDeleteDialogComponent, CertificateDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IntershipSystemCertificateModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
