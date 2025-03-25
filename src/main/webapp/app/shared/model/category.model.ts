import dayjs from 'dayjs';

export interface ICategory {
  id?: number;
  name?: string | null;
  imagePath?: string | null;
  description?: string | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  parent?: ICategory | null;
}

export const defaultValue: Readonly<ICategory> = {
  isActive: false,
};
