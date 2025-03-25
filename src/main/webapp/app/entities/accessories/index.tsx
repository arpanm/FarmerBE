import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Accessories from './accessories';
import AccessoriesDetail from './accessories-detail';
import AccessoriesUpdate from './accessories-update';
import AccessoriesDeleteDialog from './accessories-delete-dialog';

const AccessoriesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Accessories />} />
    <Route path="new" element={<AccessoriesUpdate />} />
    <Route path=":id">
      <Route index element={<AccessoriesDetail />} />
      <Route path="edit" element={<AccessoriesUpdate />} />
      <Route path="delete" element={<AccessoriesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AccessoriesRoutes;
