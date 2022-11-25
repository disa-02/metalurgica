export interface IEmployee {
  name?: string;
  tipo?: number | null;
  email?: string | null;
}

export const defaultValue: Readonly<IEmployee> = {};
