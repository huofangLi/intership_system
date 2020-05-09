import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { IntershipSystemSharedModule } from 'app/shared';
import {
  StuTeaComponent,
  StuTeaDetailComponent,
  StuTeaUpdateComponent,
  StuTeaDeletePopupComponent,
  StuTeaDeleteDialogComponent,
  stuTeaRoute,
  stuTeaPopupRoute
} from './';

const ENTITY_STATES = [...stuTeaRoute, ...stuTeaPopupRoute];

@NgModule({
  imports: [IntershipSystemSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [StuTeaComponent, StuTeaDetailComponent, StuTeaUpdateComponent, StuTeaDeleteDialogComponent, StuTeaDeletePopupComponent],
  entryComponents: [StuTeaComponent, StuTeaUpdateComponent, StuTeaDeleteDialogComponent, StuTeaDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IntershipSystemStuTeaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
