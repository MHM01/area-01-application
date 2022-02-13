import dayjs from 'dayjs/esm';
import { Statut } from 'app/entities/enumerations/statut.model';
import { Type } from 'app/entities/enumerations/type.model';

export interface IInscription {
  id?: number;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  statut?: Statut | null;
  type?: Type | null;
}

export class Inscription implements IInscription {
  constructor(
    public id?: number,
    public startDate?: dayjs.Dayjs | null,
    public endDate?: dayjs.Dayjs | null,
    public statut?: Statut | null,
    public type?: Type | null
  ) {}
}

export function getInscriptionIdentifier(inscription: IInscription): number | undefined {
  return inscription.id;
}
