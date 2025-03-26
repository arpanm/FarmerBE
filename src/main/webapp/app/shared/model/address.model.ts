import dayjs from 'dayjs';
import { IFarmer } from 'app/shared/model/farmer.model';
import { IFarm } from 'app/shared/model/farm.model';
import { ICollectionCenter } from 'app/shared/model/collection-center.model';

export interface IAddress {
  id?: number;
  line1?: string;
  line2?: string | null;
  landmark?: string | null;
  city?: string | null;
  state?: string;
  country?: string;
  pincode?: number;
  lat?: number | null;
  lon?: number | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  farmer?: IFarmer | null;
  farm?: IFarm | null;
  collectionCenter?: ICollectionCenter | null;
}

export const defaultValue: Readonly<IAddress> = {
  isActive: false,
};
