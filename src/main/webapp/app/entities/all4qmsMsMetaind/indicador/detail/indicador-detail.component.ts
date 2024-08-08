import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IIndicador } from '../indicador.model';

@Component({
  standalone: true,
  selector: 'jhi-indicador-detail',
  templateUrl: './indicador-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class IndicadorDetailComponent {
  indicador = input<IIndicador | null>(null);

  previousState(): void {
    window.history.back();
  }
}
