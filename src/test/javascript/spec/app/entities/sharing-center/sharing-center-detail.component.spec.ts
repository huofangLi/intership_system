/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IntershipSystemTestModule } from '../../../test.module';
import { SharingCenterDetailComponent } from 'app/entities/sharing-center/sharing-center-detail.component';
import { SharingCenter } from 'app/shared/model/sharing-center.model';

describe('Component Tests', () => {
  describe('SharingCenter Management Detail Component', () => {
    let comp: SharingCenterDetailComponent;
    let fixture: ComponentFixture<SharingCenterDetailComponent>;
    const route = ({ data: of({ sharingCenter: new SharingCenter(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IntershipSystemTestModule],
        declarations: [SharingCenterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SharingCenterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SharingCenterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sharingCenter).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
