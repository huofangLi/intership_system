/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IntershipSystemTestModule } from '../../../test.module';
import { InternshipTaskDetailComponent } from 'app/entities/internship-task/internship-task-detail.component';
import { InternshipTask } from 'app/shared/model/internship-task.model';

describe('Component Tests', () => {
  describe('InternshipTask Management Detail Component', () => {
    let comp: InternshipTaskDetailComponent;
    let fixture: ComponentFixture<InternshipTaskDetailComponent>;
    const route = ({ data: of({ internshipTask: new InternshipTask(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IntershipSystemTestModule],
        declarations: [InternshipTaskDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InternshipTaskDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InternshipTaskDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.internshipTask).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
