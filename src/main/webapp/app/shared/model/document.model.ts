import dayjs from 'dayjs';
import { IFarmer } from 'app/shared/model/farmer.model';
import { DocumentType } from 'app/shared/model/enumerations/document-type.model';
import { DocumentFormat } from 'app/shared/model/enumerations/document-format.model';

export interface IDocument {
  id?: number;
  docPath?: string | null;
  docType?: keyof typeof DocumentType | null;
  docFormat?: keyof typeof DocumentFormat | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  farmer?: IFarmer | null;
}

export const defaultValue: Readonly<IDocument> = {
  isActive: false,
};
