import dayjs from 'dayjs';
import { IAccessories } from 'app/shared/model/accessories.model';
import { ICrop } from 'app/shared/model/crop.model';
import { IFarmer } from 'app/shared/model/farmer.model';
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
  accessories?: IAccessories[] | null;
  crops?: ICrop[] | null;
  farmer?: IFarmer | null;
}

export const defaultValue: Readonly<IFarm> = {
  isActive: false,
};
