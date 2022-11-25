import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Subscribe from './subscribe';
import SubscribeDetail from './subscribe-detail';
import SubscribeUpdate from './subscribe-update';
import SubscribeDeleteDialog from './subscribe-delete-dialog';

const SubscribeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Subscribe />} />
    <Route path="new" element={<SubscribeUpdate />} />
    <Route path=":id">
      <Route index element={<SubscribeDetail />} />
      <Route path="edit" element={<SubscribeUpdate />} />
      <Route path="delete" element={<SubscribeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SubscribeRoutes;
