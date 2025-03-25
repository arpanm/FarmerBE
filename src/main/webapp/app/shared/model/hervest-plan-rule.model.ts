import dayjs from 'dayjs';
import { IFarm } from 'app/shared/model/farm.model';
import { ICrop } from 'app/shared/model/crop.model';
import { FrequencyType } from 'app/shared/model/enumerations/frequency-type.model';

export interface IHervestPlanRule {
  id?: number;
  frequencyType?: keyof typeof FrequencyType | null;
  hervestPlanValue?: number | null;
  hervestPlanValueMin?: number | null;
  hervestPlanValueMax?: number | null;
  daysOfWeek?: string | null;
  daysOfMonth?: string | null;
  daysOfYear?: string | null;
  alternateXdays?: number | null;
  startDate?: dayjs.Dayjs | null;
  hasEndDate?: boolean | null;
  endDate?: dayjs.Dayjs | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  farm?: IFarm | null;
  crop?: ICrop | null;
}

export const defaultValue: Readonly<IHervestPlanRule> = {
  hasEndDate: false,
  isActive: false,
};
