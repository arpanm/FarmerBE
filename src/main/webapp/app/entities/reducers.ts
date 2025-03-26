import sessionContext from 'app/entities/session-context/session-context.reducer';
import farmer from 'app/entities/farmer/farmer.reducer';
import address from 'app/entities/address/address.reducer';
import document from 'app/entities/document/document.reducer';
import otp from 'app/entities/otp/otp.reducer';
import panDetails from 'app/entities/pan-details/pan-details.reducer';
import termsAndCondition from 'app/entities/terms-and-condition/terms-and-condition.reducer';
import farm from 'app/entities/farm/farm.reducer';
import category from 'app/entities/category/category.reducer';
import crop from 'app/entities/crop/crop.reducer';
import accessories from 'app/entities/accessories/accessories.reducer';
import bankDetails from 'app/entities/bank-details/bank-details.reducer';
import demand from 'app/entities/demand/demand.reducer';
import price from 'app/entities/price/price.reducer';
import hervestPlan from 'app/entities/hervest-plan/hervest-plan.reducer';
import supplyConfirmation from 'app/entities/supply-confirmation/supply-confirmation.reducer';
import pickUpConfirmation from 'app/entities/pick-up-confirmation/pick-up-confirmation.reducer';
import pickupGradation from 'app/entities/pickup-gradation/pickup-gradation.reducer';
import pickupPayment from 'app/entities/pickup-payment/pickup-payment.reducer';
import bannerContent from 'app/entities/banner-content/banner-content.reducer';
import carouselContent from 'app/entities/carousel-content/carousel-content.reducer';
import hervestPlanRule from 'app/entities/hervest-plan-rule/hervest-plan-rule.reducer';
import collectionCenter from 'app/entities/collection-center/collection-center.reducer';
import locationMapping from 'app/entities/location-mapping/location-mapping.reducer';
import demandData from 'app/entities/demand-data/demand-data.reducer';
import demandDataFile from 'app/entities/demand-data-file/demand-data-file.reducer';
import buyer from 'app/entities/buyer/buyer.reducer';
import attendence from 'app/entities/attendence/attendence.reducer';
import fieldVisit from 'app/entities/field-visit/field-visit.reducer';
import buyerTargetAchivement from 'app/entities/buyer-target-achivement/buyer-target-achivement.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  sessionContext,
  farmer,
  address,
  document,
  otp,
  panDetails,
  termsAndCondition,
  farm,
  category,
  crop,
  accessories,
  bankDetails,
  demand,
  price,
  hervestPlan,
  supplyConfirmation,
  pickUpConfirmation,
  pickupGradation,
  pickupPayment,
  bannerContent,
  carouselContent,
  hervestPlanRule,
  collectionCenter,
  locationMapping,
  demandData,
  demandDataFile,
  buyer,
  attendence,
  fieldVisit,
  buyerTargetAchivement,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
