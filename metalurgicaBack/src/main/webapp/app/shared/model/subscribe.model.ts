import { INotification } from 'app/shared/model/notification.model';
import { IOperator } from 'app/shared/model/operator.model';

export interface ISubscribe {
  id?: number;
  notification?: INotification | null;
  operator?: IOperator | null;
}

export const defaultValue: Readonly<ISubscribe> = {};
