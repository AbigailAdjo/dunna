import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DunnaTestModule } from '../../../test.module';
import { UserExtendedComponent } from 'app/entities/user-extended/user-extended.component';
import { UserExtendedService } from 'app/entities/user-extended/user-extended.service';
import { UserExtended } from 'app/shared/model/user-extended.model';

describe('Component Tests', () => {
  describe('UserExtended Management Component', () => {
    let comp: UserExtendedComponent;
    let fixture: ComponentFixture<UserExtendedComponent>;
    let service: UserExtendedService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DunnaTestModule],
        declarations: [UserExtendedComponent],
      })
        .overrideTemplate(UserExtendedComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserExtendedComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserExtendedService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UserExtended(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.userExtendeds && comp.userExtendeds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
