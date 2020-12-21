import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserExtended } from 'app/shared/model/user-extended.model';
import { UserExtendedService } from './user-extended.service';
import { UserExtendedDeleteDialogComponent } from './user-extended-delete-dialog.component';

@Component({
  selector: 'jhi-user-extended',
  templateUrl: './user-extended.component.html',
})
export class UserExtendedComponent implements OnInit, OnDestroy {
  userExtendeds?: IUserExtended[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected userExtendedService: UserExtendedService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.userExtendedService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IUserExtended[]>) => (this.userExtendeds = res.body || []));
      return;
    }

    this.userExtendedService.query().subscribe((res: HttpResponse<IUserExtended[]>) => (this.userExtendeds = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInUserExtendeds();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUserExtended): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInUserExtendeds(): void {
    this.eventSubscriber = this.eventManager.subscribe('userExtendedListModification', () => this.loadAll());
  }

  delete(userExtended: IUserExtended): void {
    const modalRef = this.modalService.open(UserExtendedDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.userExtended = userExtended;
  }
}
