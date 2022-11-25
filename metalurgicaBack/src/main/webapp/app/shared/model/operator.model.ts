import { IEmployee } from 'app/shared/model/employee.model';
import { ISubscribe } from 'app/shared/model/subscribe.model';

export interface IOperator {
  id?: number;
  emloyee?: IEmployee | null;
  subscribes?: ISubscribe[] | null;
}

export const defaultValue: Readonly<IOperator> = {};
