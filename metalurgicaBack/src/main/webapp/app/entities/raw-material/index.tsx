import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RawMaterial from './raw-material';
import RawMaterialDetail from './raw-material-detail';
import RawMaterialUpdate from './raw-material-update';
import RawMaterialDeleteDialog from './raw-material-delete-dialog';

const RawMaterialRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RawMaterial />} />
    <Route path="new" element={<RawMaterialUpdate />} />
    <Route path=":id">
      <Route index element={<RawMaterialDetail />} />
      <Route path="edit" element={<RawMaterialUpdate />} />
      <Route path="delete" element={<RawMaterialDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RawMaterialRoutes;
