/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { IntershipSystemTestModule } from '../../../test.module';
import { JobChangeRecordsUpdateComponent } from 'app/entities/job-change-records/job-change-records-update.component';
import { JobChangeRecordsService } from 'app/entities/job-change-records/job-change-records.service';
import { JobChangeRecords } from 'app/shared/model/job-change-records.model';

describe('Component Tests', () => {
  describe('JobChangeRecords Management Update Component', () => {
    let comp: JobChangeRecordsUpdateComponent;
    let fixture: ComponentFixture<JobChangeRecordsUpdateComponent>;
    let service: JobChangeRecordsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IntershipSystemTestModule],
        declarations: [JobChangeRecordsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(JobChangeRecordsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JobChangeRecordsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JobChangeRecordsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new JobChangeRecords(123);
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
        const entity = new JobChangeRecords();
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
