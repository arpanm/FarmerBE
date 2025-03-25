import dayjs from 'dayjs';
import { ICategory } from 'app/shared/model/category.model';
import { IFarm } from 'app/shared/model/farm.model';

export interface IAccessories {
  id?: number;
  name?: string | null;
  imagePath?: string | null;
  description?: string | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  category?: ICategory | null;
  farms?: IFarm[] | null;
}

export const defaultValue: Readonly<IAccessories> = {
  isActive: false,
};
