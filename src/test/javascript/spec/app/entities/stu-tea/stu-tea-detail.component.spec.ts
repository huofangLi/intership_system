/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IntershipSystemTestModule } from '../../../test.module';
import { StuTeaDetailComponent } from 'app/entities/stu-tea/stu-tea-detail.component';
import { StuTea } from 'app/shared/model/stu-tea.model';

describe('Component Tests', () => {
  describe('StuTea Management Detail Component', () => {
    let comp: StuTeaDetailComponent;
    let fixture: ComponentFixture<StuTeaDetailComponent>;
    const route = ({ data: of({ stuTea: new StuTea(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IntershipSystemTestModule],
        declarations: [StuTeaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(StuTeaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StuTeaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.stuTea).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
