import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISale } from 'app/shared/model/sale.model';
import { getEntities } from './sale.reducer';

export const Sale = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const saleList = useAppSelector(state => state.sale.entities);
  const loading = useAppSelector(state => state.sale.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="sale-heading" data-cy="SaleHeading">
        <Translate contentKey="metalurgicaBckApp.sale.home.title">Sales</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="metalurgicaBckApp.sale.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/sale/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="metalurgicaBckApp.sale.home.createLabel">Create new Sale</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {saleList && saleList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="metalurgicaBckApp.sale.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="metalurgicaBckApp.sale.saleCode">Sale Code</Translate>
                </th>
                <th>
                  <Translate contentKey="metalurgicaBckApp.sale.date">Date</Translate>
                </th>
                <th>
                  <Translate contentKey="metalurgicaBckApp.sale.total">Total</Translate>
                </th>
                <th>
                  <Translate contentKey="metalurgicaBckApp.sale.salesPerson">Sales Person</Translate>
                </th>
                <th>
                  <Translate contentKey="metalurgicaBckApp.sale.record">Record</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {saleList.map((sale, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/sale/${sale.id}`} color="link" size="sm">
                      {sale.id}
                    </Button>
                  </td>
                  <td>{sale.saleCode}</td>
                  <td>{sale.date ? <TextFormat type="date" value={sale.date} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{sale.total}</td>
                  <td>{sale.salesPerson ? <Link to={`/sales-person/${sale.salesPerson.id}`}>{sale.salesPerson.id}</Link> : ''}</td>
                  <td>{sale.record ? <Link to={`/record/${sale.record.id}`}>{sale.record.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/sale/${sale.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/sale/${sale.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/sale/${sale.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="metalurgicaBckApp.sale.home.notFound">No Sales found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Sale;
