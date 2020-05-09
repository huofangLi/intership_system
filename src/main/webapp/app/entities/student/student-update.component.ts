import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IStudent, Student } from 'app/shared/model/student.model';
import { StudentService } from './student.service';

@Component({
  selector: 'jhi-student-update',
  templateUrl: './student-update.component.html'
})
export class StudentUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    stuId: [],
    gender: [],
    stuClass: [],
    stuDepartment: [],
    stuProfession: [],
    phone: [],
    privince: [],
    privinceCode: [],
    city: [],
    cityCode: [],
    country: [],
    countryCode: [],
    address: [],
    hobby: [],
    skill: [],
    selfEvaluation: [],
    skin: [],
    createdTime: [],
    modifyTime: []
  });

  constructor(protected studentService: StudentService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ student }) => {
      this.updateForm(student);
    });
  }

  updateForm(student: IStudent) {
    this.editForm.patchValue({
      id: student.id,
      stuId: student.stuId,
      gender: student.gender,
      stuClass: student.stuClass,
      stuDepartment: student.stuDepartment,
      stuProfession: student.stuProfession,
      phone: student.phone,
      privince: student.privince,
      privinceCode: student.privinceCode,
      city: student.city,
      cityCode: student.cityCode,
      country: student.country,
      countryCode: student.countryCode,
      address: student.address,
      hobby: student.hobby,
      skill: student.skill,
      selfEvaluation: student.selfEvaluation,
      skin: student.skin,
      createdTime: student.createdTime != null ? student.createdTime.format(DATE_TIME_FORMAT) : null,
      modifyTime: student.modifyTime != null ? student.modifyTime.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const student = this.createFromForm();
    if (student.id !== undefined) {
      this.subscribeToSaveResponse(this.studentService.update(student));
    } else {
      this.subscribeToSaveResponse(this.studentService.create(student));
    }
  }

  private createFromForm(): IStudent {
    return {
      ...new Student(),
      id: this.editForm.get(['id']).value,
      stuId: this.editForm.get(['stuId']).value,
      gender: this.editForm.get(['gender']).value,
      stuClass: this.editForm.get(['stuClass']).value,
      stuDepartment: this.editForm.get(['stuDepartment']).value,
      stuProfession: this.editForm.get(['stuProfession']).value,
      phone: this.editForm.get(['phone']).value,
      privince: this.editForm.get(['privince']).value,
      privinceCode: this.editForm.get(['privinceCode']).value,
      city: this.editForm.get(['city']).value,
      cityCode: this.editForm.get(['cityCode']).value,
      country: this.editForm.get(['country']).value,
      countryCode: this.editForm.get(['countryCode']).value,
      address: this.editForm.get(['address']).value,
      hobby: this.editForm.get(['hobby']).value,
      skill: this.editForm.get(['skill']).value,
      selfEvaluation: this.editForm.get(['selfEvaluation']).value,
      skin: this.editForm.get(['skin']).value,
      createdTime:
        this.editForm.get(['createdTime']).value != null ? moment(this.editForm.get(['createdTime']).value, DATE_TIME_FORMAT) : undefined,
      modifyTime:
        this.editForm.get(['modifyTime']).value != null ? moment(this.editForm.get(['modifyTime']).value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudent>>) {
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
