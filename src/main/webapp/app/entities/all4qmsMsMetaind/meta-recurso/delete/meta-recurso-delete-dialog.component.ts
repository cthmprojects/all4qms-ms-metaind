import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IMetaRecurso } from '../meta-recurso.model';
import { MetaRecursoService } from '../service/meta-recurso.service';

@Component({
  standalone: true,
  templateUrl: './meta-recurso-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class MetaRecursoDeleteDialogComponent {
  metaRecurso?: IMetaRecurso;

  protected metaRecursoService = inject(MetaRecursoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.metaRecursoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
