import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../indicador.test-samples';

import { IndicadorFormService } from './indicador-form.service';

describe('Indicador Form Service', () => {
  let service: IndicadorFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IndicadorFormService);
  });

  describe('Service methods', () => {
    describe('createIndicadorFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createIndicadorFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codigoIndicador: expect.any(Object),
            nomeIndicador: expect.any(Object),
            descricaoIndicador: expect.any(Object),
            unidade: expect.any(Object),
            tendencia: expect.any(Object),
            idProcesso: expect.any(Object),
            idMetaIndicador: expect.any(Object),
          }),
        );
      });

      it('passing IIndicador should create a new form with FormGroup', () => {
        const formGroup = service.createIndicadorFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codigoIndicador: expect.any(Object),
            nomeIndicador: expect.any(Object),
            descricaoIndicador: expect.any(Object),
            unidade: expect.any(Object),
            tendencia: expect.any(Object),
            idProcesso: expect.any(Object),
            idMetaIndicador: expect.any(Object),
          }),
        );
      });
    });

    describe('getIndicador', () => {
      it('should return NewIndicador for default Indicador initial value', () => {
        const formGroup = service.createIndicadorFormGroup(sampleWithNewData);

        const indicador = service.getIndicador(formGroup) as any;

        expect(indicador).toMatchObject(sampleWithNewData);
      });

      it('should return NewIndicador for empty Indicador initial value', () => {
        const formGroup = service.createIndicadorFormGroup();

        const indicador = service.getIndicador(formGroup) as any;

        expect(indicador).toMatchObject({});
      });

      it('should return IIndicador', () => {
        const formGroup = service.createIndicadorFormGroup(sampleWithRequiredData);

        const indicador = service.getIndicador(formGroup) as any;

        expect(indicador).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IIndicador should not enable id FormControl', () => {
        const formGroup = service.createIndicadorFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewIndicador should disable id FormControl', () => {
        const formGroup = service.createIndicadorFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
