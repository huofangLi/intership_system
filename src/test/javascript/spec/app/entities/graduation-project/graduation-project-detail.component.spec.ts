/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IntershipSystemTestModule } from '../../../test.module';
import { GraduationProjectDetailComponent } from 'app/entities/graduation-project/graduation-project-detail.component';
import { GraduationProject } from 'app/shared/model/graduation-project.model';

describe('Component Tests', () => {
  describe('GraduationProject Management Detail Component', () => {
    let comp: GraduationProjectDetailComponent;
    let fixture: ComponentFixture<GraduationProjectDetailComponent>;
    const route = ({ data: of({ graduationProject: new GraduationProject(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IntershipSystemTestModule],
        declarations: [GraduationProjectDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GraduationProjectDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GraduationProjectDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.graduationProject).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
