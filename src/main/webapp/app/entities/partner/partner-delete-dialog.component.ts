import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPartner } from 'app/shared/model/partner.model';
import { PartnerService } from './partner.service';

@Component({
  templateUrl: './partner-delete-dialog.component.html',
})
export class PartnerDeleteDialogComponent {
  partner?: IPartner;

  constructor(protected partnerService: PartnerService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partnerService.delete(id).subscribe(() => {
      this.eventManager.broadcast('partnerListModification');
      this.activeModal.close();
    });
  }
}
