import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TermsAndCondition from './terms-and-condition';
import TermsAndConditionDetail from './terms-and-condition-detail';
import TermsAndConditionUpdate from './terms-and-condition-update';
import TermsAndConditionDeleteDialog from './terms-and-condition-delete-dialog';

const TermsAndConditionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TermsAndCondition />} />
    <Route path="new" element={<TermsAndConditionUpdate />} />
    <Route path=":id">
      <Route index element={<TermsAndConditionDetail />} />
      <Route path="edit" element={<TermsAndConditionUpdate />} />
      <Route path="delete" element={<TermsAndConditionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TermsAndConditionRoutes;
