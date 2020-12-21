import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DunnaSharedModule } from 'app/shared/shared.module';
import { UserExtendedComponent } from './user-extended.component';
import { UserExtendedDetailComponent } from './user-extended-detail.component';
import { UserExtendedUpdateComponent } from './user-extended-update.component';
import { UserExtendedDeleteDialogComponent } from './user-extended-delete-dialog.component';
import { userExtendedRoute } from './user-extended.route';

@NgModule({
  imports: [DunnaSharedModule, RouterModule.forChild(userExtendedRoute)],
  declarations: [UserExtendedComponent, UserExtendedDetailComponent, UserExtendedUpdateComponent, UserExtendedDeleteDialogComponent],
  entryComponents: [UserExtendedDeleteDialogComponent],
})
export class DunnaUserExtendedModule {}
