import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IMetaObjetivo } from '../meta-objetivo.model';
import { MetaObjetivoService } from '../service/meta-objetivo.service';
import { MetaObjetivoFormService, MetaObjetivoFormGroup } from './meta-objetivo-form.service';

@Component({
  standalone: true,
  selector: 'jhi-meta-objetivo-update',
  templateUrl: './meta-objetivo-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MetaObjetivoUpdateComponent implements OnInit {
  isSaving = false;
  metaObjetivo: IMetaObjetivo | null = null;

  protected metaObjetivoService = inject(MetaObjetivoService);
  protected metaObjetivoFormService = inject(MetaObjetivoFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: MetaObjetivoFormGroup = this.metaObjetivoFormService.createMetaObjetivoFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ metaObjetivo }) => {
      this.metaObjetivo = metaObjetivo;
      if (metaObjetivo) {
        this.updateForm(metaObjetivo);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const metaObjetivo = this.metaObjetivoFormService.getMetaObjetivo(this.editForm);
    if (metaObjetivo.id !== null) {
      this.subscribeToSaveResponse(this.metaObjetivoService.update(metaObjetivo));
    } else {
      this.subscribeToSaveResponse(this.metaObjetivoService.create(metaObjetivo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMetaObjetivo>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(metaObjetivo: IMetaObjetivo): void {
    this.metaObjetivo = metaObjetivo;
    this.metaObjetivoFormService.resetForm(this.editForm, metaObjetivo);
  }
}
