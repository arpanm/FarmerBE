import dayjs from 'dayjs';
import { ItemGrade } from 'app/shared/model/enumerations/item-grade.model';

export interface IPickupGradation {
  id?: number;
  itemGrade?: keyof typeof ItemGrade | null;
  gradedBy?: string | null;
  gradedTime?: dayjs.Dayjs | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
}

export const defaultValue: Readonly<IPickupGradation> = {
  isActive: false,
};
