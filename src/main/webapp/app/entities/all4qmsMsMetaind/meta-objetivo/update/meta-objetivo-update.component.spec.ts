import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { MetaObjetivoService } from '../service/meta-objetivo.service';
import { IMetaObjetivo } from '../meta-objetivo.model';
import { MetaObjetivoFormService } from './meta-objetivo-form.service';

import { MetaObjetivoUpdateComponent } from './meta-objetivo-update.component';

describe('MetaObjetivo Management Update Component', () => {
  let comp: MetaObjetivoUpdateComponent;
  let fixture: ComponentFixture<MetaObjetivoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let metaObjetivoFormService: MetaObjetivoFormService;
  let metaObjetivoService: MetaObjetivoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [MetaObjetivoUpdateComponent],
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
      .overrideTemplate(MetaObjetivoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MetaObjetivoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    metaObjetivoFormService = TestBed.inject(MetaObjetivoFormService);
    metaObjetivoService = TestBed.inject(MetaObjetivoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const metaObjetivo: IMetaObjetivo = { id: 456 };

      activatedRoute.data = of({ metaObjetivo });
      comp.ngOnInit();

      expect(comp.metaObjetivo).toEqual(metaObjetivo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMetaObjetivo>>();
      const metaObjetivo = { id: 123 };
      jest.spyOn(metaObjetivoFormService, 'getMetaObjetivo').mockReturnValue(metaObjetivo);
      jest.spyOn(metaObjetivoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ metaObjetivo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: metaObjetivo }));
      saveSubject.complete();

      // THEN
      expect(metaObjetivoFormService.getMetaObjetivo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(metaObjetivoService.update).toHaveBeenCalledWith(expect.objectContaining(metaObjetivo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMetaObjetivo>>();
      const metaObjetivo = { id: 123 };
      jest.spyOn(metaObjetivoFormService, 'getMetaObjetivo').mockReturnValue({ id: null });
      jest.spyOn(metaObjetivoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ metaObjetivo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: metaObjetivo }));
      saveSubject.complete();

      // THEN
      expect(metaObjetivoFormService.getMetaObjetivo).toHaveBeenCalled();
      expect(metaObjetivoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMetaObjetivo>>();
      const metaObjetivo = { id: 123 };
      jest.spyOn(metaObjetivoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ metaObjetivo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(metaObjetivoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
