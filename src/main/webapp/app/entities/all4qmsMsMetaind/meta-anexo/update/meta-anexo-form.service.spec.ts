import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../meta-anexo.test-samples';

import { MetaAnexoFormService } from './meta-anexo-form.service';

describe('MetaAnexo Form Service', () => {
  let service: MetaAnexoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MetaAnexoFormService);
  });

  describe('Service methods', () => {
    describe('createMetaAnexoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMetaAnexoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomeFisico: expect.any(Object),
            nomeOriginal: expect.any(Object),
            extensao: expect.any(Object),
            caminho: expect.any(Object),
            dataCriacao: expect.any(Object),
            metaResultado: expect.any(Object),
          }),
        );
      });

      it('passing IMetaAnexo should create a new form with FormGroup', () => {
        const formGroup = service.createMetaAnexoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomeFisico: expect.any(Object),
            nomeOriginal: expect.any(Object),
            extensao: expect.any(Object),
            caminho: expect.any(Object),
            dataCriacao: expect.any(Object),
            metaResultado: expect.any(Object),
          }),
        );
      });
    });

    describe('getMetaAnexo', () => {
      it('should return NewMetaAnexo for default MetaAnexo initial value', () => {
        const formGroup = service.createMetaAnexoFormGroup(sampleWithNewData);

        const metaAnexo = service.getMetaAnexo(formGroup) as any;

        expect(metaAnexo).toMatchObject(sampleWithNewData);
      });

      it('should return NewMetaAnexo for empty MetaAnexo initial value', () => {
        const formGroup = service.createMetaAnexoFormGroup();

        const metaAnexo = service.getMetaAnexo(formGroup) as any;

        expect(metaAnexo).toMatchObject({});
      });

      it('should return IMetaAnexo', () => {
        const formGroup = service.createMetaAnexoFormGroup(sampleWithRequiredData);

        const metaAnexo = service.getMetaAnexo(formGroup) as any;

        expect(metaAnexo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMetaAnexo should not enable id FormControl', () => {
        const formGroup = service.createMetaAnexoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMetaAnexo should disable id FormControl', () => {
        const formGroup = service.createMetaAnexoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
