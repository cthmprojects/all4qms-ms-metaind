import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { MetaAnexoDetailComponent } from './meta-anexo-detail.component';

describe('MetaAnexo Management Detail Component', () => {
  let comp: MetaAnexoDetailComponent;
  let fixture: ComponentFixture<MetaAnexoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MetaAnexoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: MetaAnexoDetailComponent,
              resolve: { metaAnexo: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(MetaAnexoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MetaAnexoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load metaAnexo on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', MetaAnexoDetailComponent);

      // THEN
      expect(instance.metaAnexo()).toEqual(expect.objectContaining({ id: 123 }));
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
