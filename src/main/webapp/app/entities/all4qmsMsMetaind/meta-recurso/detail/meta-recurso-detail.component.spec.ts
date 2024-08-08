import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { MetaRecursoDetailComponent } from './meta-recurso-detail.component';

describe('MetaRecurso Management Detail Component', () => {
  let comp: MetaRecursoDetailComponent;
  let fixture: ComponentFixture<MetaRecursoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MetaRecursoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: MetaRecursoDetailComponent,
              resolve: { metaRecurso: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(MetaRecursoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MetaRecursoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load metaRecurso on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', MetaRecursoDetailComponent);

      // THEN
      expect(instance.metaRecurso()).toEqual(expect.objectContaining({ id: 123 }));
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
