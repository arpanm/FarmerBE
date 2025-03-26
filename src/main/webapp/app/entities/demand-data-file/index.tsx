import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DemandDataFile from './demand-data-file';
import DemandDataFileDetail from './demand-data-file-detail';
import DemandDataFileUpdate from './demand-data-file-update';
import DemandDataFileDeleteDialog from './demand-data-file-delete-dialog';

const DemandDataFileRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DemandDataFile />} />
    <Route path="new" element={<DemandDataFileUpdate />} />
    <Route path=":id">
      <Route index element={<DemandDataFileDetail />} />
      <Route path="edit" element={<DemandDataFileUpdate />} />
      <Route path="delete" element={<DemandDataFileDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DemandDataFileRoutes;
