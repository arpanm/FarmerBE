import dayjs from 'dayjs';
import { ICrop } from 'app/shared/model/crop.model';
import { ICollectionCenter } from 'app/shared/model/collection-center.model';
import { IDemandDataFile } from 'app/shared/model/demand-data-file.model';

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
  crop?: ICrop | null;
  collectionCenter?: ICollectionCenter | null;
  file?: IDemandDataFile | null;
}

export const defaultValue: Readonly<IDemandData> = {
  isActive: false,
};
