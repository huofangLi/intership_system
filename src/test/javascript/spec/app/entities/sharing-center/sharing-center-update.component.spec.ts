/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { IntershipSystemTestModule } from '../../../test.module';
import { SharingCenterUpdateComponent } from 'app/entities/sharing-center/sharing-center-update.component';
import { SharingCenterService } from 'app/entities/sharing-center/sharing-center.service';
import { SharingCenter } from 'app/shared/model/sharing-center.model';

describe('Component Tests', () => {
  describe('SharingCenter Management Update Component', () => {
    let comp: SharingCenterUpdateComponent;
    let fixture: ComponentFixture<SharingCenterUpdateComponent>;
    let service: SharingCenterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IntershipSystemTestModule],
        declarations: [SharingCenterUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SharingCenterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SharingCenterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SharingCenterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SharingCenter(123);
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
        const entity = new SharingCenter();
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
