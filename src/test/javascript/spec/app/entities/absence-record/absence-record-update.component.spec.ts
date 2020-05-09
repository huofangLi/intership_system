/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { IntershipSystemTestModule } from '../../../test.module';
import { AbsenceRecordUpdateComponent } from 'app/entities/absence-record/absence-record-update.component';
import { AbsenceRecordService } from 'app/entities/absence-record/absence-record.service';
import { AbsenceRecord } from 'app/shared/model/absence-record.model';

describe('Component Tests', () => {
  describe('AbsenceRecord Management Update Component', () => {
    let comp: AbsenceRecordUpdateComponent;
    let fixture: ComponentFixture<AbsenceRecordUpdateComponent>;
    let service: AbsenceRecordService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IntershipSystemTestModule],
        declarations: [AbsenceRecordUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AbsenceRecordUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AbsenceRecordUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AbsenceRecordService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AbsenceRecord(123);
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
        const entity = new AbsenceRecord();
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
