/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { IntershipSystemTestModule } from '../../../test.module';
import { InternshipTaskUpdateComponent } from 'app/entities/internship-task/internship-task-update.component';
import { InternshipTaskService } from 'app/entities/internship-task/internship-task.service';
import { InternshipTask } from 'app/shared/model/internship-task.model';

describe('Component Tests', () => {
  describe('InternshipTask Management Update Component', () => {
    let comp: InternshipTaskUpdateComponent;
    let fixture: ComponentFixture<InternshipTaskUpdateComponent>;
    let service: InternshipTaskService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IntershipSystemTestModule],
        declarations: [InternshipTaskUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(InternshipTaskUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InternshipTaskUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InternshipTaskService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InternshipTask(123);
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
        const entity = new InternshipTask();
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
