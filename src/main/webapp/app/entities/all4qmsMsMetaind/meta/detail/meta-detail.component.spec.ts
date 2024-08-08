import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { MetaDetailComponent } from './meta-detail.component';

describe('Meta Management Detail Component', () => {
  let comp: MetaDetailComponent;
  let fixture: ComponentFixture<MetaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MetaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: MetaDetailComponent,
              resolve: { meta: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(MetaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MetaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load meta on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', MetaDetailComponent);

      // THEN
      expect(instance.meta()).toEqual(expect.objectContaining({ id: 123 }));
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
