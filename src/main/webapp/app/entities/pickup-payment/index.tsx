import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PickupPayment from './pickup-payment';
import PickupPaymentDetail from './pickup-payment-detail';
import PickupPaymentUpdate from './pickup-payment-update';
import PickupPaymentDeleteDialog from './pickup-payment-delete-dialog';

const PickupPaymentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PickupPayment />} />
    <Route path="new" element={<PickupPaymentUpdate />} />
    <Route path=":id">
      <Route index element={<PickupPaymentDetail />} />
      <Route path="edit" element={<PickupPaymentUpdate />} />
      <Route path="delete" element={<PickupPaymentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PickupPaymentRoutes;
