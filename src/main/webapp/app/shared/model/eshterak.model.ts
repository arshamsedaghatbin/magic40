import { UntilType } from 'app/shared/model/enumerations/until-type.model';

export interface IEshterak {
  id?: number;
  text?: string | null;
  amount?: number | null;
  until?: number | null;
  type?: UntilType | null;
}

export const defaultValue: Readonly<IEshterak> = {};
