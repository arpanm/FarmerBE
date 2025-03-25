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
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
