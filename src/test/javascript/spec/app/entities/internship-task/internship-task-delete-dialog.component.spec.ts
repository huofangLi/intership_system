/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IntershipSystemTestModule } from '../../../test.module';
import { InternshipTaskDeleteDialogComponent } from 'app/entities/internship-task/internship-task-delete-dialog.component';
import { InternshipTaskService } from 'app/entities/internship-task/internship-task.service';

describe('Component Tests', () => {
  describe('InternshipTask Management Delete Component', () => {
    let comp: InternshipTaskDeleteDialogComponent;
    let fixture: ComponentFixture<InternshipTaskDeleteDialogComponent>;
    let service: InternshipTaskService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IntershipSystemTestModule],
        declarations: [InternshipTaskDeleteDialogComponent]
      })
        .overrideTemplate(InternshipTaskDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InternshipTaskDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InternshipTaskService);
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
