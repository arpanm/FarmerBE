import dayjs from 'dayjs';
import { IBuyer } from 'app/shared/model/buyer.model';
import { AttendenceType } from 'app/shared/model/enumerations/attendence-type.model';

export interface IAttendence {
  id?: number;
  attendenceType?: keyof typeof AttendenceType | null;
  attendenceDate?: dayjs.Dayjs | null;
  attendenceTime?: dayjs.Dayjs | null;
  lat?: number | null;
  lon?: number | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  buyer?: IBuyer | null;
}

export const defaultValue: Readonly<IAttendence> = {
  isActive: false,
};
