import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PanDetails from './pan-details';
import PanDetailsDetail from './pan-details-detail';
import PanDetailsUpdate from './pan-details-update';
import PanDetailsDeleteDialog from './pan-details-delete-dialog';

const PanDetailsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PanDetails />} />
    <Route path="new" element={<PanDetailsUpdate />} />
    <Route path=":id">
      <Route index element={<PanDetailsDetail />} />
      <Route path="edit" element={<PanDetailsUpdate />} />
      <Route path="delete" element={<PanDetailsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PanDetailsRoutes;
