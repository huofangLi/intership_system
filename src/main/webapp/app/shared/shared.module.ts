import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { IntershipSystemSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [IntershipSystemSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [IntershipSystemSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IntershipSystemSharedModule {
  static forRoot() {
    return {
      ngModule: IntershipSystemSharedModule
    };
  }
}
