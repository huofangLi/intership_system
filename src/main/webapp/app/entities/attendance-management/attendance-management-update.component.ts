import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IAttendanceManagement, AttendanceManagement } from 'app/shared/model/attendance-management.model';
import { AttendanceManagementService } from './attendance-management.service';
import { IStudent } from 'app/shared/model/student.model';
import { StudentService } from 'app/entities/student';

@Component({
  selector: 'jhi-attendance-management-update',
  templateUrl: './attendance-management-update.component.html'
})
export class AttendanceManagementUpdateComponent implements OnInit {
  isSaving: boolean;

  students: IStudent[];

  editForm = this.fb.group({
    id: [],
    signIn: [],
    position: [],
    coordinate: [],
    company: [],
    createdTime: [],
    stuId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected attendanceManagementService: AttendanceManagementService,
    protected studentService: StudentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ attendanceManagement }) => {
      this.updateForm(attendanceManagement);
    });
    this.studentService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IStudent[]>) => mayBeOk.ok),
        map((response: HttpResponse<IStudent[]>) => response.body)
      )
      .subscribe((res: IStudent[]) => (this.students = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(attendanceManagement: IAttendanceManagement) {
    this.editForm.patchValue({
      id: attendanceManagement.id,
      signIn: attendanceManagement.signIn != null ? attendanceManagement.signIn.format(DATE_TIME_FORMAT) : null,
      position: attendanceManagement.position,
      coordinate: attendanceManagement.coordinate,
      company: attendanceManagement.company,
      createdTime: attendanceManagement.createdTime != null ? attendanceManagement.createdTime.format(DATE_TIME_FORMAT) : null,
      stuId: attendanceManagement.stuId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const attendanceManagement = this.createFromForm();
    if (attendanceManagement.id !== undefined) {
      this.subscribeToSaveResponse(this.attendanceManagementService.update(attendanceManagement));
    } else {
      this.subscribeToSaveResponse(this.attendanceManagementService.create(attendanceManagement));
    }
  }

  private createFromForm(): IAttendanceManagement {
    return {
      ...new AttendanceManagement(),
      id: this.editForm.get(['id']).value,
      signIn: this.editForm.get(['signIn']).value != null ? moment(this.editForm.get(['signIn']).value, DATE_TIME_FORMAT) : undefined,
      position: this.editForm.get(['position']).value,
      coordinate: this.editForm.get(['coordinate']).value,
      company: this.editForm.get(['company']).value,
      createdTime:
        this.editForm.get(['createdTime']).value != null ? moment(this.editForm.get(['createdTime']).value, DATE_TIME_FORMAT) : undefined,
      stuId: this.editForm.get(['stuId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttendanceManagement>>) {
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
