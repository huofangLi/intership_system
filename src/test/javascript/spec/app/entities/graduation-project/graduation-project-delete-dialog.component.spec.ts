/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IntershipSystemTestModule } from '../../../test.module';
import { GraduationProjectDeleteDialogComponent } from 'app/entities/graduation-project/graduation-project-delete-dialog.component';
import { GraduationProjectService } from 'app/entities/graduation-project/graduation-project.service';

describe('Component Tests', () => {
  describe('GraduationProject Management Delete Component', () => {
    let comp: GraduationProjectDeleteDialogComponent;
    let fixture: ComponentFixture<GraduationProjectDeleteDialogComponent>;
    let service: GraduationProjectService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IntershipSystemTestModule],
        declarations: [GraduationProjectDeleteDialogComponent]
      })
        .overrideTemplate(GraduationProjectDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GraduationProjectDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GraduationProjectService);
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
