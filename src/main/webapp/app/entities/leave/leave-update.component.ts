import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ILeave, Leave } from 'app/shared/model/leave.model';
import { LeaveService } from './leave.service';
import { IStudent } from 'app/shared/model/student.model';
import { StudentService } from 'app/entities/student';

@Component({
  selector: 'jhi-leave-update',
  templateUrl: './leave-update.component.html'
})
export class LeaveUpdateComponent implements OnInit {
  isSaving: boolean;

  students: IStudent[];

  editForm = this.fb.group({
    id: [],
    submitTime: [],
    leaveTime: [],
    leaveDays: [],
    company: [],
    leaveReason: [],
    status: [],
    createdTime: [],
    stuId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected leaveService: LeaveService,
    protected studentService: StudentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ leave }) => {
      this.updateForm(leave);
    });
    this.studentService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IStudent[]>) => mayBeOk.ok),
        map((response: HttpResponse<IStudent[]>) => response.body)
      )
      .subscribe((res: IStudent[]) => (this.students = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(leave: ILeave) {
    this.editForm.patchValue({
      id: leave.id,
      submitTime: leave.submitTime != null ? leave.submitTime.format(DATE_TIME_FORMAT) : null,
      leaveTime: leave.leaveTime != null ? leave.leaveTime.format(DATE_TIME_FORMAT) : null,
      leaveDays: leave.leaveDays,
      company: leave.company,
      leaveReason: leave.leaveReason,
      status: leave.status,
      createdTime: leave.createdTime != null ? leave.createdTime.format(DATE_TIME_FORMAT) : null,
      stuId: leave.stuId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const leave = this.createFromForm();
    if (leave.id !== undefined) {
      this.subscribeToSaveResponse(this.leaveService.update(leave));
    } else {
      this.subscribeToSaveResponse(this.leaveService.create(leave));
    }
  }

  private createFromForm(): ILeave {
    return {
      ...new Leave(),
      id: this.editForm.get(['id']).value,
      submitTime:
        this.editForm.get(['submitTime']).value != null ? moment(this.editForm.get(['submitTime']).value, DATE_TIME_FORMAT) : undefined,
      leaveTime:
        this.editForm.get(['leaveTime']).value != null ? moment(this.editForm.get(['leaveTime']).value, DATE_TIME_FORMAT) : undefined,
      leaveDays: this.editForm.get(['leaveDays']).value,
      company: this.editForm.get(['company']).value,
      leaveReason: this.editForm.get(['leaveReason']).value,
      status: this.editForm.get(['status']).value,
      createdTime:
        this.editForm.get(['createdTime']).value != null ? moment(this.editForm.get(['createdTime']).value, DATE_TIME_FORMAT) : undefined,
      stuId: this.editForm.get(['stuId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILeave>>) {
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
