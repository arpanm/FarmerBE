import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Demand from './demand';
import DemandDetail from './demand-detail';
import DemandUpdate from './demand-update';
import DemandDeleteDialog from './demand-delete-dialog';

const DemandRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Demand />} />
    <Route path="new" element={<DemandUpdate />} />
    <Route path=":id">
      <Route index element={<DemandDetail />} />
      <Route path="edit" element={<DemandUpdate />} />
      <Route path="delete" element={<DemandDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DemandRoutes;
