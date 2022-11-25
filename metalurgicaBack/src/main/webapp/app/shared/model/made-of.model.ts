import { IRawMaterial } from 'app/shared/model/raw-material.model';
import { IProduct } from 'app/shared/model/product.model';

export interface IMadeOf {
  id?: number;
  amountMaterial?: number | null;
  rawMaterial?: IRawMaterial | null;
  product?: IProduct | null;
}

export const defaultValue: Readonly<IMadeOf> = {};
