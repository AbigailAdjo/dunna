import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPartner, Partner } from 'app/shared/model/partner.model';
import { PartnerService } from './partner.service';
import { PartnerComponent } from './partner.component';
import { PartnerDetailComponent } from './partner-detail.component';
import { PartnerUpdateComponent } from './partner-update.component';

@Injectable({ providedIn: 'root' })
export class PartnerResolve implements Resolve<IPartner> {
  constructor(private service: PartnerService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPartner> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((partner: HttpResponse<Partner>) => {
          if (partner.body) {
            return of(partner.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Partner());
  }
}

export const partnerRoute: Routes = [
  {
    path: '',
    component: PartnerComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'dunnaApp.partner.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartnerDetailComponent,
    resolve: {
      partner: PartnerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'dunnaApp.partner.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartnerUpdateComponent,
    resolve: {
      partner: PartnerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'dunnaApp.partner.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartnerUpdateComponent,
    resolve: {
      partner: PartnerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'dunnaApp.partner.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
