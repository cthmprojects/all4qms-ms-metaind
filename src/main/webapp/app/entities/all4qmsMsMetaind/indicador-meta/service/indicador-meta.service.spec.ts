import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IIndicadorMeta } from '../indicador-meta.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../indicador-meta.test-samples';

import { IndicadorMetaService } from './indicador-meta.service';

const requireRestSample: IIndicadorMeta = {
  ...sampleWithRequiredData,
};

describe('IndicadorMeta Service', () => {
  let service: IndicadorMetaService;
  let httpMock: HttpTestingController;
  let expectedResult: IIndicadorMeta | IIndicadorMeta[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(IndicadorMetaService);
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

    it('should create a IndicadorMeta', () => {
      const indicadorMeta = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(indicadorMeta).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a IndicadorMeta', () => {
      const indicadorMeta = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(indicadorMeta).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a IndicadorMeta', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of IndicadorMeta', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a IndicadorMeta', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addIndicadorMetaToCollectionIfMissing', () => {
      it('should add a IndicadorMeta to an empty array', () => {
        const indicadorMeta: IIndicadorMeta = sampleWithRequiredData;
        expectedResult = service.addIndicadorMetaToCollectionIfMissing([], indicadorMeta);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(indicadorMeta);
      });

      it('should not add a IndicadorMeta to an array that contains it', () => {
        const indicadorMeta: IIndicadorMeta = sampleWithRequiredData;
        const indicadorMetaCollection: IIndicadorMeta[] = [
          {
            ...indicadorMeta,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addIndicadorMetaToCollectionIfMissing(indicadorMetaCollection, indicadorMeta);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a IndicadorMeta to an array that doesn't contain it", () => {
        const indicadorMeta: IIndicadorMeta = sampleWithRequiredData;
        const indicadorMetaCollection: IIndicadorMeta[] = [sampleWithPartialData];
        expectedResult = service.addIndicadorMetaToCollectionIfMissing(indicadorMetaCollection, indicadorMeta);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(indicadorMeta);
      });

      it('should add only unique IndicadorMeta to an array', () => {
        const indicadorMetaArray: IIndicadorMeta[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const indicadorMetaCollection: IIndicadorMeta[] = [sampleWithRequiredData];
        expectedResult = service.addIndicadorMetaToCollectionIfMissing(indicadorMetaCollection, ...indicadorMetaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const indicadorMeta: IIndicadorMeta = sampleWithRequiredData;
        const indicadorMeta2: IIndicadorMeta = sampleWithPartialData;
        expectedResult = service.addIndicadorMetaToCollectionIfMissing([], indicadorMeta, indicadorMeta2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(indicadorMeta);
        expect(expectedResult).toContain(indicadorMeta2);
      });

      it('should accept null and undefined values', () => {
        const indicadorMeta: IIndicadorMeta = sampleWithRequiredData;
        expectedResult = service.addIndicadorMetaToCollectionIfMissing([], null, indicadorMeta, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(indicadorMeta);
      });

      it('should return initial array if no IndicadorMeta is added', () => {
        const indicadorMetaCollection: IIndicadorMeta[] = [sampleWithRequiredData];
        expectedResult = service.addIndicadorMetaToCollectionIfMissing(indicadorMetaCollection, undefined, null);
        expect(expectedResult).toEqual(indicadorMetaCollection);
      });
    });

    describe('compareIndicadorMeta', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareIndicadorMeta(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareIndicadorMeta(entity1, entity2);
        const compareResult2 = service.compareIndicadorMeta(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareIndicadorMeta(entity1, entity2);
        const compareResult2 = service.compareIndicadorMeta(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareIndicadorMeta(entity1, entity2);
        const compareResult2 = service.compareIndicadorMeta(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
