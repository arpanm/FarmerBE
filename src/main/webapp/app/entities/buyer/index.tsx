import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Buyer from './buyer';
import BuyerDetail from './buyer-detail';
import BuyerUpdate from './buyer-update';
import BuyerDeleteDialog from './buyer-delete-dialog';

const BuyerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Buyer />} />
    <Route path="new" element={<BuyerUpdate />} />
    <Route path=":id">
      <Route index element={<BuyerDetail />} />
      <Route path="edit" element={<BuyerUpdate />} />
      <Route path="delete" element={<BuyerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BuyerRoutes;
