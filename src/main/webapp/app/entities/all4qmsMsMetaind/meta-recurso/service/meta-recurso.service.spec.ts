import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IMetaRecurso } from '../meta-recurso.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../meta-recurso.test-samples';

import { MetaRecursoService } from './meta-recurso.service';

const requireRestSample: IMetaRecurso = {
  ...sampleWithRequiredData,
};

describe('MetaRecurso Service', () => {
  let service: MetaRecursoService;
  let httpMock: HttpTestingController;
  let expectedResult: IMetaRecurso | IMetaRecurso[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(MetaRecursoService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a MetaRecurso', () => {
      const metaRecurso = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(metaRecurso).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MetaRecurso', () => {
      const metaRecurso = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(metaRecurso).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MetaRecurso', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MetaRecurso', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MetaRecurso', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMetaRecursoToCollectionIfMissing', () => {
      it('should add a MetaRecurso to an empty array', () => {
        const metaRecurso: IMetaRecurso = sampleWithRequiredData;
        expectedResult = service.addMetaRecursoToCollectionIfMissing([], metaRecurso);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(metaRecurso);
      });

      it('should not add a MetaRecurso to an array that contains it', () => {
        const metaRecurso: IMetaRecurso = sampleWithRequiredData;
        const metaRecursoCollection: IMetaRecurso[] = [
          {
            ...metaRecurso,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMetaRecursoToCollectionIfMissing(metaRecursoCollection, metaRecurso);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MetaRecurso to an array that doesn't contain it", () => {
        const metaRecurso: IMetaRecurso = sampleWithRequiredData;
        const metaRecursoCollection: IMetaRecurso[] = [sampleWithPartialData];
        expectedResult = service.addMetaRecursoToCollectionIfMissing(metaRecursoCollection, metaRecurso);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(metaRecurso);
      });

      it('should add only unique MetaRecurso to an array', () => {
        const metaRecursoArray: IMetaRecurso[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const metaRecursoCollection: IMetaRecurso[] = [sampleWithRequiredData];
        expectedResult = service.addMetaRecursoToCollectionIfMissing(metaRecursoCollection, ...metaRecursoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const metaRecurso: IMetaRecurso = sampleWithRequiredData;
        const metaRecurso2: IMetaRecurso = sampleWithPartialData;
        expectedResult = service.addMetaRecursoToCollectionIfMissing([], metaRecurso, metaRecurso2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(metaRecurso);
        expect(expectedResult).toContain(metaRecurso2);
      });

      it('should accept null and undefined values', () => {
        const metaRecurso: IMetaRecurso = sampleWithRequiredData;
        expectedResult = service.addMetaRecursoToCollectionIfMissing([], null, metaRecurso, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(metaRecurso);
      });

      it('should return initial array if no MetaRecurso is added', () => {
        const metaRecursoCollection: IMetaRecurso[] = [sampleWithRequiredData];
        expectedResult = service.addMetaRecursoToCollectionIfMissing(metaRecursoCollection, undefined, null);
        expect(expectedResult).toEqual(metaRecursoCollection);
      });
    });

    describe('compareMetaRecurso', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMetaRecurso(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMetaRecurso(entity1, entity2);
        const compareResult2 = service.compareMetaRecurso(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMetaRecurso(entity1, entity2);
        const compareResult2 = service.compareMetaRecurso(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMetaRecurso(entity1, entity2);
        const compareResult2 = service.compareMetaRecurso(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
