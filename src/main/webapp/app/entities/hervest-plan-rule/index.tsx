import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import HervestPlanRule from './hervest-plan-rule';
import HervestPlanRuleDetail from './hervest-plan-rule-detail';
import HervestPlanRuleUpdate from './hervest-plan-rule-update';
import HervestPlanRuleDeleteDialog from './hervest-plan-rule-delete-dialog';

const HervestPlanRuleRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<HervestPlanRule />} />
    <Route path="new" element={<HervestPlanRuleUpdate />} />
    <Route path=":id">
      <Route index element={<HervestPlanRuleDetail />} />
      <Route path="edit" element={<HervestPlanRuleUpdate />} />
      <Route path="delete" element={<HervestPlanRuleDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HervestPlanRuleRoutes;
