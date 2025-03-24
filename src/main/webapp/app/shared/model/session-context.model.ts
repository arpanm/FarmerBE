import dayjs from 'dayjs';
import { Language } from 'app/shared/model/enumerations/language.model';

export interface ISessionContext {
  id?: number;
  sessionId?: string | null;
  language?: keyof typeof Language | null;
  isLoggedIn?: boolean | null;
  farmerId?: string | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
}

export const defaultValue: Readonly<ISessionContext> = {
  isLoggedIn: false,
  isActive: false,
};
