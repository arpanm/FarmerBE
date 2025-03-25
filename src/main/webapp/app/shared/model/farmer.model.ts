import dayjs from 'dayjs';
import { FarmerType } from 'app/shared/model/enumerations/farmer-type.model';
import { Language } from 'app/shared/model/enumerations/language.model';

export interface IFarmer {
  id?: number;
  name?: string | null;
  email?: string | null;
  phone?: number;
  isWhatsAppEnabled?: boolean | null;
  farmerType?: keyof typeof FarmerType | null;
  preferedLanguage?: keyof typeof Language | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
}

export const defaultValue: Readonly<IFarmer> = {
  isWhatsAppEnabled: false,
  isActive: false,
};
