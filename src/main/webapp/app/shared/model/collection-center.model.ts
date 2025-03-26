import dayjs from 'dayjs';
import { ICrop } from 'app/shared/model/crop.model';

export interface ICollectionCenter {
  id?: number;
  name?: string | null;
  ccId?: string | null;
  ffdcCode?: string | null;
  ffdcName?: string | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  crops?: ICrop[] | null;
}

export const defaultValue: Readonly<ICollectionCenter> = {
  isActive: false,
};
