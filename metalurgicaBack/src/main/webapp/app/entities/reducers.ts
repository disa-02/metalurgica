import employee from 'app/entities/employee/employee.reducer';
import admin from 'app/entities/admin/admin.reducer';
import salesPerson from 'app/entities/sales-person/sales-person.reducer';
import operator from 'app/entities/operator/operator.reducer';
import notification from 'app/entities/notification/notification.reducer';
import subscribe from 'app/entities/subscribe/subscribe.reducer';
import sale from 'app/entities/sale/sale.reducer';
import record from 'app/entities/record/record.reducer';
import roow from 'app/entities/roow/roow.reducer';
import product from 'app/entities/product/product.reducer';
import madeOf from 'app/entities/made-of/made-of.reducer';
import rawMaterial from 'app/entities/raw-material/raw-material.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  employee,
  admin,
  salesPerson,
  operator,
  notification,
  subscribe,
  sale,
  record,
  roow,
  product,
  madeOf,
  rawMaterial,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
