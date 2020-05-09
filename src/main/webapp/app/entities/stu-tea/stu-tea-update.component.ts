import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IStuTea, StuTea } from 'app/shared/model/stu-tea.model';
import { StuTeaService } from './stu-tea.service';
import { IStudent } from 'app/shared/model/student.model';
import { StudentService } from 'app/entities/student';
import { ITeacher } from 'app/shared/model/teacher.model';
import { TeacherService } from 'app/entities/teacher';

@Component({
  selector: 'jhi-stu-tea-update',
  templateUrl: './stu-tea-update.component.html'
})
export class StuTeaUpdateComponent implements OnInit {
  isSaving: boolean;

  students: IStudent[];

  teachers: ITeacher[];

  editForm = this.fb.group({
    id: [],
    createTime: [],
    stuId: [],
    teaId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected stuTeaService: StuTeaService,
    protected studentService: StudentService,
    protected teacherService: TeacherService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ stuTea }) => {
      this.updateForm(stuTea);
    });
    this.studentService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IStudent[]>) => mayBeOk.ok),
        map((response: HttpResponse<IStudent[]>) => response.body)
      )
      .subscribe((res: IStudent[]) => (this.students = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.teacherService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITeacher[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITeacher[]>) => response.body)
      )
      .subscribe((res: ITeacher[]) => (this.teachers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(stuTea: IStuTea) {
    this.editForm.patchValue({
      id: stuTea.id,
      createTime: stuTea.createTime != null ? stuTea.createTime.format(DATE_TIME_FORMAT) : null,
      stuId: stuTea.stuId,
      teaId: stuTea.teaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const stuTea = this.createFromForm();
    if (stuTea.id !== undefined) {
      this.subscribeToSaveResponse(this.stuTeaService.update(stuTea));
    } else {
      this.subscribeToSaveResponse(this.stuTeaService.create(stuTea));
    }
  }

  private createFromForm(): IStuTea {
    return {
      ...new StuTea(),
      id: this.editForm.get(['id']).value,
      createTime:
        this.editForm.get(['createTime']).value != null ? moment(this.editForm.get(['createTime']).value, DATE_TIME_FORMAT) : undefined,
      stuId: this.editForm.get(['stuId']).value,
      teaId: this.editForm.get(['teaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStuTea>>) {
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

  trackTeacherById(index: number, item: ITeacher) {
    return item.id;
  }
}
