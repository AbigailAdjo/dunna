import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'partner',
        loadChildren: () => import('./partner/partner.module').then(m => m.DunnaPartnerModule),
      },
      {
        path: 'country',
        loadChildren: () => import('./country/country.module').then(m => m.DunnaCountryModule),
      },
      {
        path: 'region',
        loadChildren: () => import('./region/region.module').then(m => m.DunnaRegionModule),
      },
      {
        path: 'user-extended',
        loadChildren: () => import('./user-extended/user-extended.module').then(m => m.DunnaUserExtendedModule),
      },
      {
        path: 'visitor',
        loadChildren: () => import('./visitor/visitor.module').then(m => m.DunnaVisitorModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class DunnaEntityModule {}
