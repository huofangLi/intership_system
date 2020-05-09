import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIntership } from 'app/shared/model/intership.model';
import { IntershipService } from './intership.service';

@Component({
  selector: 'jhi-intership-delete-dialog',
  templateUrl: './intership-delete-dialog.component.html'
})
export class IntershipDeleteDialogComponent {
  intership: IIntership;

  constructor(protected intershipService: IntershipService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.intershipService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'intershipListModification',
        content: 'Deleted an intership'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-intership-delete-popup',
  template: ''
})
export class IntershipDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ intership }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(IntershipDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.intership = intership;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/intership', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/intership', { outlets: { popup: null } }]);
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
