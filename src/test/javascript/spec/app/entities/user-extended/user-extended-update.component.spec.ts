import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DunnaTestModule } from '../../../test.module';
import { UserExtendedUpdateComponent } from 'app/entities/user-extended/user-extended-update.component';
import { UserExtendedService } from 'app/entities/user-extended/user-extended.service';
import { UserExtended } from 'app/shared/model/user-extended.model';

describe('Component Tests', () => {
  describe('UserExtended Management Update Component', () => {
    let comp: UserExtendedUpdateComponent;
    let fixture: ComponentFixture<UserExtendedUpdateComponent>;
    let service: UserExtendedService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DunnaTestModule],
        declarations: [UserExtendedUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserExtendedUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserExtendedUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserExtendedService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserExtended(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserExtended();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
