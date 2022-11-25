import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRawMaterial } from 'app/shared/model/raw-material.model';
import { getEntities } from './raw-material.reducer';

export const RawMaterial = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const rawMaterialList = useAppSelector(state => state.rawMaterial.entities);
  const loading = useAppSelector(state => state.rawMaterial.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="raw-material-heading" data-cy="RawMaterialHeading">
        <Translate contentKey="metalurgicaBckApp.rawMaterial.home.title">Raw Materials</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="metalurgicaBckApp.rawMaterial.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/raw-material/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="metalurgicaBckApp.rawMaterial.home.createLabel">Create new Raw Material</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {rawMaterialList && rawMaterialList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="metalurgicaBckApp.rawMaterial.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="metalurgicaBckApp.rawMaterial.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="metalurgicaBckApp.rawMaterial.stock">Stock</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {rawMaterialList.map((rawMaterial, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/raw-material/${rawMaterial.id}`} color="link" size="sm">
                      {rawMaterial.id}
                    </Button>
                  </td>
                  <td>{rawMaterial.name}</td>
                  <td>{rawMaterial.stock}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/raw-material/${rawMaterial.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/raw-material/${rawMaterial.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/raw-material/${rawMaterial.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
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
              <Translate contentKey="metalurgicaBckApp.rawMaterial.home.notFound">No Raw Materials found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default RawMaterial;
