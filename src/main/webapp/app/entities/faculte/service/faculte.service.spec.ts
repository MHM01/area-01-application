import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFaculte, Faculte } from '../faculte.model';

import { FaculteService } from './faculte.service';

describe('Faculte Service', () => {
  let service: FaculteService;
  let httpMock: HttpTestingController;
  let elemDefault: IFaculte;
  let expectedResult: IFaculte | IFaculte[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FaculteService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nom: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Faculte', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Faculte()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Faculte', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nom: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Faculte', () => {
      const patchObject = Object.assign({}, new Faculte());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Faculte', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nom: 'BBBBBB',
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

    it('should delete a Faculte', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addFaculteToCollectionIfMissing', () => {
      it('should add a Faculte to an empty array', () => {
        const faculte: IFaculte = { id: 123 };
        expectedResult = service.addFaculteToCollectionIfMissing([], faculte);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(faculte);
      });

      it('should not add a Faculte to an array that contains it', () => {
        const faculte: IFaculte = { id: 123 };
        const faculteCollection: IFaculte[] = [
          {
            ...faculte,
          },
          { id: 456 },
        ];
        expectedResult = service.addFaculteToCollectionIfMissing(faculteCollection, faculte);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Faculte to an array that doesn't contain it", () => {
        const faculte: IFaculte = { id: 123 };
        const faculteCollection: IFaculte[] = [{ id: 456 }];
        expectedResult = service.addFaculteToCollectionIfMissing(faculteCollection, faculte);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(faculte);
      });

      it('should add only unique Faculte to an array', () => {
        const faculteArray: IFaculte[] = [{ id: 123 }, { id: 456 }, { id: 93435 }];
        const faculteCollection: IFaculte[] = [{ id: 123 }];
        expectedResult = service.addFaculteToCollectionIfMissing(faculteCollection, ...faculteArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const faculte: IFaculte = { id: 123 };
        const faculte2: IFaculte = { id: 456 };
        expectedResult = service.addFaculteToCollectionIfMissing([], faculte, faculte2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(faculte);
        expect(expectedResult).toContain(faculte2);
      });

      it('should accept null and undefined values', () => {
        const faculte: IFaculte = { id: 123 };
        expectedResult = service.addFaculteToCollectionIfMissing([], null, faculte, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(faculte);
      });

      it('should return initial array if no Faculte is added', () => {
        const faculteCollection: IFaculte[] = [{ id: 123 }];
        expectedResult = service.addFaculteToCollectionIfMissing(faculteCollection, undefined, null);
        expect(expectedResult).toEqual(faculteCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
