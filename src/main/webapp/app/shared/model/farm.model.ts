import dayjs from 'dayjs';
import { ICrop } from 'app/shared/model/crop.model';
import { IAccessories } from 'app/shared/model/accessories.model';
import { IFarmer } from 'app/shared/model/farmer.model';
import { ICollectionCenter } from 'app/shared/model/collection-center.model';
import { IBuyer } from 'app/shared/model/buyer.model';
import { FarmType } from 'app/shared/model/enumerations/farm-type.model';

export interface IFarm {
  id?: number;
  farmType?: keyof typeof FarmType | null;
  ownerName?: string | null;
  relationshipWithOwner?: string | null;
  areaInAcres?: number | null;
  farmDocumentNo?: string | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  crops?: ICrop[] | null;
  accessories?: IAccessories[] | null;
  farmer?: IFarmer | null;
  collectionCenter?: ICollectionCenter | null;
  buyer?: IBuyer | null;
}

export const defaultValue: Readonly<IFarm> = {
  isActive: false,
};
