/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IntershipSystemTestModule } from '../../../test.module';
import { AlarmEventDetailComponent } from 'app/entities/alarm-event/alarm-event-detail.component';
import { AlarmEvent } from 'app/shared/model/alarm-event.model';

describe('Component Tests', () => {
  describe('AlarmEvent Management Detail Component', () => {
    let comp: AlarmEventDetailComponent;
    let fixture: ComponentFixture<AlarmEventDetailComponent>;
    const route = ({ data: of({ alarmEvent: new AlarmEvent(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IntershipSystemTestModule],
        declarations: [AlarmEventDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AlarmEventDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AlarmEventDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.alarmEvent).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
