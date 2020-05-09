import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJobChangeRecords } from 'app/shared/model/job-change-records.model';
import { JobChangeRecordsService } from './job-change-records.service';

@Component({
  selector: 'jhi-job-change-records-delete-dialog',
  templateUrl: './job-change-records-delete-dialog.component.html'
})
export class JobChangeRecordsDeleteDialogComponent {
  jobChangeRecords: IJobChangeRecords;

  constructor(
    protected jobChangeRecordsService: JobChangeRecordsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.jobChangeRecordsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'jobChangeRecordsListModification',
        content: 'Deleted an jobChangeRecords'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-job-change-records-delete-popup',
  template: ''
})
export class JobChangeRecordsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ jobChangeRecords }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(JobChangeRecordsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.jobChangeRecords = jobChangeRecords;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/job-change-records', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/job-change-records', { outlets: { popup: null } }]);
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
