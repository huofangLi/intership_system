/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IntershipSystemTestModule } from '../../../test.module';
import { AttendanceManagementDetailComponent } from 'app/entities/attendance-management/attendance-management-detail.component';
import { AttendanceManagement } from 'app/shared/model/attendance-management.model';

describe('Component Tests', () => {
  describe('AttendanceManagement Management Detail Component', () => {
    let comp: AttendanceManagementDetailComponent;
    let fixture: ComponentFixture<AttendanceManagementDetailComponent>;
    const route = ({ data: of({ attendanceManagement: new AttendanceManagement(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IntershipSystemTestModule],
        declarations: [AttendanceManagementDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AttendanceManagementDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AttendanceManagementDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.attendanceManagement).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
