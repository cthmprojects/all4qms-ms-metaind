import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IIndicadorMeta } from '../indicador-meta.model';
import { IndicadorMetaService } from '../service/indicador-meta.service';

@Component({
  standalone: true,
  templateUrl: './indicador-meta-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class IndicadorMetaDeleteDialogComponent {
  indicadorMeta?: IIndicadorMeta;

  protected indicadorMetaService = inject(IndicadorMetaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.indicadorMetaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
