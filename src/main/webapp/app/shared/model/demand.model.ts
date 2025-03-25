import dayjs from 'dayjs';
import { ICrop } from 'app/shared/model/crop.model';

export interface IDemand {
  id?: number;
  demandDate?: dayjs.Dayjs | null;
  demandValue?: number | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  crop?: ICrop | null;
}

export const defaultValue: Readonly<IDemand> = {
  isActive: false,
};
