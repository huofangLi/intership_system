/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IntershipSystemTestModule } from '../../../test.module';
import { AlarmEventDeleteDialogComponent } from 'app/entities/alarm-event/alarm-event-delete-dialog.component';
import { AlarmEventService } from 'app/entities/alarm-event/alarm-event.service';

describe('Component Tests', () => {
  describe('AlarmEvent Management Delete Component', () => {
    let comp: AlarmEventDeleteDialogComponent;
    let fixture: ComponentFixture<AlarmEventDeleteDialogComponent>;
    let service: AlarmEventService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IntershipSystemTestModule],
        declarations: [AlarmEventDeleteDialogComponent]
      })
        .overrideTemplate(AlarmEventDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AlarmEventDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AlarmEventService);
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
