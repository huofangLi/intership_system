import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ITeacher, Teacher } from 'app/shared/model/teacher.model';
import { TeacherService } from './teacher.service';

@Component({
  selector: 'jhi-teacher-update',
  templateUrl: './teacher-update.component.html'
})
export class TeacherUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    teaId: [],
    teaName: [],
    gender: [],
    teaDepartment: [],
    skin: [],
    createdTime: [],
    modifyTime: []
  });

  constructor(protected teacherService: TeacherService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ teacher }) => {
      this.updateForm(teacher);
    });
  }

  updateForm(teacher: ITeacher) {
    this.editForm.patchValue({
      id: teacher.id,
      teaId: teacher.teaId,
      teaName: teacher.teaName,
      gender: teacher.gender,
      teaDepartment: teacher.teaDepartment,
      skin: teacher.skin,
      createdTime: teacher.createdTime != null ? teacher.createdTime.format(DATE_TIME_FORMAT) : null,
      modifyTime: teacher.modifyTime != null ? teacher.modifyTime.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const teacher = this.createFromForm();
    if (teacher.id !== undefined) {
      this.subscribeToSaveResponse(this.teacherService.update(teacher));
    } else {
      this.subscribeToSaveResponse(this.teacherService.create(teacher));
    }
  }

  private createFromForm(): ITeacher {
    return {
      ...new Teacher(),
      id: this.editForm.get(['id']).value,
      teaId: this.editForm.get(['teaId']).value,
      teaName: this.editForm.get(['teaName']).value,
      gender: this.editForm.get(['gender']).value,
      teaDepartment: this.editForm.get(['teaDepartment']).value,
      skin: this.editForm.get(['skin']).value,
      createdTime:
        this.editForm.get(['createdTime']).value != null ? moment(this.editForm.get(['createdTime']).value, DATE_TIME_FORMAT) : undefined,
      modifyTime:
        this.editForm.get(['modifyTime']).value != null ? moment(this.editForm.get(['modifyTime']).value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITeacher>>) {
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
