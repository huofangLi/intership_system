import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IJobChangeRecords, JobChangeRecords } from 'app/shared/model/job-change-records.model';
import { JobChangeRecordsService } from './job-change-records.service';
import { IIntership } from 'app/shared/model/intership.model';
import { IntershipService } from 'app/entities/intership';

@Component({
  selector: 'jhi-job-change-records-update',
  templateUrl: './job-change-records-update.component.html'
})
export class JobChangeRecordsUpdateComponent implements OnInit {
  isSaving: boolean;

  interships: IIntership[];

  editForm = this.fb.group({
    id: [],
    unitChange: [],
    positionChange: [],
    createTime: [],
    interId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected jobChangeRecordsService: JobChangeRecordsService,
    protected intershipService: IntershipService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ jobChangeRecords }) => {
      this.updateForm(jobChangeRecords);
    });
    this.intershipService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IIntership[]>) => mayBeOk.ok),
        map((response: HttpResponse<IIntership[]>) => response.body)
      )
      .subscribe((res: IIntership[]) => (this.interships = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(jobChangeRecords: IJobChangeRecords) {
    this.editForm.patchValue({
      id: jobChangeRecords.id,
      unitChange: jobChangeRecords.unitChange,
      positionChange: jobChangeRecords.positionChange,
      createTime: jobChangeRecords.createTime != null ? jobChangeRecords.createTime.format(DATE_TIME_FORMAT) : null,
      interId: jobChangeRecords.interId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const jobChangeRecords = this.createFromForm();
    if (jobChangeRecords.id !== undefined) {
      this.subscribeToSaveResponse(this.jobChangeRecordsService.update(jobChangeRecords));
    } else {
      this.subscribeToSaveResponse(this.jobChangeRecordsService.create(jobChangeRecords));
    }
  }

  private createFromForm(): IJobChangeRecords {
    return {
      ...new JobChangeRecords(),
      id: this.editForm.get(['id']).value,
      unitChange: this.editForm.get(['unitChange']).value,
      positionChange: this.editForm.get(['positionChange']).value,
      createTime:
        this.editForm.get(['createTime']).value != null ? moment(this.editForm.get(['createTime']).value, DATE_TIME_FORMAT) : undefined,
      interId: this.editForm.get(['interId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJobChangeRecords>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackIntershipById(index: number, item: IIntership) {
    return item.id;
  }
}
