import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAttendanceManagement } from 'app/shared/model/attendance-management.model';

@Component({
  selector: 'jhi-attendance-management-detail',
  templateUrl: './attendance-management-detail.component.html'
})
export class AttendanceManagementDetailComponent implements OnInit {
  attendanceManagement: IAttendanceManagement;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ attendanceManagement }) => {
      this.attendanceManagement = attendanceManagement;
    });
  }

  previousState() {
    window.history.back();
  }
}
