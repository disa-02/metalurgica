import { ISubscribe } from 'app/shared/model/subscribe.model';

export interface INotification {
  id?: number;
  descripcion?: string | null;
  tipo?: number | null;
  subscribes?: ISubscribe[] | null;
}

export const defaultValue: Readonly<INotification> = {};
