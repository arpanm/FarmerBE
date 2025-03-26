import dayjs from 'dayjs';
import { UploadStatus } from 'app/shared/model/enumerations/upload-status.model';

export interface IDemandDataFile {
  id?: number;
  uploadedDate?: dayjs.Dayjs | null;
  uploadedTime?: dayjs.Dayjs | null;
  fileName?: string | null;
  uploadedBy?: string | null;
  status?: keyof typeof UploadStatus | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
}

export const defaultValue: Readonly<IDemandDataFile> = {
  isActive: false,
};
