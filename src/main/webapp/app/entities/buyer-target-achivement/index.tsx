import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BuyerTargetAchivement from './buyer-target-achivement';
import BuyerTargetAchivementDetail from './buyer-target-achivement-detail';
import BuyerTargetAchivementUpdate from './buyer-target-achivement-update';
import BuyerTargetAchivementDeleteDialog from './buyer-target-achivement-delete-dialog';

const BuyerTargetAchivementRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BuyerTargetAchivement />} />
    <Route path="new" element={<BuyerTargetAchivementUpdate />} />
    <Route path=":id">
      <Route index element={<BuyerTargetAchivementDetail />} />
      <Route path="edit" element={<BuyerTargetAchivementUpdate />} />
      <Route path="delete" element={<BuyerTargetAchivementDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BuyerTargetAchivementRoutes;
