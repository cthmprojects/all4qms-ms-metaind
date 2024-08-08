import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IMeta } from '../meta.model';

@Component({
  standalone: true,
  selector: 'jhi-meta-detail',
  templateUrl: './meta-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class MetaDetailComponent {
  meta = input<IMeta | null>(null);

  previousState(): void {
    window.history.back();
  }
}
