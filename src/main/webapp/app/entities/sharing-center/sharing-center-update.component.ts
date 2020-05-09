import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ISharingCenter, SharingCenter } from 'app/shared/model/sharing-center.model';
import { SharingCenterService } from './sharing-center.service';
import { IStudent } from 'app/shared/model/student.model';
import { StudentService } from 'app/entities/student';

@Component({
  selector: 'jhi-sharing-center-update',
  templateUrl: './sharing-center-update.component.html'
})
export class SharingCenterUpdateComponent implements OnInit {
  isSaving: boolean;

  students: IStudent[];

  editForm = this.fb.group({
    id: [],
    fileName: [],
    fileSize: [],
    uploadedBy: [],
    department: [],
    operating: [],
    createdTime: [],
    modifyTime: [],
    stuId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected sharingCenterService: SharingCenterService,
    protected studentService: StudentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sharingCenter }) => {
      this.updateForm(sharingCenter);
    });
    this.studentService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IStudent[]>) => mayBeOk.ok),
        map((response: HttpResponse<IStudent[]>) => response.body)
      )
      .subscribe((res: IStudent[]) => (this.students = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(sharingCenter: ISharingCenter) {
    this.editForm.patchValue({
      id: sharingCenter.id,
      fileName: sharingCenter.fileName,
      fileSize: sharingCenter.fileSize,
      uploadedBy: sharingCenter.uploadedBy,
      department: sharingCenter.department,
      operating: sharingCenter.operating,
      createdTime: sharingCenter.createdTime != null ? sharingCenter.createdTime.format(DATE_TIME_FORMAT) : null,
      modifyTime: sharingCenter.modifyTime != null ? sharingCenter.modifyTime.format(DATE_TIME_FORMAT) : null,
      stuId: sharingCenter.stuId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sharingCenter = this.createFromForm();
    if (sharingCenter.id !== undefined) {
      this.subscribeToSaveResponse(this.sharingCenterService.update(sharingCenter));
    } else {
      this.subscribeToSaveResponse(this.sharingCenterService.create(sharingCenter));
    }
  }

  private createFromForm(): ISharingCenter {
    return {
      ...new SharingCenter(),
      id: this.editForm.get(['id']).value,
      fileName: this.editForm.get(['fileName']).value,
      fileSize: this.editForm.get(['fileSize']).value,
      uploadedBy: this.editForm.get(['uploadedBy']).value,
      department: this.editForm.get(['department']).value,
      operating: this.editForm.get(['operating']).value,
      createdTime:
        this.editForm.get(['createdTime']).value != null ? moment(this.editForm.get(['createdTime']).value, DATE_TIME_FORMAT) : undefined,
      modifyTime:
        this.editForm.get(['modifyTime']).value != null ? moment(this.editForm.get(['modifyTime']).value, DATE_TIME_FORMAT) : undefined,
      stuId: this.editForm.get(['stuId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISharingCenter>>) {
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
