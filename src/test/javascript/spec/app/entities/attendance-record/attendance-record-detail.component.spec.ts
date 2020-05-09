/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IntershipSystemTestModule } from '../../../test.module';
import { AttendanceRecordDetailComponent } from 'app/entities/attendance-record/attendance-record-detail.component';
import { AttendanceRecord } from 'app/shared/model/attendance-record.model';

describe('Component Tests', () => {
  describe('AttendanceRecord Management Detail Component', () => {
    let comp: AttendanceRecordDetailComponent;
    let fixture: ComponentFixture<AttendanceRecordDetailComponent>;
    const route = ({ data: of({ attendanceRecord: new AttendanceRecord(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IntershipSystemTestModule],
        declarations: [AttendanceRecordDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AttendanceRecordDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AttendanceRecordDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.attendanceRecord).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
