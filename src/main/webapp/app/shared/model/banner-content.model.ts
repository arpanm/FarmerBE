import dayjs from 'dayjs';
import { ICarouselContent } from 'app/shared/model/carousel-content.model';

export interface IBannerContent {
  id?: number;
  bannerTag?: string;
  logoPath?: string | null;
  imagePath?: string | null;
  heading?: string | null;
  subHeading?: string | null;
  description?: string | null;
  landingLink?: string | null;
  landingUtm?: string | null;
  pixelLink?: string | null;
  startTime?: dayjs.Dayjs | null;
  endTime?: dayjs.Dayjs | null;
  isActive?: boolean | null;
  createddBy?: string;
  createdTime?: dayjs.Dayjs;
  updatedBy?: string;
  updatedTime?: dayjs.Dayjs;
  holdingCarousel?: ICarouselContent | null;
}

export const defaultValue: Readonly<IBannerContent> = {
  isActive: false,
};
