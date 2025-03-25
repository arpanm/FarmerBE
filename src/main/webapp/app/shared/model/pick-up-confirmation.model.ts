import dayjs from 'dayjs';
import { IFarm } from 'app/shared/model/farm.model';
import { ICrop } from 'app/shared/model/crop.model';

export interface IPickUpConfirmation {
  id?: number;
  confirmDate?: dayjs.Dayjs | null;
  confirmValue?: number | null;
  pickupBy?: string | null;
  pickupTime?: string | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  farm?: IFarm | null;
  crop?: ICrop | null;
}

export const defaultValue: Readonly<IPickUpConfirmation> = {
  isActive: false,
};
