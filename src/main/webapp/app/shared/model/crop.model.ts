import dayjs from 'dayjs';
import { ICategory } from 'app/shared/model/category.model';
import { IFarm } from 'app/shared/model/farm.model';
import { ICollectionCenter } from 'app/shared/model/collection-center.model';

export interface ICrop {
  id?: number;
  name?: string | null;
  imagePath?: string | null;
  description?: string | null;
  orderNo?: number | null;
  skuId?: string | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  category?: ICategory | null;
  farms?: IFarm[] | null;
  collectionCenters?: ICollectionCenter[] | null;
}

export const defaultValue: Readonly<ICrop> = {
  isActive: false,
};
