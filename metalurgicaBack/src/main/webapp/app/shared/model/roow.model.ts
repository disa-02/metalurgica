import { ISale } from 'app/shared/model/sale.model';
import { IProduct } from 'app/shared/model/product.model';

export interface IRoow {
  id?: number;
  amountProduct?: number | null;
  subTotal?: number | null;
  sale?: ISale | null;
  product?: IProduct | null;
}

export const defaultValue: Readonly<IRoow> = {};
