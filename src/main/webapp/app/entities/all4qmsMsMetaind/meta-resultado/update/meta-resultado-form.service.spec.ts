import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../meta-resultado.test-samples';

import { MetaResultadoFormService } from './meta-resultado-form.service';

describe('MetaResultado Form Service', () => {
  let service: MetaResultadoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MetaResultadoFormService);
  });

  describe('Service methods', () => {
    describe('createMetaResultadoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMetaResultadoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            lancadoEm: expect.any(Object),
            parcial: expect.any(Object),
            metaAtingida: expect.any(Object),
            periodo: expect.any(Object),
            avaliacao: expect.any(Object),
            analise: expect.any(Object),
            meta: expect.any(Object),
          }),
        );
      });

      it('passing IMetaResultado should create a new form with FormGroup', () => {
        const formGroup = service.createMetaResultadoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            lancadoEm: expect.any(Object),
            parcial: expect.any(Object),
            metaAtingida: expect.any(Object),
            periodo: expect.any(Object),
            avaliacao: expect.any(Object),
            analise: expect.any(Object),
            meta: expect.any(Object),
          }),
        );
      });
    });

    describe('getMetaResultado', () => {
      it('should return NewMetaResultado for default MetaResultado initial value', () => {
        const formGroup = service.createMetaResultadoFormGroup(sampleWithNewData);

        const metaResultado = service.getMetaResultado(formGroup) as any;

        expect(metaResultado).toMatchObject(sampleWithNewData);
      });

      it('should return NewMetaResultado for empty MetaResultado initial value', () => {
        const formGroup = service.createMetaResultadoFormGroup();

        const metaResultado = service.getMetaResultado(formGroup) as any;

        expect(metaResultado).toMatchObject({});
      });

      it('should return IMetaResultado', () => {
        const formGroup = service.createMetaResultadoFormGroup(sampleWithRequiredData);

        const metaResultado = service.getMetaResultado(formGroup) as any;

        expect(metaResultado).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMetaResultado should not enable id FormControl', () => {
        const formGroup = service.createMetaResultadoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMetaResultado should disable id FormControl', () => {
        const formGroup = service.createMetaResultadoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
