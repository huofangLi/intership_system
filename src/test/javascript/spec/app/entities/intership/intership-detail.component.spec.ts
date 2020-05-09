/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IntershipSystemTestModule } from '../../../test.module';
import { IntershipDetailComponent } from 'app/entities/intership/intership-detail.component';
import { Intership } from 'app/shared/model/intership.model';

describe('Component Tests', () => {
  describe('Intership Management Detail Component', () => {
    let comp: IntershipDetailComponent;
    let fixture: ComponentFixture<IntershipDetailComponent>;
    const route = ({ data: of({ intership: new Intership(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IntershipSystemTestModule],
        declarations: [IntershipDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(IntershipDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IntershipDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.intership).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
