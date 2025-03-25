import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PickupGradation from './pickup-gradation';
import PickupGradationDetail from './pickup-gradation-detail';
import PickupGradationUpdate from './pickup-gradation-update';
import PickupGradationDeleteDialog from './pickup-gradation-delete-dialog';

const PickupGradationRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PickupGradation />} />
    <Route path="new" element={<PickupGradationUpdate />} />
    <Route path=":id">
      <Route index element={<PickupGradationDetail />} />
      <Route path="edit" element={<PickupGradationUpdate />} />
      <Route path="delete" element={<PickupGradationDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PickupGradationRoutes;
