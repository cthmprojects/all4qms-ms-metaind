import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IMetaObjetivo } from '../meta-objetivo.model';
import { MetaObjetivoService } from '../service/meta-objetivo.service';

@Component({
  standalone: true,
  templateUrl: './meta-objetivo-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class MetaObjetivoDeleteDialogComponent {
  metaObjetivo?: IMetaObjetivo;

  protected metaObjetivoService = inject(MetaObjetivoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.metaObjetivoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
