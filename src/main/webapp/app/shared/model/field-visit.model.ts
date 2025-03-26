import dayjs from 'dayjs';
import { IBuyer } from 'app/shared/model/buyer.model';
import { IFarm } from 'app/shared/model/farm.model';

export interface IFieldVisit {
  id?: number;
  fieldVisitDate?: dayjs.Dayjs | null;
  fieldVisitTime?: dayjs.Dayjs | null;
  lat?: number | null;
  lon?: number | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  buyer?: IBuyer | null;
  farm?: IFarm | null;
}

export const defaultValue: Readonly<IFieldVisit> = {
  isActive: false,
};
