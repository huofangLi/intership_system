/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { IntershipSystemTestModule } from '../../../test.module';
import { StuTeaUpdateComponent } from 'app/entities/stu-tea/stu-tea-update.component';
import { StuTeaService } from 'app/entities/stu-tea/stu-tea.service';
import { StuTea } from 'app/shared/model/stu-tea.model';

describe('Component Tests', () => {
  describe('StuTea Management Update Component', () => {
    let comp: StuTeaUpdateComponent;
    let fixture: ComponentFixture<StuTeaUpdateComponent>;
    let service: StuTeaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IntershipSystemTestModule],
        declarations: [StuTeaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(StuTeaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StuTeaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StuTeaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new StuTea(123);
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
        const entity = new StuTea();
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
