import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAttendanceRecord } from 'app/shared/model/attendance-record.model';
import { AttendanceRecordService } from './attendance-record.service';

@Component({
  selector: 'jhi-attendance-record-delete-dialog',
  templateUrl: './attendance-record-delete-dialog.component.html'
})
export class AttendanceRecordDeleteDialogComponent {
  attendanceRecord: IAttendanceRecord;

  constructor(
    protected attendanceRecordService: AttendanceRecordService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.attendanceRecordService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'attendanceRecordListModification',
        content: 'Deleted an attendanceRecord'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-attendance-record-delete-popup',
  template: ''
})
export class AttendanceRecordDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ attendanceRecord }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AttendanceRecordDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.attendanceRecord = attendanceRecord;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/attendance-record', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/attendance-record', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
