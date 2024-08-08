import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IMetaObjetivo } from '../meta-objetivo.model';

@Component({
  standalone: true,
  selector: 'jhi-meta-objetivo-detail',
  templateUrl: './meta-objetivo-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class MetaObjetivoDetailComponent {
  metaObjetivo = input<IMetaObjetivo | null>(null);

  previousState(): void {
    window.history.back();
  }
}
