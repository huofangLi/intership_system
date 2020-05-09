import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IAttendanceRecord, AttendanceRecord } from 'app/shared/model/attendance-record.model';
import { AttendanceRecordService } from './attendance-record.service';
import { IStudent } from 'app/shared/model/student.model';
import { StudentService } from 'app/entities/student';

@Component({
  selector: 'jhi-attendance-record-update',
  templateUrl: './attendance-record-update.component.html'
})
export class AttendanceRecordUpdateComponent implements OnInit {
  isSaving: boolean;

  students: IStudent[];

  editForm = this.fb.group({
    id: [],
    internshipBatch: [],
    punchTime: [],
    punchLocation: [],
    attendanceStatus: [],
    createdTime: [],
    modifyTime: [],
    stuId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected attendanceRecordService: AttendanceRecordService,
    protected studentService: StudentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ attendanceRecord }) => {
      this.updateForm(attendanceRecord);
    });
    this.studentService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IStudent[]>) => mayBeOk.ok),
        map((response: HttpResponse<IStudent[]>) => response.body)
      )
      .subscribe((res: IStudent[]) => (this.students = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(attendanceRecord: IAttendanceRecord) {
    this.editForm.patchValue({
      id: attendanceRecord.id,
      internshipBatch: attendanceRecord.internshipBatch,
      punchTime: attendanceRecord.punchTime != null ? attendanceRecord.punchTime.format(DATE_TIME_FORMAT) : null,
      punchLocation: attendanceRecord.punchLocation,
      attendanceStatus: attendanceRecord.attendanceStatus,
      createdTime: attendanceRecord.createdTime != null ? attendanceRecord.createdTime.format(DATE_TIME_FORMAT) : null,
      modifyTime: attendanceRecord.modifyTime != null ? attendanceRecord.modifyTime.format(DATE_TIME_FORMAT) : null,
      stuId: attendanceRecord.stuId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const attendanceRecord = this.createFromForm();
    if (attendanceRecord.id !== undefined) {
      this.subscribeToSaveResponse(this.attendanceRecordService.update(attendanceRecord));
    } else {
      this.subscribeToSaveResponse(this.attendanceRecordService.create(attendanceRecord));
    }
  }

  private createFromForm(): IAttendanceRecord {
    return {
      ...new AttendanceRecord(),
      id: this.editForm.get(['id']).value,
      internshipBatch: this.editForm.get(['internshipBatch']).value,
      punchTime:
        this.editForm.get(['punchTime']).value != null ? moment(this.editForm.get(['punchTime']).value, DATE_TIME_FORMAT) : undefined,
      punchLocation: this.editForm.get(['punchLocation']).value,
      attendanceStatus: this.editForm.get(['attendanceStatus']).value,
      createdTime:
        this.editForm.get(['createdTime']).value != null ? moment(this.editForm.get(['createdTime']).value, DATE_TIME_FORMAT) : undefined,
      modifyTime:
        this.editForm.get(['modifyTime']).value != null ? moment(this.editForm.get(['modifyTime']).value, DATE_TIME_FORMAT) : undefined,
      stuId: this.editForm.get(['stuId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttendanceRecord>>) {
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
