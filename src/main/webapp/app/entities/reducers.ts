import sessionContext from 'app/entities/session-context/session-context.reducer';
import farmer from 'app/entities/farmer/farmer.reducer';
import address from 'app/entities/address/address.reducer';
import document from 'app/entities/document/document.reducer';
import otp from 'app/entities/otp/otp.reducer';
import panDetails from 'app/entities/pan-details/pan-details.reducer';
import termsAndCondition from 'app/entities/terms-and-condition/terms-and-condition.reducer';
import farm from 'app/entities/farm/farm.reducer';
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
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
