import dayjs from 'dayjs';
import { IFarmer } from 'app/shared/model/farmer.model';

export interface ITermsAndCondition {
  id?: number;
  termsLink?: string | null;
  aggreedOn?: dayjs.Dayjs | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  farmer?: IFarmer | null;
}

export const defaultValue: Readonly<ITermsAndCondition> = {
  isActive: false,
};
