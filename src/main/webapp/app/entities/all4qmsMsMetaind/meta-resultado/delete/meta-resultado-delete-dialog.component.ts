import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IMetaResultado } from '../meta-resultado.model';
import { MetaResultadoService } from '../service/meta-resultado.service';

@Component({
  standalone: true,
  templateUrl: './meta-resultado-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class MetaResultadoDeleteDialogComponent {
  metaResultado?: IMetaResultado;

  protected metaResultadoService = inject(MetaResultadoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.metaResultadoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
