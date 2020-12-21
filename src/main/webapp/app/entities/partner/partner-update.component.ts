import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPartner, Partner } from 'app/shared/model/partner.model';
import { PartnerService } from './partner.service';
import { IVisitor } from 'app/shared/model/visitor.model';
import { VisitorService } from 'app/entities/visitor/visitor.service';

@Component({
  selector: 'jhi-partner-update',
  templateUrl: './partner-update.component.html',
})
export class PartnerUpdateComponent implements OnInit {
  isSaving = false;
  visitors: IVisitor[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    createdAt: [],
    updatedAt: [],
    createdBy: [],
    updatedBy: [],
    email: [],
    address: [],
    phone: [],
    partnerType: [],
    visitor: [],
  });

  constructor(
    protected partnerService: PartnerService,
    protected visitorService: VisitorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partner }) => {
      if (!partner.id) {
        const today = moment().startOf('day');
        partner.createdAt = today;
        partner.updatedAt = today;
      }

      this.updateForm(partner);

      this.visitorService.query().subscribe((res: HttpResponse<IVisitor[]>) => (this.visitors = res.body || []));
    });
  }

  updateForm(partner: IPartner): void {
    this.editForm.patchValue({
      id: partner.id,
      name: partner.name,
      createdAt: partner.createdAt ? partner.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: partner.updatedAt ? partner.updatedAt.format(DATE_TIME_FORMAT) : null,
      createdBy: partner.createdBy,
      updatedBy: partner.updatedBy,
      email: partner.email,
      address: partner.address,
      phone: partner.phone,
      partnerType: partner.partnerType,
      visitor: partner.visitor,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partner = this.createFromForm();
    if (partner.id !== undefined) {
      this.subscribeToSaveResponse(this.partnerService.update(partner));
    } else {
      this.subscribeToSaveResponse(this.partnerService.create(partner));
    }
  }

  private createFromForm(): IPartner {
    return {
      ...new Partner(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? moment(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      email: this.editForm.get(['email'])!.value,
      address: this.editForm.get(['address'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      partnerType: this.editForm.get(['partnerType'])!.value,
      visitor: this.editForm.get(['visitor'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartner>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IVisitor): any {
    return item.id;
  }
}
