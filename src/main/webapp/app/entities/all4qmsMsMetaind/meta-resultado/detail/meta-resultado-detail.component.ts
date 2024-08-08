import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IMetaResultado } from '../meta-resultado.model';

@Component({
  standalone: true,
  selector: 'jhi-meta-resultado-detail',
  templateUrl: './meta-resultado-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class MetaResultadoDetailComponent {
  metaResultado = input<IMetaResultado | null>(null);

  previousState(): void {
    window.history.back();
  }
}
