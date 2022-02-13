import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IInscription, Inscription } from '../inscription.model';
import { InscriptionService } from '../service/inscription.service';
import { Statut } from 'app/entities/enumerations/statut.model';
import { Type } from 'app/entities/enumerations/type.model';

@Component({
  selector: 'jhi-inscription-update',
  templateUrl: './inscription-update.component.html',
})
export class InscriptionUpdateComponent implements OnInit {
  isSaving = false;
  statutValues = Object.keys(Statut);
  typeValues = Object.keys(Type);

  editForm = this.fb.group({
    id: [],
    startDate: [],
    endDate: [],
    statut: [],
    type: [],
  });

  constructor(protected inscriptionService: InscriptionService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inscription }) => {
      if (inscription.id === undefined) {
        const today = dayjs().startOf('day');
        inscription.startDate = today;
        inscription.endDate = today;
      }

      this.updateForm(inscription);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const inscription = this.createFromForm();
    if (inscription.id !== undefined) {
      this.subscribeToSaveResponse(this.inscriptionService.update(inscription));
    } else {
      this.subscribeToSaveResponse(this.inscriptionService.create(inscription));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInscription>>): void {
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

  protected updateForm(inscription: IInscription): void {
    this.editForm.patchValue({
      id: inscription.id,
      startDate: inscription.startDate ? inscription.startDate.format(DATE_TIME_FORMAT) : null,
      endDate: inscription.endDate ? inscription.endDate.format(DATE_TIME_FORMAT) : null,
      statut: inscription.statut,
      type: inscription.type,
    });
  }

  protected createFromForm(): IInscription {
    return {
      ...new Inscription(),
      id: this.editForm.get(['id'])!.value,
      startDate: this.editForm.get(['startDate'])!.value ? dayjs(this.editForm.get(['startDate'])!.value, DATE_TIME_FORMAT) : undefined,
      endDate: this.editForm.get(['endDate'])!.value ? dayjs(this.editForm.get(['endDate'])!.value, DATE_TIME_FORMAT) : undefined,
      statut: this.editForm.get(['statut'])!.value,
      type: this.editForm.get(['type'])!.value,
    };
  }
}
