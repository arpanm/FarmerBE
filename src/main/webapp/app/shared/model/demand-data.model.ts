import dayjs from 'dayjs';
import { IDemandDataFile } from 'app/shared/model/demand-data-file.model';
import { ICollectionCenter } from 'app/shared/model/collection-center.model';

export interface IDemandData {
  id?: number;
  fromCPC?: string | null;
  toCC?: string | null;
  pCode?: string | null;
  article?: string | null;
  description?: string | null;
  uom?: string | null;
  netWeightGrams?: number | null;
  crateSize?: number | null;
  indentUom?: number | null;
  indentKg?: number | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  file?: IDemandDataFile | null;
  collectionCenter?: ICollectionCenter | null;
}

export const defaultValue: Readonly<IDemandData> = {
  isActive: false,
};
