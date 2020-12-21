import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserExtended, UserExtended } from 'app/shared/model/user-extended.model';
import { UserExtendedService } from './user-extended.service';
import { UserExtendedComponent } from './user-extended.component';
import { UserExtendedDetailComponent } from './user-extended-detail.component';
import { UserExtendedUpdateComponent } from './user-extended-update.component';

@Injectable({ providedIn: 'root' })
export class UserExtendedResolve implements Resolve<IUserExtended> {
  constructor(private service: UserExtendedService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserExtended> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userExtended: HttpResponse<UserExtended>) => {
          if (userExtended.body) {
            return of(userExtended.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserExtended());
  }
}

export const userExtendedRoute: Routes = [
  {
    path: '',
    component: UserExtendedComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'dunnaApp.userExtended.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserExtendedDetailComponent,
    resolve: {
      userExtended: UserExtendedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'dunnaApp.userExtended.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserExtendedUpdateComponent,
    resolve: {
      userExtended: UserExtendedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'dunnaApp.userExtended.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserExtendedUpdateComponent,
    resolve: {
      userExtended: UserExtendedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'dunnaApp.userExtended.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
