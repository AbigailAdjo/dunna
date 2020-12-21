import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IVisitor, Visitor } from 'app/shared/model/visitor.model';
import { VisitorService } from './visitor.service';

@Component({
  selector: 'jhi-visitor-update',
  templateUrl: './visitor-update.component.html',
})
export class VisitorUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    genderType: [],
    name: [],
    lastname: [],
    idcardType: [],
    cardExpiringDate: [],
    birthDate: [],
    cardNumber: [],
    cardCountry: [],
    nationality: [],
  });

  constructor(protected visitorService: VisitorService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ visitor }) => {
      this.updateForm(visitor);
    });
  }

  updateForm(visitor: IVisitor): void {
    this.editForm.patchValue({
      id: visitor.id,
      genderType: visitor.genderType,
      name: visitor.name,
      lastname: visitor.lastname,
      idcardType: visitor.idcardType,
      cardExpiringDate: visitor.cardExpiringDate,
      birthDate: visitor.birthDate,
      cardNumber: visitor.cardNumber,
      cardCountry: visitor.cardCountry,
      nationality: visitor.nationality,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const visitor = this.createFromForm();
    if (visitor.id !== undefined) {
      this.subscribeToSaveResponse(this.visitorService.update(visitor));
    } else {
      this.subscribeToSaveResponse(this.visitorService.create(visitor));
    }
  }

  private createFromForm(): IVisitor {
    return {
      ...new Visitor(),
      id: this.editForm.get(['id'])!.value,
      genderType: this.editForm.get(['genderType'])!.value,
      name: this.editForm.get(['name'])!.value,
      lastname: this.editForm.get(['lastname'])!.value,
      idcardType: this.editForm.get(['idcardType'])!.value,
      cardExpiringDate: this.editForm.get(['cardExpiringDate'])!.value,
      birthDate: this.editForm.get(['birthDate'])!.value,
      cardNumber: this.editForm.get(['cardNumber'])!.value,
      cardCountry: this.editForm.get(['cardCountry'])!.value,
      nationality: this.editForm.get(['nationality'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVisitor>>): void {
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
