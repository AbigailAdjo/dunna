import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IRegion, Region } from 'app/shared/model/region.model';
import { RegionService } from './region.service';

@Component({
  selector: 'jhi-region-update',
  templateUrl: './region-update.component.html',
})
export class RegionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    regionName: [null, [Validators.required]],
    regionCode: [null, [Validators.required]],
    createdAt: [],
    updatedAt: [],
    createdBy: [],
    updatedBy: [],
  });

  constructor(protected regionService: RegionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ region }) => {
      if (!region.id) {
        const today = moment().startOf('day');
        region.createdAt = today;
        region.updatedAt = today;
      }

      this.updateForm(region);
    });
  }

  updateForm(region: IRegion): void {
    this.editForm.patchValue({
      id: region.id,
      regionName: region.regionName,
      regionCode: region.regionCode,
      createdAt: region.createdAt ? region.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: region.updatedAt ? region.updatedAt.format(DATE_TIME_FORMAT) : null,
      createdBy: region.createdBy,
      updatedBy: region.updatedBy,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const region = this.createFromForm();
    if (region.id !== undefined) {
      this.subscribeToSaveResponse(this.regionService.update(region));
    } else {
      this.subscribeToSaveResponse(this.regionService.create(region));
    }
  }

  private createFromForm(): IRegion {
    return {
      ...new Region(),
      id: this.editForm.get(['id'])!.value,
      regionName: this.editForm.get(['regionName'])!.value,
      regionCode: this.editForm.get(['regionCode'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? moment(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRegion>>): void {
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
}
