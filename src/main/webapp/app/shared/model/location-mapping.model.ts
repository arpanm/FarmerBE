import dayjs from 'dayjs';
import { ICollectionCenter } from 'app/shared/model/collection-center.model';
import { AreaType } from 'app/shared/model/enumerations/area-type.model';

export interface ILocationMapping {
  id?: number;
  areaName?: string | null;
  areaType?: keyof typeof AreaType | null;
  pincode?: number | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  collectionCenter?: ICollectionCenter | null;
}

export const defaultValue: Readonly<ILocationMapping> = {
  isActive: false,
};
