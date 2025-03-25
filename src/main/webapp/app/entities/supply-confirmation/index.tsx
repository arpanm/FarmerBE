import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SupplyConfirmation from './supply-confirmation';
import SupplyConfirmationDetail from './supply-confirmation-detail';
import SupplyConfirmationUpdate from './supply-confirmation-update';
import SupplyConfirmationDeleteDialog from './supply-confirmation-delete-dialog';

const SupplyConfirmationRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SupplyConfirmation />} />
    <Route path="new" element={<SupplyConfirmationUpdate />} />
    <Route path=":id">
      <Route index element={<SupplyConfirmationDetail />} />
      <Route path="edit" element={<SupplyConfirmationUpdate />} />
      <Route path="delete" element={<SupplyConfirmationDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SupplyConfirmationRoutes;
