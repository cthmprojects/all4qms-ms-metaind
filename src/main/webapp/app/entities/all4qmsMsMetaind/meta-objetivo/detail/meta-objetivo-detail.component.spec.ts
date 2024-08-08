import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { MetaObjetivoDetailComponent } from './meta-objetivo-detail.component';

describe('MetaObjetivo Management Detail Component', () => {
  let comp: MetaObjetivoDetailComponent;
  let fixture: ComponentFixture<MetaObjetivoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MetaObjetivoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: MetaObjetivoDetailComponent,
              resolve: { metaObjetivo: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(MetaObjetivoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MetaObjetivoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load metaObjetivo on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', MetaObjetivoDetailComponent);

      // THEN
      expect(instance.metaObjetivo()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
