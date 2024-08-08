import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IIndicadorMeta } from '../indicador-meta.model';
import { IndicadorMetaService } from '../service/indicador-meta.service';

import indicadorMetaResolve from './indicador-meta-routing-resolve.service';

describe('IndicadorMeta routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: IndicadorMetaService;
  let resultIndicadorMeta: IIndicadorMeta | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    service = TestBed.inject(IndicadorMetaService);
    resultIndicadorMeta = undefined;
  });

  describe('resolve', () => {
    it('should return IIndicadorMeta returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        indicadorMetaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultIndicadorMeta = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultIndicadorMeta).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        indicadorMetaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultIndicadorMeta = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultIndicadorMeta).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IIndicadorMeta>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        indicadorMetaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultIndicadorMeta = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultIndicadorMeta).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
