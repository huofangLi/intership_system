/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IntershipSystemTestModule } from '../../../test.module';
import { JobChangeRecordsDetailComponent } from 'app/entities/job-change-records/job-change-records-detail.component';
import { JobChangeRecords } from 'app/shared/model/job-change-records.model';

describe('Component Tests', () => {
  describe('JobChangeRecords Management Detail Component', () => {
    let comp: JobChangeRecordsDetailComponent;
    let fixture: ComponentFixture<JobChangeRecordsDetailComponent>;
    const route = ({ data: of({ jobChangeRecords: new JobChangeRecords(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IntershipSystemTestModule],
        declarations: [JobChangeRecordsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(JobChangeRecordsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JobChangeRecordsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.jobChangeRecords).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
