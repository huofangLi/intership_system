import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAbsenceRecord } from 'app/shared/model/absence-record.model';
import { AbsenceRecordService } from './absence-record.service';

@Component({
  selector: 'jhi-absence-record-delete-dialog',
  templateUrl: './absence-record-delete-dialog.component.html'
})
export class AbsenceRecordDeleteDialogComponent {
  absenceRecord: IAbsenceRecord;

  constructor(
    protected absenceRecordService: AbsenceRecordService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.absenceRecordService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'absenceRecordListModification',
        content: 'Deleted an absenceRecord'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-absence-record-delete-popup',
  template: ''
})
export class AbsenceRecordDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ absenceRecord }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AbsenceRecordDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.absenceRecord = absenceRecord;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/absence-record', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/absence-record', { outlets: { popup: null } }]);
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
