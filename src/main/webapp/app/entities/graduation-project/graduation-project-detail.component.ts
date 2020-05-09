import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGraduationProject } from 'app/shared/model/graduation-project.model';

@Component({
  selector: 'jhi-graduation-project-detail',
  templateUrl: './graduation-project-detail.component.html'
})
export class GraduationProjectDetailComponent implements OnInit {
  graduationProject: IGraduationProject;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ graduationProject }) => {
      this.graduationProject = graduationProject;
    });
  }

  previousState() {
    window.history.back();
  }
}
