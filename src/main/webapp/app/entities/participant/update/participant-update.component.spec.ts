import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ParticipantService } from '../service/participant.service';
import { IParticipant, Participant } from '../participant.model';
import { IInscription } from 'app/entities/inscription/inscription.model';
import { InscriptionService } from 'app/entities/inscription/service/inscription.service';
import { IFaculte } from 'app/entities/faculte/faculte.model';
import { FaculteService } from 'app/entities/faculte/service/faculte.service';
import { IGroupe } from 'app/entities/groupe/groupe.model';
import { GroupeService } from 'app/entities/groupe/service/groupe.service';

import { ParticipantUpdateComponent } from './participant-update.component';

describe('Participant Management Update Component', () => {
  let comp: ParticipantUpdateComponent;
  let fixture: ComponentFixture<ParticipantUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let participantService: ParticipantService;
  let inscriptionService: InscriptionService;
  let faculteService: FaculteService;
  let groupeService: GroupeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ParticipantUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ParticipantUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ParticipantUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    participantService = TestBed.inject(ParticipantService);
    inscriptionService = TestBed.inject(InscriptionService);
    faculteService = TestBed.inject(FaculteService);
    groupeService = TestBed.inject(GroupeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call inscription query and add missing value', () => {
      const participant: IParticipant = { id: 456 };
      const inscription: IInscription = { id: 56974 };
      participant.inscription = inscription;

      const inscriptionCollection: IInscription[] = [{ id: 66542 }];
      jest.spyOn(inscriptionService, 'query').mockReturnValue(of(new HttpResponse({ body: inscriptionCollection })));
      const expectedCollection: IInscription[] = [inscription, ...inscriptionCollection];
      jest.spyOn(inscriptionService, 'addInscriptionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ participant });
      comp.ngOnInit();

      expect(inscriptionService.query).toHaveBeenCalled();
      expect(inscriptionService.addInscriptionToCollectionIfMissing).toHaveBeenCalledWith(inscriptionCollection, inscription);
      expect(comp.inscriptionsCollection).toEqual(expectedCollection);
    });

    it('Should call faculte query and add missing value', () => {
      const participant: IParticipant = { id: 456 };
      const faculte: IFaculte = { id: 35127 };
      participant.faculte = faculte;

      const faculteCollection: IFaculte[] = [{ id: 40735 }];
      jest.spyOn(faculteService, 'query').mockReturnValue(of(new HttpResponse({ body: faculteCollection })));
      const expectedCollection: IFaculte[] = [faculte, ...faculteCollection];
      jest.spyOn(faculteService, 'addFaculteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ participant });
      comp.ngOnInit();

      expect(faculteService.query).toHaveBeenCalled();
      expect(faculteService.addFaculteToCollectionIfMissing).toHaveBeenCalledWith(faculteCollection, faculte);
      expect(comp.facultesCollection).toEqual(expectedCollection);
    });

    it('Should call Groupe query and add missing value', () => {
      const participant: IParticipant = { id: 456 };
      const groupe: IGroupe = { id: 63551 };
      participant.groupe = groupe;

      const groupeCollection: IGroupe[] = [{ id: 70567 }];
      jest.spyOn(groupeService, 'query').mockReturnValue(of(new HttpResponse({ body: groupeCollection })));
      const additionalGroupes = [groupe];
      const expectedCollection: IGroupe[] = [...additionalGroupes, ...groupeCollection];
      jest.spyOn(groupeService, 'addGroupeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ participant });
      comp.ngOnInit();

      expect(groupeService.query).toHaveBeenCalled();
      expect(groupeService.addGroupeToCollectionIfMissing).toHaveBeenCalledWith(groupeCollection, ...additionalGroupes);
      expect(comp.groupesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const participant: IParticipant = { id: 456 };
      const inscription: IInscription = { id: 54417 };
      participant.inscription = inscription;
      const faculte: IFaculte = { id: 56440 };
      participant.faculte = faculte;
      const groupe: IGroupe = { id: 66438 };
      participant.groupe = groupe;

      activatedRoute.data = of({ participant });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(participant));
      expect(comp.inscriptionsCollection).toContain(inscription);
      expect(comp.facultesCollection).toContain(faculte);
      expect(comp.groupesSharedCollection).toContain(groupe);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Participant>>();
      const participant = { id: 123 };
      jest.spyOn(participantService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ participant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: participant }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(participantService.update).toHaveBeenCalledWith(participant);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Participant>>();
      const participant = new Participant();
      jest.spyOn(participantService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ participant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: participant }));
      saveSubject.complete();

      // THEN
      expect(participantService.create).toHaveBeenCalledWith(participant);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Participant>>();
      const participant = { id: 123 };
      jest.spyOn(participantService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ participant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(participantService.update).toHaveBeenCalledWith(participant);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackInscriptionById', () => {
      it('Should return tracked Inscription primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackInscriptionById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackFaculteById', () => {
      it('Should return tracked Faculte primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackFaculteById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackGroupeById', () => {
      it('Should return tracked Groupe primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackGroupeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
