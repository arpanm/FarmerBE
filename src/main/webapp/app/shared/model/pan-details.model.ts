import dayjs from 'dayjs';
import { IFarmer } from 'app/shared/model/farmer.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface IPanDetails {
  id?: number;
  pan?: string;
  name?: string | null;
  dateOfBirth?: dayjs.Dayjs | null;
  gender?: keyof typeof Gender | null;
  country?: string | null;
  isVerified?: boolean | null;
  verificationTime?: dayjs.Dayjs | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  farmer?: IFarmer | null;
}

export const defaultValue: Readonly<IPanDetails> = {
  isVerified: false,
  isActive: false,
};
