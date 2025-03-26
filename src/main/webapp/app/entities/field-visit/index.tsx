import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FieldVisit from './field-visit';
import FieldVisitDetail from './field-visit-detail';
import FieldVisitUpdate from './field-visit-update';
import FieldVisitDeleteDialog from './field-visit-delete-dialog';

const FieldVisitRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FieldVisit />} />
    <Route path="new" element={<FieldVisitUpdate />} />
    <Route path=":id">
      <Route index element={<FieldVisitDetail />} />
      <Route path="edit" element={<FieldVisitUpdate />} />
      <Route path="delete" element={<FieldVisitDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FieldVisitRoutes;
