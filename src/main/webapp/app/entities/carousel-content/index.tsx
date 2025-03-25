import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CarouselContent from './carousel-content';
import CarouselContentDetail from './carousel-content-detail';
import CarouselContentUpdate from './carousel-content-update';
import CarouselContentDeleteDialog from './carousel-content-delete-dialog';

const CarouselContentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CarouselContent />} />
    <Route path="new" element={<CarouselContentUpdate />} />
    <Route path=":id">
      <Route index element={<CarouselContentDetail />} />
      <Route path="edit" element={<CarouselContentUpdate />} />
      <Route path="delete" element={<CarouselContentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CarouselContentRoutes;
