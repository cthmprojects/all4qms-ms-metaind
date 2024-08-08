import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { IndicadorMetaDetailComponent } from './indicador-meta-detail.component';

describe('IndicadorMeta Management Detail Component', () => {
  let comp: IndicadorMetaDetailComponent;
  let fixture: ComponentFixture<IndicadorMetaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IndicadorMetaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: IndicadorMetaDetailComponent,
              resolve: { indicadorMeta: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(IndicadorMetaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IndicadorMetaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load indicadorMeta on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', IndicadorMetaDetailComponent);

      // THEN
      expect(instance.indicadorMeta()).toEqual(expect.objectContaining({ id: 123 }));
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
