import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IAbsenceRecord, AbsenceRecord } from 'app/shared/model/absence-record.model';
import { AbsenceRecordService } from './absence-record.service';
import { IStudent } from 'app/shared/model/student.model';
import { StudentService } from 'app/entities/student';

@Component({
  selector: 'jhi-absence-record-update',
  templateUrl: './absence-record-update.component.html'
})
export class AbsenceRecordUpdateComponent implements OnInit {
  isSaving: boolean;

  students: IStudent[];

  editForm = this.fb.group({
    id: [],
    absenceStartTime: [],
    absenceEndTime: [],
    absenceDays: [],
    company: [],
    remarks: [],
    createdTime: [],
    stuId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected absenceRecordService: AbsenceRecordService,
    protected studentService: StudentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ absenceRecord }) => {
      this.updateForm(absenceRecord);
    });
    this.studentService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IStudent[]>) => mayBeOk.ok),
        map((response: HttpResponse<IStudent[]>) => response.body)
      )
      .subscribe((res: IStudent[]) => (this.students = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(absenceRecord: IAbsenceRecord) {
    this.editForm.patchValue({
      id: absenceRecord.id,
      absenceStartTime: absenceRecord.absenceStartTime != null ? absenceRecord.absenceStartTime.format(DATE_TIME_FORMAT) : null,
      absenceEndTime: absenceRecord.absenceEndTime != null ? absenceRecord.absenceEndTime.format(DATE_TIME_FORMAT) : null,
      absenceDays: absenceRecord.absenceDays,
      company: absenceRecord.company,
      remarks: absenceRecord.remarks,
      createdTime: absenceRecord.createdTime != null ? absenceRecord.createdTime.format(DATE_TIME_FORMAT) : null,
      stuId: absenceRecord.stuId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const absenceRecord = this.createFromForm();
    if (absenceRecord.id !== undefined) {
      this.subscribeToSaveResponse(this.absenceRecordService.update(absenceRecord));
    } else {
      this.subscribeToSaveResponse(this.absenceRecordService.create(absenceRecord));
    }
  }

  private createFromForm(): IAbsenceRecord {
    return {
      ...new AbsenceRecord(),
      id: this.editForm.get(['id']).value,
      absenceStartTime:
        this.editForm.get(['absenceStartTime']).value != null
          ? moment(this.editForm.get(['absenceStartTime']).value, DATE_TIME_FORMAT)
          : undefined,
      absenceEndTime:
        this.editForm.get(['absenceEndTime']).value != null
          ? moment(this.editForm.get(['absenceEndTime']).value, DATE_TIME_FORMAT)
          : undefined,
      absenceDays: this.editForm.get(['absenceDays']).value,
      company: this.editForm.get(['company']).value,
      remarks: this.editForm.get(['remarks']).value,
      createdTime:
        this.editForm.get(['createdTime']).value != null ? moment(this.editForm.get(['createdTime']).value, DATE_TIME_FORMAT) : undefined,
      stuId: this.editForm.get(['stuId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAbsenceRecord>>) {
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

  trackStudentById(index: number, item: IStudent) {
    return item.id;
  }
}
