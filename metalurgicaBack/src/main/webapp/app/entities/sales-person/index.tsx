import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SalesPerson from './sales-person';
import SalesPersonDetail from './sales-person-detail';
import SalesPersonUpdate from './sales-person-update';
import SalesPersonDeleteDialog from './sales-person-delete-dialog';

const SalesPersonRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SalesPerson />} />
    <Route path="new" element={<SalesPersonUpdate />} />
    <Route path=":id">
      <Route index element={<SalesPersonDetail />} />
      <Route path="edit" element={<SalesPersonUpdate />} />
      <Route path="delete" element={<SalesPersonDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SalesPersonRoutes;
