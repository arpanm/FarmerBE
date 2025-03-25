import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SessionContext from './session-context';
import Farmer from './farmer';
import Address from './address';
import Document from './document';
import Otp from './otp';
import PanDetails from './pan-details';
import TermsAndCondition from './terms-and-condition';
import Farm from './farm';
import Category from './category';
import Crop from './crop';
import Accessories from './accessories';
import BankDetails from './bank-details';
import Demand from './demand';
import Price from './price';
import HervestPlan from './hervest-plan';
import SupplyConfirmation from './supply-confirmation';
import PickUpConfirmation from './pick-up-confirmation';
import PickupGradation from './pickup-gradation';
import PickupPayment from './pickup-payment';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="session-context/*" element={<SessionContext />} />
        <Route path="farmer/*" element={<Farmer />} />
        <Route path="address/*" element={<Address />} />
        <Route path="document/*" element={<Document />} />
        <Route path="otp/*" element={<Otp />} />
        <Route path="pan-details/*" element={<PanDetails />} />
        <Route path="terms-and-condition/*" element={<TermsAndCondition />} />
        <Route path="farm/*" element={<Farm />} />
        <Route path="category/*" element={<Category />} />
        <Route path="crop/*" element={<Crop />} />
        <Route path="accessories/*" element={<Accessories />} />
        <Route path="bank-details/*" element={<BankDetails />} />
        <Route path="demand/*" element={<Demand />} />
        <Route path="price/*" element={<Price />} />
        <Route path="hervest-plan/*" element={<HervestPlan />} />
        <Route path="supply-confirmation/*" element={<SupplyConfirmation />} />
        <Route path="pick-up-confirmation/*" element={<PickUpConfirmation />} />
        <Route path="pickup-gradation/*" element={<PickupGradation />} />
        <Route path="pickup-payment/*" element={<PickupPayment />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
