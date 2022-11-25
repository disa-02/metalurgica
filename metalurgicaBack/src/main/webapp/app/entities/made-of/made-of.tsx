import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMadeOf } from 'app/shared/model/made-of.model';
import { getEntities } from './made-of.reducer';

export const MadeOf = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const madeOfList = useAppSelector(state => state.madeOf.entities);
  const loading = useAppSelector(state => state.madeOf.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="made-of-heading" data-cy="MadeOfHeading">
        <Translate contentKey="metalurgicaBckApp.madeOf.home.title">Madeoves</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="metalurgicaBckApp.madeOf.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/made-of/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="metalurgicaBckApp.madeOf.home.createLabel">Create new Made Of</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {madeOfList && madeOfList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="metalurgicaBckApp.madeOf.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="metalurgicaBckApp.madeOf.amountMaterial">Amount Material</Translate>
                </th>
                <th>
                  <Translate contentKey="metalurgicaBckApp.madeOf.rawMaterial">Raw Material</Translate>
                </th>
                <th>
                  <Translate contentKey="metalurgicaBckApp.madeOf.product">Product</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {madeOfList.map((madeOf, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/made-of/${madeOf.id}`} color="link" size="sm">
                      {madeOf.id}
                    </Button>
                  </td>
                  <td>{madeOf.amountMaterial}</td>
                  <td>{madeOf.rawMaterial ? <Link to={`/raw-material/${madeOf.rawMaterial.id}`}>{madeOf.rawMaterial.id}</Link> : ''}</td>
                  <td>{madeOf.product ? <Link to={`/product/${madeOf.product.id}`}>{madeOf.product.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/made-of/${madeOf.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/made-of/${madeOf.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/made-of/${madeOf.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="metalurgicaBckApp.madeOf.home.notFound">No Madeoves found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default MadeOf;
