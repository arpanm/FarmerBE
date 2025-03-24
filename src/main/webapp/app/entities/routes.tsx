import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SessionContext from './session-context';
import Farmer from './farmer';
import Address from './address';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="session-context/*" element={<SessionContext />} />
        <Route path="farmer/*" element={<Farmer />} />
        <Route path="address/*" element={<Address />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
