/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IntershipSystemTestModule } from '../../../test.module';
import { InternshipReportDetailComponent } from 'app/entities/internship-report/internship-report-detail.component';
import { InternshipReport } from 'app/shared/model/internship-report.model';

describe('Component Tests', () => {
  describe('InternshipReport Management Detail Component', () => {
    let comp: InternshipReportDetailComponent;
    let fixture: ComponentFixture<InternshipReportDetailComponent>;
    const route = ({ data: of({ internshipReport: new InternshipReport(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IntershipSystemTestModule],
        declarations: [InternshipReportDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InternshipReportDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InternshipReportDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.internshipReport).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
