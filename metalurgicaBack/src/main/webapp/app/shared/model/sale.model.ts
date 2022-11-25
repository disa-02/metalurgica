import dayjs from 'dayjs';
import { IRoow } from 'app/shared/model/roow.model';
import { ISalesPerson } from 'app/shared/model/sales-person.model';
import { IRecord } from 'app/shared/model/record.model';

export interface ISale {
  id?: number;
  saleCode?: number | null;
  date?: string | null;
  total?: number | null;
  roows?: IRoow[] | null;
  salesPerson?: ISalesPerson | null;
  record?: IRecord | null;
}

export const defaultValue: Readonly<ISale> = {};
