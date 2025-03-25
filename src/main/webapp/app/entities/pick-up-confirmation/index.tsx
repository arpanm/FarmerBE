import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PickUpConfirmation from './pick-up-confirmation';
import PickUpConfirmationDetail from './pick-up-confirmation-detail';
import PickUpConfirmationUpdate from './pick-up-confirmation-update';
import PickUpConfirmationDeleteDialog from './pick-up-confirmation-delete-dialog';

const PickUpConfirmationRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PickUpConfirmation />} />
    <Route path="new" element={<PickUpConfirmationUpdate />} />
    <Route path=":id">
      <Route index element={<PickUpConfirmationDetail />} />
      <Route path="edit" element={<PickUpConfirmationUpdate />} />
      <Route path="delete" element={<PickUpConfirmationDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PickUpConfirmationRoutes;
