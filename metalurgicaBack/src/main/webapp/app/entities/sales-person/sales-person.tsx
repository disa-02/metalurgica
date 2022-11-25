import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISalesPerson } from 'app/shared/model/sales-person.model';
import { getEntities } from './sales-person.reducer';

export const SalesPerson = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const salesPersonList = useAppSelector(state => state.salesPerson.entities);
  const loading = useAppSelector(state => state.salesPerson.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="sales-person-heading" data-cy="SalesPersonHeading">
        <Translate contentKey="metalurgicaBckApp.salesPerson.home.title">Sales People</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="metalurgicaBckApp.salesPerson.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/sales-person/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="metalurgicaBckApp.salesPerson.home.createLabel">Create new Sales Person</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {salesPersonList && salesPersonList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="metalurgicaBckApp.salesPerson.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="metalurgicaBckApp.salesPerson.emloyee">Emloyee</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {salesPersonList.map((salesPerson, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/sales-person/${salesPerson.id}`} color="link" size="sm">
                      {salesPerson.id}
                    </Button>
                  </td>
                  <td>{salesPerson.emloyee ? <Link to={`/employee/${salesPerson.emloyee.name}`}>{salesPerson.emloyee.name}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/sales-person/${salesPerson.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/sales-person/${salesPerson.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/sales-person/${salesPerson.id}/delete`}
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
              <Translate contentKey="metalurgicaBckApp.salesPerson.home.notFound">No Sales People found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default SalesPerson;
