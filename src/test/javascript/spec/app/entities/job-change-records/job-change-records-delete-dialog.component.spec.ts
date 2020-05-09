/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IntershipSystemTestModule } from '../../../test.module';
import { JobChangeRecordsDeleteDialogComponent } from 'app/entities/job-change-records/job-change-records-delete-dialog.component';
import { JobChangeRecordsService } from 'app/entities/job-change-records/job-change-records.service';

describe('Component Tests', () => {
  describe('JobChangeRecords Management Delete Component', () => {
    let comp: JobChangeRecordsDeleteDialogComponent;
    let fixture: ComponentFixture<JobChangeRecordsDeleteDialogComponent>;
    let service: JobChangeRecordsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IntershipSystemTestModule],
        declarations: [JobChangeRecordsDeleteDialogComponent]
      })
        .overrideTemplate(JobChangeRecordsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JobChangeRecordsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JobChangeRecordsService);
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
