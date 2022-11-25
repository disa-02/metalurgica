import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Employee from './employee';
import Admin from './admin';
import SalesPerson from './sales-person';
import Operator from './operator';
import Notification from './notification';
import Subscribe from './subscribe';
import Sale from './sale';
import Record from './record';
import Roow from './roow';
import Product from './product';
import MadeOf from './made-of';
import RawMaterial from './raw-material';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="employee/*" element={<Employee />} />
        <Route path="admin/*" element={<Admin />} />
        <Route path="sales-person/*" element={<SalesPerson />} />
        <Route path="operator/*" element={<Operator />} />
        <Route path="notification/*" element={<Notification />} />
        <Route path="subscribe/*" element={<Subscribe />} />
        <Route path="sale/*" element={<Sale />} />
        <Route path="record/*" element={<Record />} />
        <Route path="roow/*" element={<Roow />} />
        <Route path="product/*" element={<Product />} />
        <Route path="made-of/*" element={<MadeOf />} />
        <Route path="raw-material/*" element={<RawMaterial />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
