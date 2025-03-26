import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CollectionCenter from './collection-center';
import CollectionCenterDetail from './collection-center-detail';
import CollectionCenterUpdate from './collection-center-update';
import CollectionCenterDeleteDialog from './collection-center-delete-dialog';

const CollectionCenterRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CollectionCenter />} />
    <Route path="new" element={<CollectionCenterUpdate />} />
    <Route path=":id">
      <Route index element={<CollectionCenterDetail />} />
      <Route path="edit" element={<CollectionCenterUpdate />} />
      <Route path="delete" element={<CollectionCenterDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CollectionCenterRoutes;
