import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAttendanceRecord } from 'app/shared/model/attendance-record.model';

@Component({
  selector: 'jhi-attendance-record-detail',
  templateUrl: './attendance-record-detail.component.html'
})
export class AttendanceRecordDetailComponent implements OnInit {
  attendanceRecord: IAttendanceRecord;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ attendanceRecord }) => {
      this.attendanceRecord = attendanceRecord;
    });
  }

  previousState() {
    window.history.back();
  }
}
