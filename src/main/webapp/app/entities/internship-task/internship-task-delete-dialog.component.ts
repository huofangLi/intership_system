import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInternshipTask } from 'app/shared/model/internship-task.model';
import { InternshipTaskService } from './internship-task.service';

@Component({
  selector: 'jhi-internship-task-delete-dialog',
  templateUrl: './internship-task-delete-dialog.component.html'
})
export class InternshipTaskDeleteDialogComponent {
  internshipTask: IInternshipTask;

  constructor(
    protected internshipTaskService: InternshipTaskService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.internshipTaskService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'internshipTaskListModification',
        content: 'Deleted an internshipTask'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-internship-task-delete-popup',
  template: ''
})
export class InternshipTaskDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ internshipTask }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(InternshipTaskDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.internshipTask = internshipTask;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/internship-task', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/internship-task', { outlets: { popup: null } }]);
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
