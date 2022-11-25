import { IMadeOf } from 'app/shared/model/made-of.model';
import { IRoow } from 'app/shared/model/roow.model';

export interface IProduct {
  id?: number;
  name?: string | null;
  stock?: number | null;
  buyPrice?: number | null;
  sellPrice?: number | null;
  madeoves?: IMadeOf[] | null;
  roows?: IRoow[] | null;
}

export const defaultValue: Readonly<IProduct> = {};
