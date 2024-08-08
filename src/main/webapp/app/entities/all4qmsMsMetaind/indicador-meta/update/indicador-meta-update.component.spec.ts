import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IIndicador } from 'app/entities/all4qmsMsMetaind/indicador/indicador.model';
import { IndicadorService } from 'app/entities/all4qmsMsMetaind/indicador/service/indicador.service';
import { IndicadorMetaService } from '../service/indicador-meta.service';
import { IIndicadorMeta } from '../indicador-meta.model';
import { IndicadorMetaFormService } from './indicador-meta-form.service';

import { IndicadorMetaUpdateComponent } from './indicador-meta-update.component';

describe('IndicadorMeta Management Update Component', () => {
  let comp: IndicadorMetaUpdateComponent;
  let fixture: ComponentFixture<IndicadorMetaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let indicadorMetaFormService: IndicadorMetaFormService;
  let indicadorMetaService: IndicadorMetaService;
  let indicadorService: IndicadorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [IndicadorMetaUpdateComponent],
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
      .overrideTemplate(IndicadorMetaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IndicadorMetaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    indicadorMetaFormService = TestBed.inject(IndicadorMetaFormService);
    indicadorMetaService = TestBed.inject(IndicadorMetaService);
    indicadorService = TestBed.inject(IndicadorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Indicador query and add missing value', () => {
      const indicadorMeta: IIndicadorMeta = { id: 456 };
      const indicador: IIndicador = { id: 24631 };
      indicadorMeta.indicador = indicador;

      const indicadorCollection: IIndicador[] = [{ id: 10747 }];
      jest.spyOn(indicadorService, 'query').mockReturnValue(of(new HttpResponse({ body: indicadorCollection })));
      const additionalIndicadors = [indicador];
      const expectedCollection: IIndicador[] = [...additionalIndicadors, ...indicadorCollection];
      jest.spyOn(indicadorService, 'addIndicadorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ indicadorMeta });
      comp.ngOnInit();

      expect(indicadorService.query).toHaveBeenCalled();
      expect(indicadorService.addIndicadorToCollectionIfMissing).toHaveBeenCalledWith(
        indicadorCollection,
        ...additionalIndicadors.map(expect.objectContaining),
      );
      expect(comp.indicadorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const indicadorMeta: IIndicadorMeta = { id: 456 };
      const indicador: IIndicador = { id: 9456 };
      indicadorMeta.indicador = indicador;

      activatedRoute.data = of({ indicadorMeta });
      comp.ngOnInit();

      expect(comp.indicadorsSharedCollection).toContain(indicador);
      expect(comp.indicadorMeta).toEqual(indicadorMeta);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIndicadorMeta>>();
      const indicadorMeta = { id: 123 };
      jest.spyOn(indicadorMetaFormService, 'getIndicadorMeta').mockReturnValue(indicadorMeta);
      jest.spyOn(indicadorMetaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ indicadorMeta });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: indicadorMeta }));
      saveSubject.complete();

      // THEN
      expect(indicadorMetaFormService.getIndicadorMeta).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(indicadorMetaService.update).toHaveBeenCalledWith(expect.objectContaining(indicadorMeta));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIndicadorMeta>>();
      const indicadorMeta = { id: 123 };
      jest.spyOn(indicadorMetaFormService, 'getIndicadorMeta').mockReturnValue({ id: null });
      jest.spyOn(indicadorMetaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ indicadorMeta: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: indicadorMeta }));
      saveSubject.complete();

      // THEN
      expect(indicadorMetaFormService.getIndicadorMeta).toHaveBeenCalled();
      expect(indicadorMetaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIndicadorMeta>>();
      const indicadorMeta = { id: 123 };
      jest.spyOn(indicadorMetaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ indicadorMeta });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(indicadorMetaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareIndicador', () => {
      it('Should forward to indicadorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(indicadorService, 'compareIndicador');
        comp.compareIndicador(entity, entity2);
        expect(indicadorService.compareIndicador).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
