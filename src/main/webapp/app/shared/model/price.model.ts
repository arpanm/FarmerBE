import dayjs from 'dayjs';
import { ICrop } from 'app/shared/model/crop.model';

export interface IPrice {
  id?: number;
  priceDate?: dayjs.Dayjs | null;
  priceValue?: number | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  crop?: ICrop | null;
}

export const defaultValue: Readonly<IPrice> = {
  isActive: false,
};
