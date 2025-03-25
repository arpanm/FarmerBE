import dayjs from 'dayjs';
import { IFarm } from 'app/shared/model/farm.model';
import { ICrop } from 'app/shared/model/crop.model';

export interface IHervestPlan {
  id?: number;
  hervestPlanDate?: dayjs.Dayjs | null;
  hervestPlanValue?: number | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  farm?: IFarm | null;
  crop?: ICrop | null;
}

export const defaultValue: Readonly<IHervestPlan> = {
  isActive: false,
};
