export interface IFaculte {
  id?: number;
  nom?: string | null;
}

export class Faculte implements IFaculte {
  constructor(public id?: number, public nom?: string | null) {}
}

export function getFaculteIdentifier(faculte: IFaculte): number | undefined {
  return faculte.id;
}
