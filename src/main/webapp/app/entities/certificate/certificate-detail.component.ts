import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICertificate } from 'app/shared/model/certificate.model';

@Component({
  selector: 'jhi-certificate-detail',
  templateUrl: './certificate-detail.component.html'
})
export class CertificateDetailComponent implements OnInit {
  certificate: ICertificate;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ certificate }) => {
      this.certificate = certificate;
    });
  }

  previousState() {
    window.history.back();
  }
}
