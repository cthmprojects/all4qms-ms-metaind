import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IMetaResultado } from 'app/entities/all4qmsMsMetaind/meta-resultado/meta-resultado.model';
import { MetaResultadoService } from 'app/entities/all4qmsMsMetaind/meta-resultado/service/meta-resultado.service';
import { MetaAnexoService } from '../service/meta-anexo.service';
import { IMetaAnexo } from '../meta-anexo.model';
import { MetaAnexoFormService } from './meta-anexo-form.service';

import { MetaAnexoUpdateComponent } from './meta-anexo-update.component';

describe('MetaAnexo Management Update Component', () => {
  let comp: MetaAnexoUpdateComponent;
  let fixture: ComponentFixture<MetaAnexoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let metaAnexoFormService: MetaAnexoFormService;
  let metaAnexoService: MetaAnexoService;
  let metaResultadoService: MetaResultadoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [MetaAnexoUpdateComponent],
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
      .overrideTemplate(MetaAnexoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MetaAnexoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    metaAnexoFormService = TestBed.inject(MetaAnexoFormService);
    metaAnexoService = TestBed.inject(MetaAnexoService);
    metaResultadoService = TestBed.inject(MetaResultadoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call MetaResultado query and add missing value', () => {
      const metaAnexo: IMetaAnexo = { id: 456 };
      const metaResultado: IMetaResultado = { id: 9558 };
      metaAnexo.metaResultado = metaResultado;

      const metaResultadoCollection: IMetaResultado[] = [{ id: 13479 }];
      jest.spyOn(metaResultadoService, 'query').mockReturnValue(of(new HttpResponse({ body: metaResultadoCollection })));
      const additionalMetaResultados = [metaResultado];
      const expectedCollection: IMetaResultado[] = [...additionalMetaResultados, ...metaResultadoCollection];
      jest.spyOn(metaResultadoService, 'addMetaResultadoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ metaAnexo });
      comp.ngOnInit();

      expect(metaResultadoService.query).toHaveBeenCalled();
      expect(metaResultadoService.addMetaResultadoToCollectionIfMissing).toHaveBeenCalledWith(
        metaResultadoCollection,
        ...additionalMetaResultados.map(expect.objectContaining),
      );
      expect(comp.metaResultadosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const metaAnexo: IMetaAnexo = { id: 456 };
      const metaResultado: IMetaResultado = { id: 8279 };
      metaAnexo.metaResultado = metaResultado;

      activatedRoute.data = of({ metaAnexo });
      comp.ngOnInit();

      expect(comp.metaResultadosSharedCollection).toContain(metaResultado);
      expect(comp.metaAnexo).toEqual(metaAnexo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMetaAnexo>>();
      const metaAnexo = { id: 123 };
      jest.spyOn(metaAnexoFormService, 'getMetaAnexo').mockReturnValue(metaAnexo);
      jest.spyOn(metaAnexoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ metaAnexo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: metaAnexo }));
      saveSubject.complete();

      // THEN
      expect(metaAnexoFormService.getMetaAnexo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(metaAnexoService.update).toHaveBeenCalledWith(expect.objectContaining(metaAnexo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMetaAnexo>>();
      const metaAnexo = { id: 123 };
      jest.spyOn(metaAnexoFormService, 'getMetaAnexo').mockReturnValue({ id: null });
      jest.spyOn(metaAnexoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ metaAnexo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: metaAnexo }));
      saveSubject.complete();

      // THEN
      expect(metaAnexoFormService.getMetaAnexo).toHaveBeenCalled();
      expect(metaAnexoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMetaAnexo>>();
      const metaAnexo = { id: 123 };
      jest.spyOn(metaAnexoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ metaAnexo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(metaAnexoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareMetaResultado', () => {
      it('Should forward to metaResultadoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(metaResultadoService, 'compareMetaResultado');
        comp.compareMetaResultado(entity, entity2);
        expect(metaResultadoService.compareMetaResultado).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
