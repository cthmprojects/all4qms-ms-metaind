import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IndicadorService } from '../service/indicador.service';
import { IIndicador } from '../indicador.model';
import { IndicadorFormService } from './indicador-form.service';

import { IndicadorUpdateComponent } from './indicador-update.component';

describe('Indicador Management Update Component', () => {
  let comp: IndicadorUpdateComponent;
  let fixture: ComponentFixture<IndicadorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let indicadorFormService: IndicadorFormService;
  let indicadorService: IndicadorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [IndicadorUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(IndicadorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IndicadorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    indicadorFormService = TestBed.inject(IndicadorFormService);
    indicadorService = TestBed.inject(IndicadorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const indicador: IIndicador = { id: 456 };

      activatedRoute.data = of({ indicador });
      comp.ngOnInit();

      expect(comp.indicador).toEqual(indicador);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIndicador>>();
      const indicador = { id: 123 };
      jest.spyOn(indicadorFormService, 'getIndicador').mockReturnValue(indicador);
      jest.spyOn(indicadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ indicador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: indicador }));
      saveSubject.complete();

      // THEN
      expect(indicadorFormService.getIndicador).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(indicadorService.update).toHaveBeenCalledWith(expect.objectContaining(indicador));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIndicador>>();
      const indicador = { id: 123 };
      jest.spyOn(indicadorFormService, 'getIndicador').mockReturnValue({ id: null });
      jest.spyOn(indicadorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ indicador: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: indicador }));
      saveSubject.complete();

      // THEN
      expect(indicadorFormService.getIndicador).toHaveBeenCalled();
      expect(indicadorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIndicador>>();
      const indicador = { id: 123 };
      jest.spyOn(indicadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ indicador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(indicadorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
