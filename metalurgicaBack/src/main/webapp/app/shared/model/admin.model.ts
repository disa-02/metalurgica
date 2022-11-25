import { IEmployee } from 'app/shared/model/employee.model';

export interface IAdmin {
  id?: number;
  emloyee?: IEmployee | null;
}

export const defaultValue: Readonly<IAdmin> = {};
