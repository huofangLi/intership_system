/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { IntershipSystemTestModule } from '../../../test.module';
import { InternshipReportUpdateComponent } from 'app/entities/internship-report/internship-report-update.component';
import { InternshipReportService } from 'app/entities/internship-report/internship-report.service';
import { InternshipReport } from 'app/shared/model/internship-report.model';

describe('Component Tests', () => {
  describe('InternshipReport Management Update Component', () => {
    let comp: InternshipReportUpdateComponent;
    let fixture: ComponentFixture<InternshipReportUpdateComponent>;
    let service: InternshipReportService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IntershipSystemTestModule],
        declarations: [InternshipReportUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(InternshipReportUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InternshipReportUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InternshipReportService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InternshipReport(123);
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
        const entity = new InternshipReport();
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
