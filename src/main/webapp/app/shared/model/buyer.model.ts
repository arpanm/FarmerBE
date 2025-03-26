import dayjs from 'dayjs';
import { ICollectionCenter } from 'app/shared/model/collection-center.model';
import { Language } from 'app/shared/model/enumerations/language.model';

export interface IBuyer {
  id?: number;
  name?: string | null;
  email?: string | null;
  phone?: number;
  isWhatsAppEnabled?: boolean | null;
  preferedLanguage?: keyof typeof Language | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  collectionCenter?: ICollectionCenter | null;
}

export const defaultValue: Readonly<IBuyer> = {
  isWhatsAppEnabled: false,
  isActive: false,
};
