import dayjs from 'dayjs';

export interface ICarouselContent {
  id?: number;
  carouselTag?: string;
  showViewMore?: boolean | null;
  viewMoreLink?: string | null;
  viewMoreUtm?: string | null;
  pixelLink?: string | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
}

export const defaultValue: Readonly<ICarouselContent> = {
  showViewMore: false,
  isActive: false,
};
