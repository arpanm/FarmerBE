import dayjs from 'dayjs';
import { IFarmer } from 'app/shared/model/farmer.model';

export interface IOtp {
  id?: number;
  email?: string | null;
  emailOtp?: number | null;
  phone?: number | null;
  phoneOtp?: number | null;
  isValidated?: boolean | null;
  expiryTime?: dayjs.Dayjs | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  farmer?: IFarmer | null;
}

export const defaultValue: Readonly<IOtp> = {
  isValidated: false,
  isActive: false,
};
