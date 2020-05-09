/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { IntershipSystemTestModule } from '../../../test.module';
import { AttendanceManagementUpdateComponent } from 'app/entities/attendance-management/attendance-management-update.component';
import { AttendanceManagementService } from 'app/entities/attendance-management/attendance-management.service';
import { AttendanceManagement } from 'app/shared/model/attendance-management.model';

describe('Component Tests', () => {
  describe('AttendanceManagement Management Update Component', () => {
    let comp: AttendanceManagementUpdateComponent;
    let fixture: ComponentFixture<AttendanceManagementUpdateComponent>;
    let service: AttendanceManagementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IntershipSystemTestModule],
        declarations: [AttendanceManagementUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AttendanceManagementUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AttendanceManagementUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AttendanceManagementService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AttendanceManagement(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new AttendanceManagement();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
