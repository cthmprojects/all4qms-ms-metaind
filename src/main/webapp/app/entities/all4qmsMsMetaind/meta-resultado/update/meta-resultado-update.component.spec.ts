import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IMeta } from 'app/entities/all4qmsMsMetaind/meta/meta.model';
import { MetaService } from 'app/entities/all4qmsMsMetaind/meta/service/meta.service';
import { MetaResultadoService } from '../service/meta-resultado.service';
import { IMetaResultado } from '../meta-resultado.model';
import { MetaResultadoFormService } from './meta-resultado-form.service';

import { MetaResultadoUpdateComponent } from './meta-resultado-update.component';

describe('MetaResultado Management Update Component', () => {
  let comp: MetaResultadoUpdateComponent;
  let fixture: ComponentFixture<MetaResultadoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let metaResultadoFormService: MetaResultadoFormService;
  let metaResultadoService: MetaResultadoService;
  let metaService: MetaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [MetaResultadoUpdateComponent],
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
      .overrideTemplate(MetaResultadoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MetaResultadoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    metaResultadoFormService = TestBed.inject(MetaResultadoFormService);
    metaResultadoService = TestBed.inject(MetaResultadoService);
    metaService = TestBed.inject(MetaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Meta query and add missing value', () => {
      const metaResultado: IMetaResultado = { id: 456 };
      const meta: IMeta = { id: 19867 };
      metaResultado.meta = meta;

      const metaCollection: IMeta[] = [{ id: 28096 }];
      jest.spyOn(metaService, 'query').mockReturnValue(of(new HttpResponse({ body: metaCollection })));
      const additionalMetas = [meta];
      const expectedCollection: IMeta[] = [...additionalMetas, ...metaCollection];
      jest.spyOn(metaService, 'addMetaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ metaResultado });
      comp.ngOnInit();

      expect(metaService.query).toHaveBeenCalled();
      expect(metaService.addMetaToCollectionIfMissing).toHaveBeenCalledWith(
        metaCollection,
        ...additionalMetas.map(expect.objectContaining),
      );
      expect(comp.metasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const metaResultado: IMetaResultado = { id: 456 };
      const meta: IMeta = { id: 3478 };
      metaResultado.meta = meta;

      activatedRoute.data = of({ metaResultado });
      comp.ngOnInit();

      expect(comp.metasSharedCollection).toContain(meta);
      expect(comp.metaResultado).toEqual(metaResultado);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMetaResultado>>();
      const metaResultado = { id: 123 };
      jest.spyOn(metaResultadoFormService, 'getMetaResultado').mockReturnValue(metaResultado);
      jest.spyOn(metaResultadoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ metaResultado });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: metaResultado }));
      saveSubject.complete();

      // THEN
      expect(metaResultadoFormService.getMetaResultado).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(metaResultadoService.update).toHaveBeenCalledWith(expect.objectContaining(metaResultado));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMetaResultado>>();
      const metaResultado = { id: 123 };
      jest.spyOn(metaResultadoFormService, 'getMetaResultado').mockReturnValue({ id: null });
      jest.spyOn(metaResultadoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ metaResultado: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: metaResultado }));
      saveSubject.complete();

      // THEN
      expect(metaResultadoFormService.getMetaResultado).toHaveBeenCalled();
      expect(metaResultadoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMetaResultado>>();
      const metaResultado = { id: 123 };
      jest.spyOn(metaResultadoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ metaResultado });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(metaResultadoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareMeta', () => {
      it('Should forward to metaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(metaService, 'compareMeta');
        comp.compareMeta(entity, entity2);
        expect(metaService.compareMeta).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
