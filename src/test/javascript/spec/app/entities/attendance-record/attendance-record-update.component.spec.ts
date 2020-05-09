/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { IntershipSystemTestModule } from '../../../test.module';
import { AttendanceRecordUpdateComponent } from 'app/entities/attendance-record/attendance-record-update.component';
import { AttendanceRecordService } from 'app/entities/attendance-record/attendance-record.service';
import { AttendanceRecord } from 'app/shared/model/attendance-record.model';

describe('Component Tests', () => {
  describe('AttendanceRecord Management Update Component', () => {
    let comp: AttendanceRecordUpdateComponent;
    let fixture: ComponentFixture<AttendanceRecordUpdateComponent>;
    let service: AttendanceRecordService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IntershipSystemTestModule],
        declarations: [AttendanceRecordUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AttendanceRecordUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AttendanceRecordUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AttendanceRecordService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AttendanceRecord(123);
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
        const entity = new AttendanceRecord();
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
