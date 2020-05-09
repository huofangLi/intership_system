import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInternshipTask } from 'app/shared/model/internship-task.model';

@Component({
  selector: 'jhi-internship-task-detail',
  templateUrl: './internship-task-detail.component.html'
})
export class InternshipTaskDetailComponent implements OnInit {
  internshipTask: IInternshipTask;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ internshipTask }) => {
      this.internshipTask = internshipTask;
    });
  }

  previousState() {
    window.history.back();
  }
}
