import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ICertificate, Certificate } from 'app/shared/model/certificate.model';
import { CertificateService } from './certificate.service';

@Component({
  selector: 'jhi-certificate-update',
  templateUrl: './certificate-update.component.html'
})
export class CertificateUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    certificateName: [],
    certificateType: [],
    certificateLevel: [],
    certificateAcquisitionTime: [],
    certificatePhoto: [],
    createdTime: [],
    modifyTime: []
  });

  constructor(protected certificateService: CertificateService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ certificate }) => {
      this.updateForm(certificate);
    });
  }

  updateForm(certificate: ICertificate) {
    this.editForm.patchValue({
      id: certificate.id,
      certificateName: certificate.certificateName,
      certificateType: certificate.certificateType,
      certificateLevel: certificate.certificateLevel,
      certificateAcquisitionTime:
        certificate.certificateAcquisitionTime != null ? certificate.certificateAcquisitionTime.format(DATE_TIME_FORMAT) : null,
      certificatePhoto: certificate.certificatePhoto,
      createdTime: certificate.createdTime != null ? certificate.createdTime.format(DATE_TIME_FORMAT) : null,
      modifyTime: certificate.modifyTime != null ? certificate.modifyTime.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const certificate = this.createFromForm();
    if (certificate.id !== undefined) {
      this.subscribeToSaveResponse(this.certificateService.update(certificate));
    } else {
      this.subscribeToSaveResponse(this.certificateService.create(certificate));
    }
  }

  private createFromForm(): ICertificate {
    return {
      ...new Certificate(),
      id: this.editForm.get(['id']).value,
      certificateName: this.editForm.get(['certificateName']).value,
      certificateType: this.editForm.get(['certificateType']).value,
      certificateLevel: this.editForm.get(['certificateLevel']).value,
      certificateAcquisitionTime:
        this.editForm.get(['certificateAcquisitionTime']).value != null
          ? moment(this.editForm.get(['certificateAcquisitionTime']).value, DATE_TIME_FORMAT)
          : undefined,
      certificatePhoto: this.editForm.get(['certificatePhoto']).value,
      createdTime:
        this.editForm.get(['createdTime']).value != null ? moment(this.editForm.get(['createdTime']).value, DATE_TIME_FORMAT) : undefined,
      modifyTime:
        this.editForm.get(['modifyTime']).value != null ? moment(this.editForm.get(['modifyTime']).value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICertificate>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
