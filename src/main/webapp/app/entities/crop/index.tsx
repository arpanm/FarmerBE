import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Crop from './crop';
import CropDetail from './crop-detail';
import CropUpdate from './crop-update';
import CropDeleteDialog from './crop-delete-dialog';

const CropRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Crop />} />
    <Route path="new" element={<CropUpdate />} />
    <Route path=":id">
      <Route index element={<CropDetail />} />
      <Route path="edit" element={<CropUpdate />} />
      <Route path="delete" element={<CropDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CropRoutes;
