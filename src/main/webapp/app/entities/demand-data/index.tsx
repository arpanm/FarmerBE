import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DemandData from './demand-data';
import DemandDataDetail from './demand-data-detail';
import DemandDataUpdate from './demand-data-update';
import DemandDataDeleteDialog from './demand-data-delete-dialog';

const DemandDataRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DemandData />} />
    <Route path="new" element={<DemandDataUpdate />} />
    <Route path=":id">
      <Route index element={<DemandDataDetail />} />
      <Route path="edit" element={<DemandDataUpdate />} />
      <Route path="delete" element={<DemandDataDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DemandDataRoutes;
