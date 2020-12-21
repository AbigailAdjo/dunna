import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { VisitorService } from 'app/entities/visitor/visitor.service';
import { IVisitor, Visitor } from 'app/shared/model/visitor.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { IdcardType } from 'app/shared/model/enumerations/idcard-type.model';

describe('Service Tests', () => {
  describe('Visitor Service', () => {
    let injector: TestBed;
    let service: VisitorService;
    let httpMock: HttpTestingController;
    let elemDefault: IVisitor;
    let expectedResult: IVisitor | IVisitor[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(VisitorService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Visitor(0, Gender.MALE, 'AAAAAAA', 'AAAAAAA', IdcardType.Idcard, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Visitor', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Visitor()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Visitor', () => {
        const returnedFromService = Object.assign(
          {
            genderType: 'BBBBBB',
            name: 'BBBBBB',
            lastname: 'BBBBBB',
            idcardType: 'BBBBBB',
            cardExpiringDate: 'BBBBBB',
            birthDate: 'BBBBBB',
            cardNumber: 'BBBBBB',
            cardCountry: 1,
            nationality: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Visitor', () => {
        const returnedFromService = Object.assign(
          {
            genderType: 'BBBBBB',
            name: 'BBBBBB',
            lastname: 'BBBBBB',
            idcardType: 'BBBBBB',
            cardExpiringDate: 'BBBBBB',
            birthDate: 'BBBBBB',
            cardNumber: 'BBBBBB',
            cardCountry: 1,
            nationality: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Visitor', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
