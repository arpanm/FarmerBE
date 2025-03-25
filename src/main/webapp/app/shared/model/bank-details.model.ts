import dayjs from 'dayjs';
import { IFarmer } from 'app/shared/model/farmer.model';

export interface IBankDetails {
  id?: number;
  name?: string | null;
  accountNumber?: string | null;
  ifsC?: string | null;
  bankName?: string | null;
  branchName?: string | null;
  isVerified?: boolean | null;
  verificationTime?: dayjs.Dayjs | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  farmer?: IFarmer | null;
}

export const defaultValue: Readonly<IBankDetails> = {
  isVerified: false,
  isActive: false,
};
