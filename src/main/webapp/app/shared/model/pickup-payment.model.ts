import dayjs from 'dayjs';
import { PaymentStatus } from 'app/shared/model/enumerations/payment-status.model';

export interface IPickupPayment {
  id?: number;
  paymentStatus?: keyof typeof PaymentStatus | null;
  transactionId?: string | null;
  paymentDate?: dayjs.Dayjs | null;
  details?: string | null;
  paymentUpdatedBy?: string | null;
  paymentUpdatedTime?: dayjs.Dayjs | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
}

export const defaultValue: Readonly<IPickupPayment> = {
  isActive: false,
};
