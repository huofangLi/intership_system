import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJobChangeRecords } from 'app/shared/model/job-change-records.model';

@Component({
  selector: 'jhi-job-change-records-detail',
  templateUrl: './job-change-records-detail.component.html'
})
export class JobChangeRecordsDetailComponent implements OnInit {
  jobChangeRecords: IJobChangeRecords;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ jobChangeRecords }) => {
      this.jobChangeRecords = jobChangeRecords;
    });
  }

  previousState() {
    window.history.back();
  }
}
