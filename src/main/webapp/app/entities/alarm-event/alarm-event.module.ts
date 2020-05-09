import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { IntershipSystemSharedModule } from 'app/shared';
import {
  AlarmEventComponent,
  AlarmEventDetailComponent,
  AlarmEventUpdateComponent,
  AlarmEventDeletePopupComponent,
  AlarmEventDeleteDialogComponent,
  alarmEventRoute,
  alarmEventPopupRoute
} from './';

const ENTITY_STATES = [...alarmEventRoute, ...alarmEventPopupRoute];

@NgModule({
  imports: [IntershipSystemSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AlarmEventComponent,
    AlarmEventDetailComponent,
    AlarmEventUpdateComponent,
    AlarmEventDeleteDialogComponent,
    AlarmEventDeletePopupComponent
  ],
  entryComponents: [AlarmEventComponent, AlarmEventUpdateComponent, AlarmEventDeleteDialogComponent, AlarmEventDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IntershipSystemAlarmEventModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
