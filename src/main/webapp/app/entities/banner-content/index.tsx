import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BannerContent from './banner-content';
import BannerContentDetail from './banner-content-detail';
import BannerContentUpdate from './banner-content-update';
import BannerContentDeleteDialog from './banner-content-delete-dialog';

const BannerContentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BannerContent />} />
    <Route path="new" element={<BannerContentUpdate />} />
    <Route path=":id">
      <Route index element={<BannerContentDetail />} />
      <Route path="edit" element={<BannerContentUpdate />} />
      <Route path="delete" element={<BannerContentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BannerContentRoutes;
