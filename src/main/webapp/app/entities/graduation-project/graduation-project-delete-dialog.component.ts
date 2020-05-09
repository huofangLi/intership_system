import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGraduationProject } from 'app/shared/model/graduation-project.model';
import { GraduationProjectService } from './graduation-project.service';

@Component({
  selector: 'jhi-graduation-project-delete-dialog',
  templateUrl: './graduation-project-delete-dialog.component.html'
})
export class GraduationProjectDeleteDialogComponent {
  graduationProject: IGraduationProject;

  constructor(
    protected graduationProjectService: GraduationProjectService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.graduationProjectService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'graduationProjectListModification',
        content: 'Deleted an graduationProject'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-graduation-project-delete-popup',
  template: ''
})
export class GraduationProjectDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ graduationProject }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(GraduationProjectDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.graduationProject = graduationProject;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/graduation-project', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/graduation-project', { outlets: { popup: null } }]);
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
