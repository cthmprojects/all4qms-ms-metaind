import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../meta-recurso.test-samples';

import { MetaRecursoFormService } from './meta-recurso-form.service';

describe('MetaRecurso Form Service', () => {
  let service: MetaRecursoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MetaRecursoFormService);
  });

  describe('Service methods', () => {
    describe('createMetaRecursoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMetaRecursoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            recursoNome: expect.any(Object),
            metas: expect.any(Object),
          }),
        );
      });

      it('passing IMetaRecurso should create a new form with FormGroup', () => {
        const formGroup = service.createMetaRecursoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            recursoNome: expect.any(Object),
            metas: expect.any(Object),
          }),
        );
      });
    });

    describe('getMetaRecurso', () => {
      it('should return NewMetaRecurso for default MetaRecurso initial value', () => {
        const formGroup = service.createMetaRecursoFormGroup(sampleWithNewData);

        const metaRecurso = service.getMetaRecurso(formGroup) as any;

        expect(metaRecurso).toMatchObject(sampleWithNewData);
      });

      it('should return NewMetaRecurso for empty MetaRecurso initial value', () => {
        const formGroup = service.createMetaRecursoFormGroup();

        const metaRecurso = service.getMetaRecurso(formGroup) as any;

        expect(metaRecurso).toMatchObject({});
      });

      it('should return IMetaRecurso', () => {
        const formGroup = service.createMetaRecursoFormGroup(sampleWithRequiredData);

        const metaRecurso = service.getMetaRecurso(formGroup) as any;

        expect(metaRecurso).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMetaRecurso should not enable id FormControl', () => {
        const formGroup = service.createMetaRecursoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMetaRecurso should disable id FormControl', () => {
        const formGroup = service.createMetaRecursoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
