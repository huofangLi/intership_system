import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IGraduationProject, GraduationProject } from 'app/shared/model/graduation-project.model';
import { GraduationProjectService } from './graduation-project.service';
import { IStudent } from 'app/shared/model/student.model';
import { StudentService } from 'app/entities/student';

@Component({
  selector: 'jhi-graduation-project-update',
  templateUrl: './graduation-project-update.component.html'
})
export class GraduationProjectUpdateComponent implements OnInit {
  isSaving: boolean;

  students: IStudent[];

  editForm = this.fb.group({
    id: [],
    graduationProjectTitle: [],
    source: [],
    taskBook: [],
    taskBookCheck: [],
    taskBookReviews: [],
    openingReport: [],
    openingReportCheck: [],
    openingReportReviews: [],
    midTermInspection: [],
    midTermInspectionCheck: [],
    midTermInspectionReviews: [],
    firstDraft: [],
    firstDraftCheck: [],
    firstDraftReviews: [],
    finalDraft: [],
    finalDraftCheck: [],
    finalDraftReviews: [],
    createdTime: [],
    modifyTime: [],
    stuId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected graduationProjectService: GraduationProjectService,
    protected studentService: StudentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ graduationProject }) => {
      this.updateForm(graduationProject);
    });
    this.studentService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IStudent[]>) => mayBeOk.ok),
        map((response: HttpResponse<IStudent[]>) => response.body)
      )
      .subscribe((res: IStudent[]) => (this.students = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(graduationProject: IGraduationProject) {
    this.editForm.patchValue({
      id: graduationProject.id,
      graduationProjectTitle: graduationProject.graduationProjectTitle,
      source: graduationProject.source,
      taskBook: graduationProject.taskBook,
      taskBookCheck: graduationProject.taskBookCheck,
      taskBookReviews: graduationProject.taskBookReviews,
      openingReport: graduationProject.openingReport,
      openingReportCheck: graduationProject.openingReportCheck,
      openingReportReviews: graduationProject.openingReportReviews,
      midTermInspection: graduationProject.midTermInspection,
      midTermInspectionCheck: graduationProject.midTermInspectionCheck,
      midTermInspectionReviews: graduationProject.midTermInspectionReviews,
      firstDraft: graduationProject.firstDraft,
      firstDraftCheck: graduationProject.firstDraftCheck,
      firstDraftReviews: graduationProject.firstDraftReviews,
      finalDraft: graduationProject.finalDraft,
      finalDraftCheck: graduationProject.finalDraftCheck,
      finalDraftReviews: graduationProject.finalDraftReviews,
      createdTime: graduationProject.createdTime != null ? graduationProject.createdTime.format(DATE_TIME_FORMAT) : null,
      modifyTime: graduationProject.modifyTime != null ? graduationProject.modifyTime.format(DATE_TIME_FORMAT) : null,
      stuId: graduationProject.stuId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const graduationProject = this.createFromForm();
    if (graduationProject.id !== undefined) {
      this.subscribeToSaveResponse(this.graduationProjectService.update(graduationProject));
    } else {
      this.subscribeToSaveResponse(this.graduationProjectService.create(graduationProject));
    }
  }

  private createFromForm(): IGraduationProject {
    return {
      ...new GraduationProject(),
      id: this.editForm.get(['id']).value,
      graduationProjectTitle: this.editForm.get(['graduationProjectTitle']).value,
      source: this.editForm.get(['source']).value,
      taskBook: this.editForm.get(['taskBook']).value,
      taskBookCheck: this.editForm.get(['taskBookCheck']).value,
      taskBookReviews: this.editForm.get(['taskBookReviews']).value,
      openingReport: this.editForm.get(['openingReport']).value,
      openingReportCheck: this.editForm.get(['openingReportCheck']).value,
      openingReportReviews: this.editForm.get(['openingReportReviews']).value,
      midTermInspection: this.editForm.get(['midTermInspection']).value,
      midTermInspectionCheck: this.editForm.get(['midTermInspectionCheck']).value,
      midTermInspectionReviews: this.editForm.get(['midTermInspectionReviews']).value,
      firstDraft: this.editForm.get(['firstDraft']).value,
      firstDraftCheck: this.editForm.get(['firstDraftCheck']).value,
      firstDraftReviews: this.editForm.get(['firstDraftReviews']).value,
      finalDraft: this.editForm.get(['finalDraft']).value,
      finalDraftCheck: this.editForm.get(['finalDraftCheck']).value,
      finalDraftReviews: this.editForm.get(['finalDraftReviews']).value,
      createdTime:
        this.editForm.get(['createdTime']).value != null ? moment(this.editForm.get(['createdTime']).value, DATE_TIME_FORMAT) : undefined,
      modifyTime:
        this.editForm.get(['modifyTime']).value != null ? moment(this.editForm.get(['modifyTime']).value, DATE_TIME_FORMAT) : undefined,
      stuId: this.editForm.get(['stuId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGraduationProject>>) {
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
