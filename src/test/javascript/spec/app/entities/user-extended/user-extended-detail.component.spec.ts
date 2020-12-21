import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { DunnaTestModule } from '../../../test.module';
import { UserExtendedDetailComponent } from 'app/entities/user-extended/user-extended-detail.component';
import { UserExtended } from 'app/shared/model/user-extended.model';

describe('Component Tests', () => {
  describe('UserExtended Management Detail Component', () => {
    let comp: UserExtendedDetailComponent;
    let fixture: ComponentFixture<UserExtendedDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ userExtended: new UserExtended(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DunnaTestModule],
        declarations: [UserExtendedDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserExtendedDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserExtendedDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load userExtended on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userExtended).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
