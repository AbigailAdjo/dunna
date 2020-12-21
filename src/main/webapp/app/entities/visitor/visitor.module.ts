import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DunnaSharedModule } from 'app/shared/shared.module';
import { VisitorComponent } from './visitor.component';
import { VisitorDetailComponent } from './visitor-detail.component';
import { VisitorUpdateComponent } from './visitor-update.component';
import { VisitorDeleteDialogComponent } from './visitor-delete-dialog.component';
import { visitorRoute } from './visitor.route';

@NgModule({
  imports: [DunnaSharedModule, RouterModule.forChild(visitorRoute)],
  declarations: [VisitorComponent, VisitorDetailComponent, VisitorUpdateComponent, VisitorDeleteDialogComponent],
  entryComponents: [VisitorDeleteDialogComponent],
})
export class DunnaVisitorModule {}
