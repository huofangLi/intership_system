/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { IntershipSystemTestModule } from '../../../test.module';
import { GraduationProjectUpdateComponent } from 'app/entities/graduation-project/graduation-project-update.component';
import { GraduationProjectService } from 'app/entities/graduation-project/graduation-project.service';
import { GraduationProject } from 'app/shared/model/graduation-project.model';

describe('Component Tests', () => {
  describe('GraduationProject Management Update Component', () => {
    let comp: GraduationProjectUpdateComponent;
    let fixture: ComponentFixture<GraduationProjectUpdateComponent>;
    let service: GraduationProjectService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IntershipSystemTestModule],
        declarations: [GraduationProjectUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GraduationProjectUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GraduationProjectUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GraduationProjectService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GraduationProject(123);
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
        const entity = new GraduationProject();
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
