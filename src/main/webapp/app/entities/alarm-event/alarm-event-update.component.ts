import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IAlarmEvent, AlarmEvent } from 'app/shared/model/alarm-event.model';
import { AlarmEventService } from './alarm-event.service';
import { IStudent } from 'app/shared/model/student.model';
import { StudentService } from 'app/entities/student';

@Component({
  selector: 'jhi-alarm-event-update',
  templateUrl: './alarm-event-update.component.html'
})
export class AlarmEventUpdateComponent implements OnInit {
  isSaving: boolean;

  students: IStudent[];

  editForm = this.fb.group({
    id: [],
    title: [],
    content: [],
    remarks: [],
    createdTime: [],
    stuId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected alarmEventService: AlarmEventService,
    protected studentService: StudentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ alarmEvent }) => {
      this.updateForm(alarmEvent);
    });
    this.studentService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IStudent[]>) => mayBeOk.ok),
        map((response: HttpResponse<IStudent[]>) => response.body)
      )
      .subscribe((res: IStudent[]) => (this.students = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(alarmEvent: IAlarmEvent) {
    this.editForm.patchValue({
      id: alarmEvent.id,
      title: alarmEvent.title,
      content: alarmEvent.content,
      remarks: alarmEvent.remarks,
      createdTime: alarmEvent.createdTime != null ? alarmEvent.createdTime.format(DATE_TIME_FORMAT) : null,
      stuId: alarmEvent.stuId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const alarmEvent = this.createFromForm();
    if (alarmEvent.id !== undefined) {
      this.subscribeToSaveResponse(this.alarmEventService.update(alarmEvent));
    } else {
      this.subscribeToSaveResponse(this.alarmEventService.create(alarmEvent));
    }
  }

  private createFromForm(): IAlarmEvent {
    return {
      ...new AlarmEvent(),
      id: this.editForm.get(['id']).value,
      title: this.editForm.get(['title']).value,
      content: this.editForm.get(['content']).value,
      remarks: this.editForm.get(['remarks']).value,
      createdTime:
        this.editForm.get(['createdTime']).value != null ? moment(this.editForm.get(['createdTime']).value, DATE_TIME_FORMAT) : undefined,
      stuId: this.editForm.get(['stuId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAlarmEvent>>) {
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
