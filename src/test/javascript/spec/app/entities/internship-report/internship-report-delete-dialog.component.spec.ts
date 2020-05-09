/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IntershipSystemTestModule } from '../../../test.module';
import { InternshipReportDeleteDialogComponent } from 'app/entities/internship-report/internship-report-delete-dialog.component';
import { InternshipReportService } from 'app/entities/internship-report/internship-report.service';

describe('Component Tests', () => {
  describe('InternshipReport Management Delete Component', () => {
    let comp: InternshipReportDeleteDialogComponent;
    let fixture: ComponentFixture<InternshipReportDeleteDialogComponent>;
    let service: InternshipReportService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IntershipSystemTestModule],
        declarations: [InternshipReportDeleteDialogComponent]
      })
        .overrideTemplate(InternshipReportDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InternshipReportDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InternshipReportService);
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
