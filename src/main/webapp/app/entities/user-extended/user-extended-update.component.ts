import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IUserExtended, UserExtended } from 'app/shared/model/user-extended.model';
import { UserExtendedService } from './user-extended.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-user-extended-update',
  templateUrl: './user-extended-update.component.html',
})
export class UserExtendedUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    genderType: [],
    phone: [null, [Validators.required]],
    responsible: [],
    createdAt: [],
    updatedAt: [],
    createdBy: [],
    updatedBy: [],
    avatar: [],
    avatarContentType: [],
    lastLogin: [],
    user: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected userExtendedService: UserExtendedService,
    protected userService: UserService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userExtended }) => {
      if (!userExtended.id) {
        const today = moment().startOf('day');
        userExtended.createdAt = today;
        userExtended.updatedAt = today;
        userExtended.lastLogin = today;
      }

      this.updateForm(userExtended);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(userExtended: IUserExtended): void {
    this.editForm.patchValue({
      id: userExtended.id,
      genderType: userExtended.genderType,
      phone: userExtended.phone,
      responsible: userExtended.responsible,
      createdAt: userExtended.createdAt ? userExtended.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: userExtended.updatedAt ? userExtended.updatedAt.format(DATE_TIME_FORMAT) : null,
      createdBy: userExtended.createdBy,
      updatedBy: userExtended.updatedBy,
      avatar: userExtended.avatar,
      avatarContentType: userExtended.avatarContentType,
      lastLogin: userExtended.lastLogin ? userExtended.lastLogin.format(DATE_TIME_FORMAT) : null,
      user: userExtended.user,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('dunnaApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userExtended = this.createFromForm();
    if (userExtended.id !== undefined) {
      this.subscribeToSaveResponse(this.userExtendedService.update(userExtended));
    } else {
      this.subscribeToSaveResponse(this.userExtendedService.create(userExtended));
    }
  }

  private createFromForm(): IUserExtended {
    return {
      ...new UserExtended(),
      id: this.editForm.get(['id'])!.value,
      genderType: this.editForm.get(['genderType'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      responsible: this.editForm.get(['responsible'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? moment(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      avatarContentType: this.editForm.get(['avatarContentType'])!.value,
      avatar: this.editForm.get(['avatar'])!.value,
      lastLogin: this.editForm.get(['lastLogin'])!.value ? moment(this.editForm.get(['lastLogin'])!.value, DATE_TIME_FORMAT) : undefined,
      user: this.editForm.get(['user'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserExtended>>): void {
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

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
