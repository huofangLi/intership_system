/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IntershipSystemTestModule } from '../../../test.module';
import { AbsenceRecordDeleteDialogComponent } from 'app/entities/absence-record/absence-record-delete-dialog.component';
import { AbsenceRecordService } from 'app/entities/absence-record/absence-record.service';

describe('Component Tests', () => {
  describe('AbsenceRecord Management Delete Component', () => {
    let comp: AbsenceRecordDeleteDialogComponent;
    let fixture: ComponentFixture<AbsenceRecordDeleteDialogComponent>;
    let service: AbsenceRecordService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IntershipSystemTestModule],
        declarations: [AbsenceRecordDeleteDialogComponent]
      })
        .overrideTemplate(AbsenceRecordDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AbsenceRecordDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AbsenceRecordService);
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
