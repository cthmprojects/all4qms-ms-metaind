import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IMetaAnexo } from '../meta-anexo.model';

@Component({
  standalone: true,
  selector: 'jhi-meta-anexo-detail',
  templateUrl: './meta-anexo-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class MetaAnexoDetailComponent {
  metaAnexo = input<IMetaAnexo | null>(null);

  previousState(): void {
    window.history.back();
  }
}
