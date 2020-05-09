import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IInternshipTask, InternshipTask } from 'app/shared/model/internship-task.model';
import { InternshipTaskService } from './internship-task.service';
import { IIntership } from 'app/shared/model/intership.model';
import { IntershipService } from 'app/entities/intership';

@Component({
  selector: 'jhi-internship-task-update',
  templateUrl: './internship-task-update.component.html'
})
export class InternshipTaskUpdateComponent implements OnInit {
  isSaving: boolean;

  interships: IIntership[];

  editForm = this.fb.group({
    id: [],
    taskTitle: [],
    founder: [],
    startTime: [],
    endTime: [],
    importance: [],
    complexity: [],
    taskAnnex: [],
    star: [],
    status: [],
    operating: [],
    createTime: [],
    interId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected internshipTaskService: InternshipTaskService,
    protected intershipService: IntershipService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ internshipTask }) => {
      this.updateForm(internshipTask);
    });
    this.intershipService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IIntership[]>) => mayBeOk.ok),
        map((response: HttpResponse<IIntership[]>) => response.body)
      )
      .subscribe((res: IIntership[]) => (this.interships = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(internshipTask: IInternshipTask) {
    this.editForm.patchValue({
      id: internshipTask.id,
      taskTitle: internshipTask.taskTitle,
      founder: internshipTask.founder,
      startTime: internshipTask.startTime != null ? internshipTask.startTime.format(DATE_TIME_FORMAT) : null,
      endTime: internshipTask.endTime != null ? internshipTask.endTime.format(DATE_TIME_FORMAT) : null,
      importance: internshipTask.importance,
      complexity: internshipTask.complexity,
      taskAnnex: internshipTask.taskAnnex,
      star: internshipTask.star,
      status: internshipTask.status,
      operating: internshipTask.operating,
      createTime: internshipTask.createTime != null ? internshipTask.createTime.format(DATE_TIME_FORMAT) : null,
      interId: internshipTask.interId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const internshipTask = this.createFromForm();
    if (internshipTask.id !== undefined) {
      this.subscribeToSaveResponse(this.internshipTaskService.update(internshipTask));
    } else {
      this.subscribeToSaveResponse(this.internshipTaskService.create(internshipTask));
    }
  }

  private createFromForm(): IInternshipTask {
    return {
      ...new InternshipTask(),
      id: this.editForm.get(['id']).value,
      taskTitle: this.editForm.get(['taskTitle']).value,
      founder: this.editForm.get(['founder']).value,
      startTime:
        this.editForm.get(['startTime']).value != null ? moment(this.editForm.get(['startTime']).value, DATE_TIME_FORMAT) : undefined,
      endTime: this.editForm.get(['endTime']).value != null ? moment(this.editForm.get(['endTime']).value, DATE_TIME_FORMAT) : undefined,
      importance: this.editForm.get(['importance']).value,
      complexity: this.editForm.get(['complexity']).value,
      taskAnnex: this.editForm.get(['taskAnnex']).value,
      star: this.editForm.get(['star']).value,
      status: this.editForm.get(['status']).value,
      operating: this.editForm.get(['operating']).value,
      createTime:
        this.editForm.get(['createTime']).value != null ? moment(this.editForm.get(['createTime']).value, DATE_TIME_FORMAT) : undefined,
      interId: this.editForm.get(['interId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInternshipTask>>) {
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
