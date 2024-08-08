import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IIndicador } from '../indicador.model';
import { IndicadorService } from '../service/indicador.service';

@Component({
  standalone: true,
  templateUrl: './indicador-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class IndicadorDeleteDialogComponent {
  indicador?: IIndicador;

  protected indicadorService = inject(IndicadorService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.indicadorService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
