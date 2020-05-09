/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { IntershipSystemTestModule } from '../../../test.module';
import { IntershipUpdateComponent } from 'app/entities/intership/intership-update.component';
import { IntershipService } from 'app/entities/intership/intership.service';
import { Intership } from 'app/shared/model/intership.model';

describe('Component Tests', () => {
  describe('Intership Management Update Component', () => {
    let comp: IntershipUpdateComponent;
    let fixture: ComponentFixture<IntershipUpdateComponent>;
    let service: IntershipService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IntershipSystemTestModule],
        declarations: [IntershipUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(IntershipUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IntershipUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IntershipService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Intership(123);
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
        const entity = new Intership();
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
