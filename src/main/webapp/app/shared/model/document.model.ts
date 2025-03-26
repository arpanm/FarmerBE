import dayjs from 'dayjs';
import { IFarmer } from 'app/shared/model/farmer.model';
import { IFarm } from 'app/shared/model/farm.model';
import { IAddress } from 'app/shared/model/address.model';
import { IPanDetails } from 'app/shared/model/pan-details.model';
import { IBankDetails } from 'app/shared/model/bank-details.model';
import { IFieldVisit } from 'app/shared/model/field-visit.model';
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
  farm?: IFarm | null;
  address?: IAddress | null;
  panDetails?: IPanDetails | null;
  bankDetails?: IBankDetails | null;
  fieldVisit?: IFieldVisit | null;
}

export const defaultValue: Readonly<IDocument> = {
  isActive: false,
};
