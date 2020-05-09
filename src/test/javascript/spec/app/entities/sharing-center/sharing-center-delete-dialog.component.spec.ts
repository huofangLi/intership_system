/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IntershipSystemTestModule } from '../../../test.module';
import { SharingCenterDeleteDialogComponent } from 'app/entities/sharing-center/sharing-center-delete-dialog.component';
import { SharingCenterService } from 'app/entities/sharing-center/sharing-center.service';

describe('Component Tests', () => {
  describe('SharingCenter Management Delete Component', () => {
    let comp: SharingCenterDeleteDialogComponent;
    let fixture: ComponentFixture<SharingCenterDeleteDialogComponent>;
    let service: SharingCenterService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IntershipSystemTestModule],
        declarations: [SharingCenterDeleteDialogComponent]
      })
        .overrideTemplate(SharingCenterDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SharingCenterDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SharingCenterService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
