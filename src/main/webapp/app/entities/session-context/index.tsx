import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SessionContext from './session-context';
import SessionContextDetail from './session-context-detail';
import SessionContextUpdate from './session-context-update';
import SessionContextDeleteDialog from './session-context-delete-dialog';

const SessionContextRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SessionContext />} />
    <Route path="new" element={<SessionContextUpdate />} />
    <Route path=":id">
      <Route index element={<SessionContextDetail />} />
      <Route path="edit" element={<SessionContextUpdate />} />
      <Route path="delete" element={<SessionContextDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SessionContextRoutes;
