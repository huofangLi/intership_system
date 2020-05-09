import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAlarmEvent } from 'app/shared/model/alarm-event.model';

@Component({
  selector: 'jhi-alarm-event-detail',
  templateUrl: './alarm-event-detail.component.html'
})
export class AlarmEventDetailComponent implements OnInit {
  alarmEvent: IAlarmEvent;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ alarmEvent }) => {
      this.alarmEvent = alarmEvent;
    });
  }

  previousState() {
    window.history.back();
  }
}
