import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../indicador-meta.test-samples';

import { IndicadorMetaFormService } from './indicador-meta-form.service';

describe('IndicadorMeta Form Service', () => {
  let service: IndicadorMetaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IndicadorMetaFormService);
  });

  describe('Service methods', () => {
    describe('createIndicadorMetaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createIndicadorMetaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            frequencia: expect.any(Object),
            anoIndicador: expect.any(Object),
            meta01: expect.any(Object),
            meta02: expect.any(Object),
            meta03: expect.any(Object),
            meta04: expect.any(Object),
            meta05: expect.any(Object),
            meta06: expect.any(Object),
            meta07: expect.any(Object),
            meta08: expect.any(Object),
            meta09: expect.any(Object),
            meta10: expect.any(Object),
            meta11: expect.any(Object),
            meta12: expect.any(Object),
            medicao01: expect.any(Object),
            medicao02: expect.any(Object),
            medicao03: expect.any(Object),
            medicao04: expect.any(Object),
            medicao05: expect.any(Object),
            medicao06: expect.any(Object),
            medicao07: expect.any(Object),
            medicao08: expect.any(Object),
            medicao09: expect.any(Object),
            medicao10: expect.any(Object),
            medicao11: expect.any(Object),
            medicao12: expect.any(Object),
            indicador: expect.any(Object),
          }),
        );
      });

      it('passing IIndicadorMeta should create a new form with FormGroup', () => {
        const formGroup = service.createIndicadorMetaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            frequencia: expect.any(Object),
            anoIndicador: expect.any(Object),
            meta01: expect.any(Object),
            meta02: expect.any(Object),
            meta03: expect.any(Object),
            meta04: expect.any(Object),
            meta05: expect.any(Object),
            meta06: expect.any(Object),
            meta07: expect.any(Object),
            meta08: expect.any(Object),
            meta09: expect.any(Object),
            meta10: expect.any(Object),
            meta11: expect.any(Object),
            meta12: expect.any(Object),
            medicao01: expect.any(Object),
            medicao02: expect.any(Object),
            medicao03: expect.any(Object),
            medicao04: expect.any(Object),
            medicao05: expect.any(Object),
            medicao06: expect.any(Object),
            medicao07: expect.any(Object),
            medicao08: expect.any(Object),
            medicao09: expect.any(Object),
            medicao10: expect.any(Object),
            medicao11: expect.any(Object),
            medicao12: expect.any(Object),
            indicador: expect.any(Object),
          }),
        );
      });
    });

    describe('getIndicadorMeta', () => {
      it('should return NewIndicadorMeta for default IndicadorMeta initial value', () => {
        const formGroup = service.createIndicadorMetaFormGroup(sampleWithNewData);

        const indicadorMeta = service.getIndicadorMeta(formGroup) as any;

        expect(indicadorMeta).toMatchObject(sampleWithNewData);
      });

      it('should return NewIndicadorMeta for empty IndicadorMeta initial value', () => {
        const formGroup = service.createIndicadorMetaFormGroup();

        const indicadorMeta = service.getIndicadorMeta(formGroup) as any;

        expect(indicadorMeta).toMatchObject({});
      });

      it('should return IIndicadorMeta', () => {
        const formGroup = service.createIndicadorMetaFormGroup(sampleWithRequiredData);

        const indicadorMeta = service.getIndicadorMeta(formGroup) as any;

        expect(indicadorMeta).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IIndicadorMeta should not enable id FormControl', () => {
        const formGroup = service.createIndicadorMetaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewIndicadorMeta should disable id FormControl', () => {
        const formGroup = service.createIndicadorMetaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
