import dayjs from 'dayjs';
import { ICategory } from 'app/shared/model/category.model';

export interface ICrop {
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
}

export const defaultValue: Readonly<ICrop> = {
  isActive: false,
};
