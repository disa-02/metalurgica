import { IEmployee } from 'app/shared/model/employee.model';
import { ISale } from 'app/shared/model/sale.model';

export interface ISalesPerson {
  id?: number;
  emloyee?: IEmployee | null;
  sales?: ISale[] | null;
}

export const defaultValue: Readonly<ISalesPerson> = {};
