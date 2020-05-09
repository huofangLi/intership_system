import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IIntership, Intership } from 'app/shared/model/intership.model';
import { IntershipService } from './intership.service';
import { IStudent } from 'app/shared/model/student.model';
import { StudentService } from 'app/entities/student';

@Component({
  selector: 'jhi-intership-update',
  templateUrl: './intership-update.component.html'
})
export class IntershipUpdateComponent implements OnInit {
  isSaving: boolean;

  students: IStudent[];

  editForm = this.fb.group({
    id: [],
    batchName: [],
    practiceType: [],
    course: [],
    beginIntership: [],
    endIntership: [],
    tutorScore: [],
    masterScore: [],
    intershipScore: [],
    companyCreditCode: [],
    companyName: [],
    internship: [],
    companyContactName: [],
    companyContactJob: [],
    companyContactPhone: [],
    masterContactName: [],
    masterContactSkill: [],
    masterContactPhone: [],
    urgentContactName: [],
    urgentContactPhone: [],
    urgentContactAddress: [],
    accommodationType: [],
    accommodationAddress: [],
    companyAddress: [],
    companyDetailAddress: [],
    companyNature: [],
    scale: [],
    industry: [],
    companyProfile: [],
    isInsurance: [],
    insuranceCompanyAndPolicyNumber: [],
    isInternshipAgreement: [],
    annex: [],
    createTime: [],
    modifyTime: [],
    stuId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected intershipService: IntershipService,
    protected studentService: StudentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ intership }) => {
      this.updateForm(intership);
    });
    this.studentService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IStudent[]>) => mayBeOk.ok),
        map((response: HttpResponse<IStudent[]>) => response.body)
      )
      .subscribe((res: IStudent[]) => (this.students = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(intership: IIntership) {
    this.editForm.patchValue({
      id: intership.id,
      batchName: intership.batchName,
      practiceType: intership.practiceType,
      course: intership.course,
      beginIntership: intership.beginIntership != null ? intership.beginIntership.format(DATE_TIME_FORMAT) : null,
      endIntership: intership.endIntership != null ? intership.endIntership.format(DATE_TIME_FORMAT) : null,
      tutorScore: intership.tutorScore,
      masterScore: intership.masterScore,
      intershipScore: intership.intershipScore,
      companyCreditCode: intership.companyCreditCode,
      companyName: intership.companyName,
      internship: intership.internship,
      companyContactName: intership.companyContactName,
      companyContactJob: intership.companyContactJob,
      companyContactPhone: intership.companyContactPhone,
      masterContactName: intership.masterContactName,
      masterContactSkill: intership.masterContactSkill,
      masterContactPhone: intership.masterContactPhone,
      urgentContactName: intership.urgentContactName,
      urgentContactPhone: intership.urgentContactPhone,
      urgentContactAddress: intership.urgentContactAddress,
      accommodationType: intership.accommodationType,
      accommodationAddress: intership.accommodationAddress,
      companyAddress: intership.companyAddress,
      companyDetailAddress: intership.companyDetailAddress,
      companyNature: intership.companyNature,
      scale: intership.scale,
      industry: intership.industry,
      companyProfile: intership.companyProfile,
      isInsurance: intership.isInsurance,
      insuranceCompanyAndPolicyNumber: intership.insuranceCompanyAndPolicyNumber,
      isInternshipAgreement: intership.isInternshipAgreement,
      annex: intership.annex,
      createTime: intership.createTime != null ? intership.createTime.format(DATE_TIME_FORMAT) : null,
      modifyTime: intership.modifyTime != null ? intership.modifyTime.format(DATE_TIME_FORMAT) : null,
      stuId: intership.stuId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const intership = this.createFromForm();
    if (intership.id !== undefined) {
      this.subscribeToSaveResponse(this.intershipService.update(intership));
    } else {
      this.subscribeToSaveResponse(this.intershipService.create(intership));
    }
  }

  private createFromForm(): IIntership {
    return {
      ...new Intership(),
      id: this.editForm.get(['id']).value,
      batchName: this.editForm.get(['batchName']).value,
      practiceType: this.editForm.get(['practiceType']).value,
      course: this.editForm.get(['course']).value,
      beginIntership:
        this.editForm.get(['beginIntership']).value != null
          ? moment(this.editForm.get(['beginIntership']).value, DATE_TIME_FORMAT)
          : undefined,
      endIntership:
        this.editForm.get(['endIntership']).value != null ? moment(this.editForm.get(['endIntership']).value, DATE_TIME_FORMAT) : undefined,
      tutorScore: this.editForm.get(['tutorScore']).value,
      masterScore: this.editForm.get(['masterScore']).value,
      intershipScore: this.editForm.get(['intershipScore']).value,
      companyCreditCode: this.editForm.get(['companyCreditCode']).value,
      companyName: this.editForm.get(['companyName']).value,
      internship: this.editForm.get(['internship']).value,
      companyContactName: this.editForm.get(['companyContactName']).value,
      companyContactJob: this.editForm.get(['companyContactJob']).value,
      companyContactPhone: this.editForm.get(['companyContactPhone']).value,
      masterContactName: this.editForm.get(['masterContactName']).value,
      masterContactSkill: this.editForm.get(['masterContactSkill']).value,
      masterContactPhone: this.editForm.get(['masterContactPhone']).value,
      urgentContactName: this.editForm.get(['urgentContactName']).value,
      urgentContactPhone: this.editForm.get(['urgentContactPhone']).value,
      urgentContactAddress: this.editForm.get(['urgentContactAddress']).value,
      accommodationType: this.editForm.get(['accommodationType']).value,
      accommodationAddress: this.editForm.get(['accommodationAddress']).value,
      companyAddress: this.editForm.get(['companyAddress']).value,
      companyDetailAddress: this.editForm.get(['companyDetailAddress']).value,
      companyNature: this.editForm.get(['companyNature']).value,
      scale: this.editForm.get(['scale']).value,
      industry: this.editForm.get(['industry']).value,
      companyProfile: this.editForm.get(['companyProfile']).value,
      isInsurance: this.editForm.get(['isInsurance']).value,
      insuranceCompanyAndPolicyNumber: this.editForm.get(['insuranceCompanyAndPolicyNumber']).value,
      isInternshipAgreement: this.editForm.get(['isInternshipAgreement']).value,
      annex: this.editForm.get(['annex']).value,
      createTime:
        this.editForm.get(['createTime']).value != null ? moment(this.editForm.get(['createTime']).value, DATE_TIME_FORMAT) : undefined,
      modifyTime:
        this.editForm.get(['modifyTime']).value != null ? moment(this.editForm.get(['modifyTime']).value, DATE_TIME_FORMAT) : undefined,
      stuId: this.editForm.get(['stuId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIntership>>) {
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
