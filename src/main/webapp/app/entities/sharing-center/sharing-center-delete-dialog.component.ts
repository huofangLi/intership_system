import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISharingCenter } from 'app/shared/model/sharing-center.model';
import { SharingCenterService } from './sharing-center.service';

@Component({
  selector: 'jhi-sharing-center-delete-dialog',
  templateUrl: './sharing-center-delete-dialog.component.html'
})
export class SharingCenterDeleteDialogComponent {
  sharingCenter: ISharingCenter;

  constructor(
    protected sharingCenterService: SharingCenterService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.sharingCenterService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'sharingCenterListModification',
        content: 'Deleted an sharingCenter'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-sharing-center-delete-popup',
  template: ''
})
export class SharingCenterDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sharingCenter }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SharingCenterDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sharingCenter = sharingCenter;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/sharing-center', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/sharing-center', { outlets: { popup: null } }]);
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
