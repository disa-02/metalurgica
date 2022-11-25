import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Roow from './roow';
import RoowDetail from './roow-detail';
import RoowUpdate from './roow-update';
import RoowDeleteDialog from './roow-delete-dialog';

const RoowRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Roow />} />
    <Route path="new" element={<RoowUpdate />} />
    <Route path=":id">
      <Route index element={<RoowDetail />} />
      <Route path="edit" element={<RoowUpdate />} />
      <Route path="delete" element={<RoowDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RoowRoutes;
