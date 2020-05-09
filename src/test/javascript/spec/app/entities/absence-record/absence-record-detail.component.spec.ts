/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IntershipSystemTestModule } from '../../../test.module';
import { AbsenceRecordDetailComponent } from 'app/entities/absence-record/absence-record-detail.component';
import { AbsenceRecord } from 'app/shared/model/absence-record.model';

describe('Component Tests', () => {
  describe('AbsenceRecord Management Detail Component', () => {
    let comp: AbsenceRecordDetailComponent;
    let fixture: ComponentFixture<AbsenceRecordDetailComponent>;
    const route = ({ data: of({ absenceRecord: new AbsenceRecord(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IntershipSystemTestModule],
        declarations: [AbsenceRecordDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AbsenceRecordDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AbsenceRecordDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.absenceRecord).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
