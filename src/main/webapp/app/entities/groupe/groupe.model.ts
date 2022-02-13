import { IParticipant } from 'app/entities/participant/participant.model';

export interface IGroupe {
  id?: number;
  nom?: string | null;
  participants?: IParticipant[] | null;
}

export class Groupe implements IGroupe {
  constructor(public id?: number, public nom?: string | null, public participants?: IParticipant[] | null) {}
}

export function getGroupeIdentifier(groupe: IGroupe): number | undefined {
  return groupe.id;
}
