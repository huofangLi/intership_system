import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAlarmEvent } from 'app/shared/model/alarm-event.model';
import { AlarmEventService } from './alarm-event.service';

@Component({
  selector: 'jhi-alarm-event-delete-dialog',
  templateUrl: './alarm-event-delete-dialog.component.html'
})
export class AlarmEventDeleteDialogComponent {
  alarmEvent: IAlarmEvent;

  constructor(
    protected alarmEventService: AlarmEventService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.alarmEventService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'alarmEventListModification',
        content: 'Deleted an alarmEvent'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-alarm-event-delete-popup',
  template: ''
})
export class AlarmEventDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ alarmEvent }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AlarmEventDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.alarmEvent = alarmEvent;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/alarm-event', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/alarm-event', { outlets: { popup: null } }]);
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
