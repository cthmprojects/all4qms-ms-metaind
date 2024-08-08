import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IMetaResultado } from '../meta-resultado.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../meta-resultado.test-samples';

import { MetaResultadoService, RestMetaResultado } from './meta-resultado.service';

const requireRestSample: RestMetaResultado = {
  ...sampleWithRequiredData,
  lancadoEm: sampleWithRequiredData.lancadoEm?.toJSON(),
  periodo: sampleWithRequiredData.periodo?.toJSON(),
};

describe('MetaResultado Service', () => {
  let service: MetaResultadoService;
  let httpMock: HttpTestingController;
  let expectedResult: IMetaResultado | IMetaResultado[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(MetaResultadoService);
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

    it('should create a MetaResultado', () => {
      const metaResultado = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(metaResultado).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MetaResultado', () => {
      const metaResultado = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(metaResultado).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MetaResultado', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MetaResultado', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MetaResultado', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMetaResultadoToCollectionIfMissing', () => {
      it('should add a MetaResultado to an empty array', () => {
        const metaResultado: IMetaResultado = sampleWithRequiredData;
        expectedResult = service.addMetaResultadoToCollectionIfMissing([], metaResultado);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(metaResultado);
      });

      it('should not add a MetaResultado to an array that contains it', () => {
        const metaResultado: IMetaResultado = sampleWithRequiredData;
        const metaResultadoCollection: IMetaResultado[] = [
          {
            ...metaResultado,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMetaResultadoToCollectionIfMissing(metaResultadoCollection, metaResultado);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MetaResultado to an array that doesn't contain it", () => {
        const metaResultado: IMetaResultado = sampleWithRequiredData;
        const metaResultadoCollection: IMetaResultado[] = [sampleWithPartialData];
        expectedResult = service.addMetaResultadoToCollectionIfMissing(metaResultadoCollection, metaResultado);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(metaResultado);
      });

      it('should add only unique MetaResultado to an array', () => {
        const metaResultadoArray: IMetaResultado[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const metaResultadoCollection: IMetaResultado[] = [sampleWithRequiredData];
        expectedResult = service.addMetaResultadoToCollectionIfMissing(metaResultadoCollection, ...metaResultadoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const metaResultado: IMetaResultado = sampleWithRequiredData;
        const metaResultado2: IMetaResultado = sampleWithPartialData;
        expectedResult = service.addMetaResultadoToCollectionIfMissing([], metaResultado, metaResultado2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(metaResultado);
        expect(expectedResult).toContain(metaResultado2);
      });

      it('should accept null and undefined values', () => {
        const metaResultado: IMetaResultado = sampleWithRequiredData;
        expectedResult = service.addMetaResultadoToCollectionIfMissing([], null, metaResultado, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(metaResultado);
      });

      it('should return initial array if no MetaResultado is added', () => {
        const metaResultadoCollection: IMetaResultado[] = [sampleWithRequiredData];
        expectedResult = service.addMetaResultadoToCollectionIfMissing(metaResultadoCollection, undefined, null);
        expect(expectedResult).toEqual(metaResultadoCollection);
      });
    });

    describe('compareMetaResultado', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMetaResultado(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMetaResultado(entity1, entity2);
        const compareResult2 = service.compareMetaResultado(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMetaResultado(entity1, entity2);
        const compareResult2 = service.compareMetaResultado(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMetaResultado(entity1, entity2);
        const compareResult2 = service.compareMetaResultado(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
