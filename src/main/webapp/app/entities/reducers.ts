import sessionContext from 'app/entities/session-context/session-context.reducer';
import farmer from 'app/entities/farmer/farmer.reducer';
import address from 'app/entities/address/address.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  sessionContext,
  farmer,
  address,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
