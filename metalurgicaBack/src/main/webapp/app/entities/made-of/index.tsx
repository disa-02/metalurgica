import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MadeOf from './made-of';
import MadeOfDetail from './made-of-detail';
import MadeOfUpdate from './made-of-update';
import MadeOfDeleteDialog from './made-of-delete-dialog';

const MadeOfRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MadeOf />} />
    <Route path="new" element={<MadeOfUpdate />} />
    <Route path=":id">
      <Route index element={<MadeOfDetail />} />
      <Route path="edit" element={<MadeOfUpdate />} />
      <Route path="delete" element={<MadeOfDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MadeOfRoutes;
