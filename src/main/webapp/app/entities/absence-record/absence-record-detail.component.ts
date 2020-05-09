import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAbsenceRecord } from 'app/shared/model/absence-record.model';

@Component({
  selector: 'jhi-absence-record-detail',
  templateUrl: './absence-record-detail.component.html'
})
export class AbsenceRecordDetailComponent implements OnInit {
  absenceRecord: IAbsenceRecord;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ absenceRecord }) => {
      this.absenceRecord = absenceRecord;
    });
  }

  previousState() {
    window.history.back();
  }
}
