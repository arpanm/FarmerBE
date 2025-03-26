import dayjs from 'dayjs';
import { IBuyer } from 'app/shared/model/buyer.model';

export interface IBuyerTargetAchivement {
  id?: number;
  labour?: number | null;
  farmVisit?: number | null;
  totalCollection?: number | null;
  targetDate?: dayjs.Dayjs | null;
  attendenceHours?: number | null;
  achivementLabour?: number | null;
  achivementFarmVisit?: number | null;
  achivementTotalCollection?: number | null;
  achivementAttendenceHours?: number | null;
  achivementScore?: number | null;
  incentive?: number | null;
  kmDriven?: number | null;
  conveyance?: number | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  buyer?: IBuyer | null;
}

export const defaultValue: Readonly<IBuyerTargetAchivement> = {
  isActive: false,
};
