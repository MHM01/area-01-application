import dayjs from 'dayjs/esm';
import { IInscription } from 'app/entities/inscription/inscription.model';
import { IFaculte } from 'app/entities/faculte/faculte.model';
import { IGroupe } from 'app/entities/groupe/groupe.model';
import { Sexe } from 'app/entities/enumerations/sexe.model';

export interface IParticipant {
  id?: number;
  prenom?: string | null;
  nom?: string | null;
  email?: string | null;
  password?: string | null;
  telephone?: string | null;
  dateDeNaissance?: dayjs.Dayjs | null;
  sexe?: Sexe | null;
  cin?: string | null;
  inscription?: IInscription | null;
  faculte?: IFaculte | null;
  groupe?: IGroupe | null;
}

export class Participant implements IParticipant {
  constructor(
    public id?: number,
    public prenom?: string | null,
    public nom?: string | null,
    public email?: string | null,
    public password?: string | null,
    public telephone?: string | null,
    public dateDeNaissance?: dayjs.Dayjs | null,
    public sexe?: Sexe | null,
    public cin?: string | null,
    public inscription?: IInscription | null,
    public faculte?: IFaculte | null,
    public groupe?: IGroupe | null
  ) {}
}

export function getParticipantIdentifier(participant: IParticipant): number | undefined {
  return participant.id;
}
