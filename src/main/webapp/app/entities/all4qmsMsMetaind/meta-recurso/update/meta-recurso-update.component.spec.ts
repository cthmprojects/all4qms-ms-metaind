import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IMeta } from 'app/entities/all4qmsMsMetaind/meta/meta.model';
import { MetaService } from 'app/entities/all4qmsMsMetaind/meta/service/meta.service';
import { MetaRecursoService } from '../service/meta-recurso.service';
import { IMetaRecurso } from '../meta-recurso.model';
import { MetaRecursoFormService } from './meta-recurso-form.service';

import { MetaRecursoUpdateComponent } from './meta-recurso-update.component';

describe('MetaRecurso Management Update Component', () => {
  let comp: MetaRecursoUpdateComponent;
  let fixture: ComponentFixture<MetaRecursoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let metaRecursoFormService: MetaRecursoFormService;
  let metaRecursoService: MetaRecursoService;
  let metaService: MetaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [MetaRecursoUpdateComponent],
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
      .overrideTemplate(MetaRecursoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MetaRecursoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    metaRecursoFormService = TestBed.inject(MetaRecursoFormService);
    metaRecursoService = TestBed.inject(MetaRecursoService);
    metaService = TestBed.inject(MetaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Meta query and add missing value', () => {
      const metaRecurso: IMetaRecurso = { id: 456 };
      const metas: IMeta[] = [{ id: 13189 }];
      metaRecurso.metas = metas;

      const metaCollection: IMeta[] = [{ id: 9259 }];
      jest.spyOn(metaService, 'query').mockReturnValue(of(new HttpResponse({ body: metaCollection })));
      const additionalMetas = [...metas];
      const expectedCollection: IMeta[] = [...additionalMetas, ...metaCollection];
      jest.spyOn(metaService, 'addMetaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ metaRecurso });
      comp.ngOnInit();

      expect(metaService.query).toHaveBeenCalled();
      expect(metaService.addMetaToCollectionIfMissing).toHaveBeenCalledWith(
        metaCollection,
        ...additionalMetas.map(expect.objectContaining),
      );
      expect(comp.metasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const metaRecurso: IMetaRecurso = { id: 456 };
      const metas: IMeta = { id: 31565 };
      metaRecurso.metas = [metas];

      activatedRoute.data = of({ metaRecurso });
      comp.ngOnInit();

      expect(comp.metasSharedCollection).toContain(metas);
      expect(comp.metaRecurso).toEqual(metaRecurso);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMetaRecurso>>();
      const metaRecurso = { id: 123 };
      jest.spyOn(metaRecursoFormService, 'getMetaRecurso').mockReturnValue(metaRecurso);
      jest.spyOn(metaRecursoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ metaRecurso });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: metaRecurso }));
      saveSubject.complete();

      // THEN
      expect(metaRecursoFormService.getMetaRecurso).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(metaRecursoService.update).toHaveBeenCalledWith(expect.objectContaining(metaRecurso));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMetaRecurso>>();
      const metaRecurso = { id: 123 };
      jest.spyOn(metaRecursoFormService, 'getMetaRecurso').mockReturnValue({ id: null });
      jest.spyOn(metaRecursoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ metaRecurso: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: metaRecurso }));
      saveSubject.complete();

      // THEN
      expect(metaRecursoFormService.getMetaRecurso).toHaveBeenCalled();
      expect(metaRecursoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMetaRecurso>>();
      const metaRecurso = { id: 123 };
      jest.spyOn(metaRecursoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ metaRecurso });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(metaRecursoService.update).toHaveBeenCalled();
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
