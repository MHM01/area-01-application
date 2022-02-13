import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IParticipant, Participant } from '../participant.model';
import { ParticipantService } from '../service/participant.service';
import { IInscription } from 'app/entities/inscription/inscription.model';
import { InscriptionService } from 'app/entities/inscription/service/inscription.service';
import { IFaculte } from 'app/entities/faculte/faculte.model';
import { FaculteService } from 'app/entities/faculte/service/faculte.service';
import { IGroupe } from 'app/entities/groupe/groupe.model';
import { GroupeService } from 'app/entities/groupe/service/groupe.service';
import { Sexe } from 'app/entities/enumerations/sexe.model';

@Component({
  selector: 'jhi-participant-update',
  templateUrl: './participant-update.component.html',
})
export class ParticipantUpdateComponent implements OnInit {
  isSaving = false;
  sexeValues = Object.keys(Sexe);

  inscriptionsCollection: IInscription[] = [];
  facultesCollection: IFaculte[] = [];
  groupesSharedCollection: IGroupe[] = [];

  editForm = this.fb.group({
    id: [],
    prenom: [],
    nom: [],
    email: [],
    password: [],
    telephone: [],
    dateDeNaissance: [],
    sexe: [],
    cin: [],
    inscription: [],
    faculte: [],
    groupe: [],
  });

  constructor(
    protected participantService: ParticipantService,
    protected inscriptionService: InscriptionService,
    protected faculteService: FaculteService,
    protected groupeService: GroupeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ participant }) => {
      if (participant.id === undefined) {
        const today = dayjs().startOf('day');
        participant.dateDeNaissance = today;
      }

      this.updateForm(participant);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const participant = this.createFromForm();
    if (participant.id !== undefined) {
      this.subscribeToSaveResponse(this.participantService.update(participant));
    } else {
      this.subscribeToSaveResponse(this.participantService.create(participant));
    }
  }

  trackInscriptionById(index: number, item: IInscription): number {
    return item.id!;
  }

  trackFaculteById(index: number, item: IFaculte): number {
    return item.id!;
  }

  trackGroupeById(index: number, item: IGroupe): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParticipant>>): void {
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

  protected updateForm(participant: IParticipant): void {
    this.editForm.patchValue({
      id: participant.id,
      prenom: participant.prenom,
      nom: participant.nom,
      email: participant.email,
      password: participant.password,
      telephone: participant.telephone,
      dateDeNaissance: participant.dateDeNaissance ? participant.dateDeNaissance.format(DATE_TIME_FORMAT) : null,
      sexe: participant.sexe,
      cin: participant.cin,
      inscription: participant.inscription,
      faculte: participant.faculte,
      groupe: participant.groupe,
    });

    this.inscriptionsCollection = this.inscriptionService.addInscriptionToCollectionIfMissing(
      this.inscriptionsCollection,
      participant.inscription
    );
    this.facultesCollection = this.faculteService.addFaculteToCollectionIfMissing(this.facultesCollection, participant.faculte);
    this.groupesSharedCollection = this.groupeService.addGroupeToCollectionIfMissing(this.groupesSharedCollection, participant.groupe);
  }

  protected loadRelationshipsOptions(): void {
    this.inscriptionService
      .query({ filter: 'participant-is-null' })
      .pipe(map((res: HttpResponse<IInscription[]>) => res.body ?? []))
      .pipe(
        map((inscriptions: IInscription[]) =>
          this.inscriptionService.addInscriptionToCollectionIfMissing(inscriptions, this.editForm.get('inscription')!.value)
        )
      )
      .subscribe((inscriptions: IInscription[]) => (this.inscriptionsCollection = inscriptions));

    this.faculteService
      .query({ filter: 'participant-is-null' })
      .pipe(map((res: HttpResponse<IFaculte[]>) => res.body ?? []))
      .pipe(
        map((facultes: IFaculte[]) => this.faculteService.addFaculteToCollectionIfMissing(facultes, this.editForm.get('faculte')!.value))
      )
      .subscribe((facultes: IFaculte[]) => (this.facultesCollection = facultes));

    this.groupeService
      .query()
      .pipe(map((res: HttpResponse<IGroupe[]>) => res.body ?? []))
      .pipe(map((groupes: IGroupe[]) => this.groupeService.addGroupeToCollectionIfMissing(groupes, this.editForm.get('groupe')!.value)))
      .subscribe((groupes: IGroupe[]) => (this.groupesSharedCollection = groupes));
  }

  protected createFromForm(): IParticipant {
    return {
      ...new Participant(),
      id: this.editForm.get(['id'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      email: this.editForm.get(['email'])!.value,
      password: this.editForm.get(['password'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      dateDeNaissance: this.editForm.get(['dateDeNaissance'])!.value
        ? dayjs(this.editForm.get(['dateDeNaissance'])!.value, DATE_TIME_FORMAT)
        : undefined,
      sexe: this.editForm.get(['sexe'])!.value,
      cin: this.editForm.get(['cin'])!.value,
      inscription: this.editForm.get(['inscription'])!.value,
      faculte: this.editForm.get(['faculte'])!.value,
      groupe: this.editForm.get(['groupe'])!.value,
    };
  }
}
