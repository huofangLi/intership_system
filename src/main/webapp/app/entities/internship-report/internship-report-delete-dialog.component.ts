import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInternshipReport } from 'app/shared/model/internship-report.model';
import { InternshipReportService } from './internship-report.service';

@Component({
  selector: 'jhi-internship-report-delete-dialog',
  templateUrl: './internship-report-delete-dialog.component.html'
})
export class InternshipReportDeleteDialogComponent {
  internshipReport: IInternshipReport;

  constructor(
    protected internshipReportService: InternshipReportService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.internshipReportService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'internshipReportListModification',
        content: 'Deleted an internshipReport'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-internship-report-delete-popup',
  template: ''
})
export class InternshipReportDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ internshipReport }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(InternshipReportDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.internshipReport = internshipReport;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/internship-report', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/internship-report', { outlets: { popup: null } }]);
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
