import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DunnaTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { UserExtendedDeleteDialogComponent } from 'app/entities/user-extended/user-extended-delete-dialog.component';
import { UserExtendedService } from 'app/entities/user-extended/user-extended.service';

describe('Component Tests', () => {
  describe('UserExtended Management Delete Component', () => {
    let comp: UserExtendedDeleteDialogComponent;
    let fixture: ComponentFixture<UserExtendedDeleteDialogComponent>;
    let service: UserExtendedService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DunnaTestModule],
        declarations: [UserExtendedDeleteDialogComponent],
      })
        .overrideTemplate(UserExtendedDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserExtendedDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserExtendedService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
