import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRoow } from 'app/shared/model/roow.model';
import { getEntities } from './roow.reducer';

export const Roow = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const roowList = useAppSelector(state => state.roow.entities);
  const loading = useAppSelector(state => state.roow.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="roow-heading" data-cy="RoowHeading">
        <Translate contentKey="metalurgicaBckApp.roow.home.title">Roows</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="metalurgicaBckApp.roow.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/roow/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="metalurgicaBckApp.roow.home.createLabel">Create new Roow</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {roowList && roowList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="metalurgicaBckApp.roow.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="metalurgicaBckApp.roow.amountProduct">Amount Product</Translate>
                </th>
                <th>
                  <Translate contentKey="metalurgicaBckApp.roow.subTotal">Sub Total</Translate>
                </th>
                <th>
                  <Translate contentKey="metalurgicaBckApp.roow.sale">Sale</Translate>
                </th>
                <th>
                  <Translate contentKey="metalurgicaBckApp.roow.product">Product</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {roowList.map((roow, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/roow/${roow.id}`} color="link" size="sm">
                      {roow.id}
                    </Button>
                  </td>
                  <td>{roow.amountProduct}</td>
                  <td>{roow.subTotal}</td>
                  <td>{roow.sale ? <Link to={`/sale/${roow.sale.id}`}>{roow.sale.id}</Link> : ''}</td>
                  <td>{roow.product ? <Link to={`/product/${roow.product.id}`}>{roow.product.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/roow/${roow.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/roow/${roow.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/roow/${roow.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="metalurgicaBckApp.roow.home.notFound">No Roows found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Roow;
