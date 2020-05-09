/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IntershipSystemTestModule } from '../../../test.module';
import { AttendanceManagementDeleteDialogComponent } from 'app/entities/attendance-management/attendance-management-delete-dialog.component';
import { AttendanceManagementService } from 'app/entities/attendance-management/attendance-management.service';

describe('Component Tests', () => {
  describe('AttendanceManagement Management Delete Component', () => {
    let comp: AttendanceManagementDeleteDialogComponent;
    let fixture: ComponentFixture<AttendanceManagementDeleteDialogComponent>;
    let service: AttendanceManagementService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IntershipSystemTestModule],
        declarations: [AttendanceManagementDeleteDialogComponent]
      })
        .overrideTemplate(AttendanceManagementDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AttendanceManagementDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AttendanceManagementService);
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
