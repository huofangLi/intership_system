import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStuTea } from 'app/shared/model/stu-tea.model';

@Component({
  selector: 'jhi-stu-tea-detail',
  templateUrl: './stu-tea-detail.component.html'
})
export class StuTeaDetailComponent implements OnInit {
  stuTea: IStuTea;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ stuTea }) => {
      this.stuTea = stuTea;
    });
  }

  previousState() {
    window.history.back();
  }
}
