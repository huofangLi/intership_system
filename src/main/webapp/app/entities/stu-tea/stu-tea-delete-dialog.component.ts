import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStuTea } from 'app/shared/model/stu-tea.model';
import { StuTeaService } from './stu-tea.service';

@Component({
  selector: 'jhi-stu-tea-delete-dialog',
  templateUrl: './stu-tea-delete-dialog.component.html'
})
export class StuTeaDeleteDialogComponent {
  stuTea: IStuTea;

  constructor(protected stuTeaService: StuTeaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.stuTeaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'stuTeaListModification',
        content: 'Deleted an stuTea'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-stu-tea-delete-popup',
  template: ''
})
export class StuTeaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ stuTea }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(StuTeaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.stuTea = stuTea;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/stu-tea', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/stu-tea', { outlets: { popup: null } }]);
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
