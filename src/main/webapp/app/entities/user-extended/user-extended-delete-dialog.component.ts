import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserExtended } from 'app/shared/model/user-extended.model';
import { UserExtendedService } from './user-extended.service';

@Component({
  templateUrl: './user-extended-delete-dialog.component.html',
})
export class UserExtendedDeleteDialogComponent {
  userExtended?: IUserExtended;

  constructor(
    protected userExtendedService: UserExtendedService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userExtendedService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userExtendedListModification');
      this.activeModal.close();
    });
  }
}
