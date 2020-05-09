import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIntership } from 'app/shared/model/intership.model';

@Component({
  selector: 'jhi-intership-detail',
  templateUrl: './intership-detail.component.html'
})
export class IntershipDetailComponent implements OnInit {
  intership: IIntership;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ intership }) => {
      this.intership = intership;
    });
  }

  previousState() {
    window.history.back();
  }
}
