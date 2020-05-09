import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAttendanceManagement } from 'app/shared/model/attendance-management.model';
import { AttendanceManagementService } from './attendance-management.service';

@Component({
  selector: 'jhi-attendance-management-delete-dialog',
  templateUrl: './attendance-management-delete-dialog.component.html'
})
export class AttendanceManagementDeleteDialogComponent {
  attendanceManagement: IAttendanceManagement;

  constructor(
    protected attendanceManagementService: AttendanceManagementService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.attendanceManagementService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'attendanceManagementListModification',
        content: 'Deleted an attendanceManagement'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-attendance-management-delete-popup',
  template: ''
})
export class AttendanceManagementDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ attendanceManagement }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AttendanceManagementDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.attendanceManagement = attendanceManagement;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/attendance-management', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/attendance-management', { outlets: { popup: null } }]);
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
