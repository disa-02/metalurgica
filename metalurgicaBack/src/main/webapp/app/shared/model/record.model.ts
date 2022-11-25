import dayjs from 'dayjs';
import { ISale } from 'app/shared/model/sale.model';

export interface IRecord {
  id?: number;
  dateRange?: string | null;
  amount?: number | null;
  sales?: ISale[] | null;
}

export const defaultValue: Readonly<IRecord> = {};
