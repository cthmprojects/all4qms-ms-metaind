import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IMetaRecurso } from '../meta-recurso.model';

@Component({
  standalone: true,
  selector: 'jhi-meta-recurso-detail',
  templateUrl: './meta-recurso-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class MetaRecursoDetailComponent {
  metaRecurso = input<IMetaRecurso | null>(null);

  previousState(): void {
    window.history.back();
  }
}
