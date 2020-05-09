/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IntershipSystemTestModule } from '../../../test.module';
import { StuTeaDeleteDialogComponent } from 'app/entities/stu-tea/stu-tea-delete-dialog.component';
import { StuTeaService } from 'app/entities/stu-tea/stu-tea.service';

describe('Component Tests', () => {
  describe('StuTea Management Delete Component', () => {
    let comp: StuTeaDeleteDialogComponent;
    let fixture: ComponentFixture<StuTeaDeleteDialogComponent>;
    let service: StuTeaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IntershipSystemTestModule],
        declarations: [StuTeaDeleteDialogComponent]
      })
        .overrideTemplate(StuTeaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StuTeaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StuTeaService);
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
