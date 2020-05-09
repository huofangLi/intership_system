import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISharingCenter } from 'app/shared/model/sharing-center.model';

@Component({
  selector: 'jhi-sharing-center-detail',
  templateUrl: './sharing-center-detail.component.html'
})
export class SharingCenterDetailComponent implements OnInit {
  sharingCenter: ISharingCenter;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sharingCenter }) => {
      this.sharingCenter = sharingCenter;
    });
  }

  previousState() {
    window.history.back();
  }
}
