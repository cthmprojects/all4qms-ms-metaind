import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../meta-objetivo.test-samples';

import { MetaObjetivoFormService } from './meta-objetivo-form.service';

describe('MetaObjetivo Form Service', () => {
  let service: MetaObjetivoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MetaObjetivoFormService);
  });

  describe('Service methods', () => {
    describe('createMetaObjetivoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMetaObjetivoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            politicaSGQ: expect.any(Object),
            desdobramentoSGQ: expect.any(Object),
            objetivoSGQ: expect.any(Object),
          }),
        );
      });

      it('passing IMetaObjetivo should create a new form with FormGroup', () => {
        const formGroup = service.createMetaObjetivoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            politicaSGQ: expect.any(Object),
            desdobramentoSGQ: expect.any(Object),
            objetivoSGQ: expect.any(Object),
          }),
        );
      });
    });

    describe('getMetaObjetivo', () => {
      it('should return NewMetaObjetivo for default MetaObjetivo initial value', () => {
        const formGroup = service.createMetaObjetivoFormGroup(sampleWithNewData);

        const metaObjetivo = service.getMetaObjetivo(formGroup) as any;

        expect(metaObjetivo).toMatchObject(sampleWithNewData);
      });

      it('should return NewMetaObjetivo for empty MetaObjetivo initial value', () => {
        const formGroup = service.createMetaObjetivoFormGroup();

        const metaObjetivo = service.getMetaObjetivo(formGroup) as any;

        expect(metaObjetivo).toMatchObject({});
      });

      it('should return IMetaObjetivo', () => {
        const formGroup = service.createMetaObjetivoFormGroup(sampleWithRequiredData);

        const metaObjetivo = service.getMetaObjetivo(formGroup) as any;

        expect(metaObjetivo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMetaObjetivo should not enable id FormControl', () => {
        const formGroup = service.createMetaObjetivoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMetaObjetivo should disable id FormControl', () => {
        const formGroup = service.createMetaObjetivoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
