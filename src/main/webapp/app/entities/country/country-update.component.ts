import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICountry, Country } from 'app/shared/model/country.model';
import { CountryService } from './country.service';
import { IRegion } from 'app/shared/model/region.model';
import { RegionService } from 'app/entities/region/region.service';
import { IVisitor } from 'app/shared/model/visitor.model';
import { VisitorService } from 'app/entities/visitor/visitor.service';
import { IPartner } from 'app/shared/model/partner.model';
import { PartnerService } from 'app/entities/partner/partner.service';

type SelectableEntity = IRegion | IVisitor | IPartner;

@Component({
  selector: 'jhi-country-update',
  templateUrl: './country-update.component.html',
})
export class CountryUpdateComponent implements OnInit {
  isSaving = false;
  regions: IRegion[] = [];
  visitors: IVisitor[] = [];
  partners: IPartner[] = [];

  editForm = this.fb.group({
    id: [],
    countryName: [null, [Validators.required]],
    countryCodeAlpha: [null, [Validators.required, Validators.maxLength(3)]],
    countryCode: [null, [Validators.required]],
    countryFlag: [],
    createdAt: [],
    updatedAt: [],
    createdBy: [],
    updatedBy: [],
    region: [],
    visitor: [],
    visitor: [],
    partner: [],
  });

  constructor(
    protected countryService: CountryService,
    protected regionService: RegionService,
    protected visitorService: VisitorService,
    protected partnerService: PartnerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ country }) => {
      if (!country.id) {
        const today = moment().startOf('day');
        country.createdAt = today;
        country.updatedAt = today;
      }

      this.updateForm(country);

      this.regionService.query().subscribe((res: HttpResponse<IRegion[]>) => (this.regions = res.body || []));

      this.visitorService.query().subscribe((res: HttpResponse<IVisitor[]>) => (this.visitors = res.body || []));

      this.partnerService.query().subscribe((res: HttpResponse<IPartner[]>) => (this.partners = res.body || []));
    });
  }

  updateForm(country: ICountry): void {
    this.editForm.patchValue({
      id: country.id,
      countryName: country.countryName,
      countryCodeAlpha: country.countryCodeAlpha,
      countryCode: country.countryCode,
      countryFlag: country.countryFlag,
      createdAt: country.createdAt ? country.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: country.updatedAt ? country.updatedAt.format(DATE_TIME_FORMAT) : null,
      createdBy: country.createdBy,
      updatedBy: country.updatedBy,
      region: country.region,
      visitor: country.visitor,
      visitor: country.visitor,
      partner: country.partner,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const country = this.createFromForm();
    if (country.id !== undefined) {
      this.subscribeToSaveResponse(this.countryService.update(country));
    } else {
      this.subscribeToSaveResponse(this.countryService.create(country));
    }
  }

  private createFromForm(): ICountry {
    return {
      ...new Country(),
      id: this.editForm.get(['id'])!.value,
      countryName: this.editForm.get(['countryName'])!.value,
      countryCodeAlpha: this.editForm.get(['countryCodeAlpha'])!.value,
      countryCode: this.editForm.get(['countryCode'])!.value,
      countryFlag: this.editForm.get(['countryFlag'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? moment(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      region: this.editForm.get(['region'])!.value,
      visitor: this.editForm.get(['visitor'])!.value,
      visitor: this.editForm.get(['visitor'])!.value,
      partner: this.editForm.get(['partner'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICountry>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
