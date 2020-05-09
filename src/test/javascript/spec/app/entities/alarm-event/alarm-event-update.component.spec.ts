/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { IntershipSystemTestModule } from '../../../test.module';
import { AlarmEventUpdateComponent } from 'app/entities/alarm-event/alarm-event-update.component';
import { AlarmEventService } from 'app/entities/alarm-event/alarm-event.service';
import { AlarmEvent } from 'app/shared/model/alarm-event.model';

describe('Component Tests', () => {
  describe('AlarmEvent Management Update Component', () => {
    let comp: AlarmEventUpdateComponent;
    let fixture: ComponentFixture<AlarmEventUpdateComponent>;
    let service: AlarmEventService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IntershipSystemTestModule],
        declarations: [AlarmEventUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AlarmEventUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AlarmEventUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AlarmEventService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AlarmEvent(123);
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
        const entity = new AlarmEvent();
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
