import { IMadeOf } from 'app/shared/model/made-of.model';

export interface IRawMaterial {
  id?: number;
  name?: string | null;
  stock?: number | null;
  madeoves?: IMadeOf[] | null;
}

export const defaultValue: Readonly<IRawMaterial> = {};
