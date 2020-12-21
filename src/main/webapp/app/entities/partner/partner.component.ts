import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPartner } from 'app/shared/model/partner.model';
import { PartnerService } from './partner.service';
import { PartnerDeleteDialogComponent } from './partner-delete-dialog.component';

@Component({
  selector: 'jhi-partner',
  templateUrl: './partner.component.html',
})
export class PartnerComponent implements OnInit, OnDestroy {
  partners?: IPartner[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected partnerService: PartnerService,
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
      this.partnerService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IPartner[]>) => (this.partners = res.body || []));
      return;
    }

    this.partnerService.query().subscribe((res: HttpResponse<IPartner[]>) => (this.partners = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPartners();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPartner): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPartners(): void {
    this.eventSubscriber = this.eventManager.subscribe('partnerListModification', () => this.loadAll());
  }

  delete(partner: IPartner): void {
    const modalRef = this.modalService.open(PartnerDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.partner = partner;
  }
}
