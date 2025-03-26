import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Attendence from './attendence';
import AttendenceDetail from './attendence-detail';
import AttendenceUpdate from './attendence-update';
import AttendenceDeleteDialog from './attendence-delete-dialog';

const AttendenceRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Attendence />} />
    <Route path="new" element={<AttendenceUpdate />} />
    <Route path=":id">
      <Route index element={<AttendenceDetail />} />
      <Route path="edit" element={<AttendenceUpdate />} />
      <Route path="delete" element={<AttendenceDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AttendenceRoutes;
