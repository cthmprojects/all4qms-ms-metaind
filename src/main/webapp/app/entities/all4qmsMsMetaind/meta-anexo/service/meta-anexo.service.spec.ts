import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IMetaAnexo } from '../meta-anexo.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../meta-anexo.test-samples';

import { MetaAnexoService, RestMetaAnexo } from './meta-anexo.service';

const requireRestSample: RestMetaAnexo = {
  ...sampleWithRequiredData,
  dataCriacao: sampleWithRequiredData.dataCriacao?.toJSON(),
};

describe('MetaAnexo Service', () => {
  let service: MetaAnexoService;
  let httpMock: HttpTestingController;
  let expectedResult: IMetaAnexo | IMetaAnexo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(MetaAnexoService);
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

    it('should create a MetaAnexo', () => {
      const metaAnexo = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(metaAnexo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MetaAnexo', () => {
      const metaAnexo = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(metaAnexo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MetaAnexo', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MetaAnexo', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MetaAnexo', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMetaAnexoToCollectionIfMissing', () => {
      it('should add a MetaAnexo to an empty array', () => {
        const metaAnexo: IMetaAnexo = sampleWithRequiredData;
        expectedResult = service.addMetaAnexoToCollectionIfMissing([], metaAnexo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(metaAnexo);
      });

      it('should not add a MetaAnexo to an array that contains it', () => {
        const metaAnexo: IMetaAnexo = sampleWithRequiredData;
        const metaAnexoCollection: IMetaAnexo[] = [
          {
            ...metaAnexo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMetaAnexoToCollectionIfMissing(metaAnexoCollection, metaAnexo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MetaAnexo to an array that doesn't contain it", () => {
        const metaAnexo: IMetaAnexo = sampleWithRequiredData;
        const metaAnexoCollection: IMetaAnexo[] = [sampleWithPartialData];
        expectedResult = service.addMetaAnexoToCollectionIfMissing(metaAnexoCollection, metaAnexo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(metaAnexo);
      });

      it('should add only unique MetaAnexo to an array', () => {
        const metaAnexoArray: IMetaAnexo[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const metaAnexoCollection: IMetaAnexo[] = [sampleWithRequiredData];
        expectedResult = service.addMetaAnexoToCollectionIfMissing(metaAnexoCollection, ...metaAnexoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const metaAnexo: IMetaAnexo = sampleWithRequiredData;
        const metaAnexo2: IMetaAnexo = sampleWithPartialData;
        expectedResult = service.addMetaAnexoToCollectionIfMissing([], metaAnexo, metaAnexo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(metaAnexo);
        expect(expectedResult).toContain(metaAnexo2);
      });

      it('should accept null and undefined values', () => {
        const metaAnexo: IMetaAnexo = sampleWithRequiredData;
        expectedResult = service.addMetaAnexoToCollectionIfMissing([], null, metaAnexo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(metaAnexo);
      });

      it('should return initial array if no MetaAnexo is added', () => {
        const metaAnexoCollection: IMetaAnexo[] = [sampleWithRequiredData];
        expectedResult = service.addMetaAnexoToCollectionIfMissing(metaAnexoCollection, undefined, null);
        expect(expectedResult).toEqual(metaAnexoCollection);
      });
    });

    describe('compareMetaAnexo', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMetaAnexo(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMetaAnexo(entity1, entity2);
        const compareResult2 = service.compareMetaAnexo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMetaAnexo(entity1, entity2);
        const compareResult2 = service.compareMetaAnexo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMetaAnexo(entity1, entity2);
        const compareResult2 = service.compareMetaAnexo(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
