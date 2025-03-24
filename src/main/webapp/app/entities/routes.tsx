import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SessionContext from './session-context';
import Farmer from './farmer';
import Address from './address';
import Document from './document';
import Otp from './otp';
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
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
