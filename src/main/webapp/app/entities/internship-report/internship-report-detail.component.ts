import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInternshipReport } from 'app/shared/model/internship-report.model';

@Component({
  selector: 'jhi-internship-report-detail',
  templateUrl: './internship-report-detail.component.html'
})
export class InternshipReportDetailComponent implements OnInit {
  internshipReport: IInternshipReport;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ internshipReport }) => {
      this.internshipReport = internshipReport;
    });
  }

  previousState() {
    window.history.back();
  }
}
