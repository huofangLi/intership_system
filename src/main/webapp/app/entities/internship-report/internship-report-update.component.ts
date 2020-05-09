import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IInternshipReport, InternshipReport } from 'app/shared/model/internship-report.model';
import { InternshipReportService } from './internship-report.service';
import { IIntership } from 'app/shared/model/intership.model';
import { IntershipService } from 'app/entities/intership';

@Component({
  selector: 'jhi-internship-report-update',
  templateUrl: './internship-report-update.component.html'
})
export class InternshipReportUpdateComponent implements OnInit {
  isSaving: boolean;

  interships: IIntership[];

  editForm = this.fb.group({
    id: [],
    reportType: [],
    projectName: [],
    intershipStage: [],
    annex: [],
    reportContent: [],
    createTime: [],
    star: [],
    status: [],
    operating: [],
    interId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected internshipReportService: InternshipReportService,
    protected intershipService: IntershipService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ internshipReport }) => {
      this.updateForm(internshipReport);
    });
    this.intershipService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IIntership[]>) => mayBeOk.ok),
        map((response: HttpResponse<IIntership[]>) => response.body)
      )
      .subscribe((res: IIntership[]) => (this.interships = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(internshipReport: IInternshipReport) {
    this.editForm.patchValue({
      id: internshipReport.id,
      reportType: internshipReport.reportType,
      projectName: internshipReport.projectName,
      intershipStage: internshipReport.intershipStage,
      annex: internshipReport.annex,
      reportContent: internshipReport.reportContent,
      createTime: internshipReport.createTime != null ? internshipReport.createTime.format(DATE_TIME_FORMAT) : null,
      star: internshipReport.star,
      status: internshipReport.status,
      operating: internshipReport.operating,
      interId: internshipReport.interId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const internshipReport = this.createFromForm();
    if (internshipReport.id !== undefined) {
      this.subscribeToSaveResponse(this.internshipReportService.update(internshipReport));
    } else {
      this.subscribeToSaveResponse(this.internshipReportService.create(internshipReport));
    }
  }

  private createFromForm(): IInternshipReport {
    return {
      ...new InternshipReport(),
      id: this.editForm.get(['id']).value,
      reportType: this.editForm.get(['reportType']).value,
      projectName: this.editForm.get(['projectName']).value,
      intershipStage: this.editForm.get(['intershipStage']).value,
      annex: this.editForm.get(['annex']).value,
      reportContent: this.editForm.get(['reportContent']).value,
      createTime:
        this.editForm.get(['createTime']).value != null ? moment(this.editForm.get(['createTime']).value, DATE_TIME_FORMAT) : undefined,
      star: this.editForm.get(['star']).value,
      status: this.editForm.get(['status']).value,
      operating: this.editForm.get(['operating']).value,
      interId: this.editForm.get(['interId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInternshipReport>>) {
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
