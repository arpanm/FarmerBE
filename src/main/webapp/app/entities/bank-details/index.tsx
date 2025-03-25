import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BankDetails from './bank-details';
import BankDetailsDetail from './bank-details-detail';
import BankDetailsUpdate from './bank-details-update';
import BankDetailsDeleteDialog from './bank-details-delete-dialog';

const BankDetailsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BankDetails />} />
    <Route path="new" element={<BankDetailsUpdate />} />
    <Route path=":id">
      <Route index element={<BankDetailsDetail />} />
      <Route path="edit" element={<BankDetailsUpdate />} />
      <Route path="delete" element={<BankDetailsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BankDetailsRoutes;
