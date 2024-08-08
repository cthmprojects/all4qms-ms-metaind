import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IMetaRecurso } from 'app/entities/all4qmsMsMetaind/meta-recurso/meta-recurso.model';
import { MetaRecursoService } from 'app/entities/all4qmsMsMetaind/meta-recurso/service/meta-recurso.service';
import { IMetaObjetivo } from 'app/entities/all4qmsMsMetaind/meta-objetivo/meta-objetivo.model';
import { MetaObjetivoService } from 'app/entities/all4qmsMsMetaind/meta-objetivo/service/meta-objetivo.service';
import { IMeta } from '../meta.model';
import { MetaService } from '../service/meta.service';
import { MetaFormService } from './meta-form.service';

import { MetaUpdateComponent } from './meta-update.component';

describe('Meta Management Update Component', () => {
  let comp: MetaUpdateComponent;
  let fixture: ComponentFixture<MetaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let metaFormService: MetaFormService;
  let metaService: MetaService;
  let metaRecursoService: MetaRecursoService;
  let metaObjetivoService: MetaObjetivoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [MetaUpdateComponent],
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
      .overrideTemplate(MetaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MetaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    metaFormService = TestBed.inject(MetaFormService);
    metaService = TestBed.inject(MetaService);
    metaRecursoService = TestBed.inject(MetaRecursoService);
    metaObjetivoService = TestBed.inject(MetaObjetivoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call MetaRecurso query and add missing value', () => {
      const meta: IMeta = { id: 456 };
      const recursos: IMetaRecurso[] = [{ id: 23117 }];
      meta.recursos = recursos;

      const metaRecursoCollection: IMetaRecurso[] = [{ id: 6430 }];
      jest.spyOn(metaRecursoService, 'query').mockReturnValue(of(new HttpResponse({ body: metaRecursoCollection })));
      const additionalMetaRecursos = [...recursos];
      const expectedCollection: IMetaRecurso[] = [...additionalMetaRecursos, ...metaRecursoCollection];
      jest.spyOn(metaRecursoService, 'addMetaRecursoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ meta });
      comp.ngOnInit();

      expect(metaRecursoService.query).toHaveBeenCalled();
      expect(metaRecursoService.addMetaRecursoToCollectionIfMissing).toHaveBeenCalledWith(
        metaRecursoCollection,
        ...additionalMetaRecursos.map(expect.objectContaining),
      );
      expect(comp.metaRecursosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call MetaObjetivo query and add missing value', () => {
      const meta: IMeta = { id: 456 };
      const metaObjetivo: IMetaObjetivo = { id: 24178 };
      meta.metaObjetivo = metaObjetivo;

      const metaObjetivoCollection: IMetaObjetivo[] = [{ id: 11234 }];
      jest.spyOn(metaObjetivoService, 'query').mockReturnValue(of(new HttpResponse({ body: metaObjetivoCollection })));
      const additionalMetaObjetivos = [metaObjetivo];
      const expectedCollection: IMetaObjetivo[] = [...additionalMetaObjetivos, ...metaObjetivoCollection];
      jest.spyOn(metaObjetivoService, 'addMetaObjetivoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ meta });
      comp.ngOnInit();

      expect(metaObjetivoService.query).toHaveBeenCalled();
      expect(metaObjetivoService.addMetaObjetivoToCollectionIfMissing).toHaveBeenCalledWith(
        metaObjetivoCollection,
        ...additionalMetaObjetivos.map(expect.objectContaining),
      );
      expect(comp.metaObjetivosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const meta: IMeta = { id: 456 };
      const recursos: IMetaRecurso = { id: 10994 };
      meta.recursos = [recursos];
      const metaObjetivo: IMetaObjetivo = { id: 6938 };
      meta.metaObjetivo = metaObjetivo;

      activatedRoute.data = of({ meta });
      comp.ngOnInit();

      expect(comp.metaRecursosSharedCollection).toContain(recursos);
      expect(comp.metaObjetivosSharedCollection).toContain(metaObjetivo);
      expect(comp.meta).toEqual(meta);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMeta>>();
      const meta = { id: 123 };
      jest.spyOn(metaFormService, 'getMeta').mockReturnValue(meta);
      jest.spyOn(metaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ meta });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: meta }));
      saveSubject.complete();

      // THEN
      expect(metaFormService.getMeta).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(metaService.update).toHaveBeenCalledWith(expect.objectContaining(meta));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMeta>>();
      const meta = { id: 123 };
      jest.spyOn(metaFormService, 'getMeta').mockReturnValue({ id: null });
      jest.spyOn(metaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ meta: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: meta }));
      saveSubject.complete();

      // THEN
      expect(metaFormService.getMeta).toHaveBeenCalled();
      expect(metaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMeta>>();
      const meta = { id: 123 };
      jest.spyOn(metaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ meta });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(metaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareMetaRecurso', () => {
      it('Should forward to metaRecursoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(metaRecursoService, 'compareMetaRecurso');
        comp.compareMetaRecurso(entity, entity2);
        expect(metaRecursoService.compareMetaRecurso).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareMetaObjetivo', () => {
      it('Should forward to metaObjetivoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(metaObjetivoService, 'compareMetaObjetivo');
        comp.compareMetaObjetivo(entity, entity2);
        expect(metaObjetivoService.compareMetaObjetivo).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
