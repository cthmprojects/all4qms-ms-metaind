import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { IndicadorDetailComponent } from './indicador-detail.component';

describe('Indicador Management Detail Component', () => {
  let comp: IndicadorDetailComponent;
  let fixture: ComponentFixture<IndicadorDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IndicadorDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: IndicadorDetailComponent,
              resolve: { indicador: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(IndicadorDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IndicadorDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load indicador on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', IndicadorDetailComponent);

      // THEN
      expect(instance.indicador()).toEqual(expect.objectContaining({ id: 123 }));
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
