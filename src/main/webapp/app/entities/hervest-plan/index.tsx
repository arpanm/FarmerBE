import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import HervestPlan from './hervest-plan';
import HervestPlanDetail from './hervest-plan-detail';
import HervestPlanUpdate from './hervest-plan-update';
import HervestPlanDeleteDialog from './hervest-plan-delete-dialog';

const HervestPlanRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<HervestPlan />} />
    <Route path="new" element={<HervestPlanUpdate />} />
    <Route path=":id">
      <Route index element={<HervestPlanDetail />} />
      <Route path="edit" element={<HervestPlanUpdate />} />
      <Route path="delete" element={<HervestPlanDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HervestPlanRoutes;
