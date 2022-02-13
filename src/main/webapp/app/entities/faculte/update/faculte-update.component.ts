import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IFaculte, Faculte } from '../faculte.model';
import { FaculteService } from '../service/faculte.service';

@Component({
  selector: 'jhi-faculte-update',
  templateUrl: './faculte-update.component.html',
})
export class FaculteUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nom: [],
  });

  constructor(protected faculteService: FaculteService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ faculte }) => {
      this.updateForm(faculte);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const faculte = this.createFromForm();
    if (faculte.id !== undefined) {
      this.subscribeToSaveResponse(this.faculteService.update(faculte));
    } else {
      this.subscribeToSaveResponse(this.faculteService.create(faculte));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFaculte>>): void {
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

  protected updateForm(faculte: IFaculte): void {
    this.editForm.patchValue({
      id: faculte.id,
      nom: faculte.nom,
    });
  }

  protected createFromForm(): IFaculte {
    return {
      ...new Faculte(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
    };
  }
}
