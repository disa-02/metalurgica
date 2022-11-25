import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISubscribe } from 'app/shared/model/subscribe.model';
import { getEntities } from './subscribe.reducer';

export const Subscribe = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const subscribeList = useAppSelector(state => state.subscribe.entities);
  const loading = useAppSelector(state => state.subscribe.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="subscribe-heading" data-cy="SubscribeHeading">
        <Translate contentKey="metalurgicaBckApp.subscribe.home.title">Subscribes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="metalurgicaBckApp.subscribe.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/subscribe/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="metalurgicaBckApp.subscribe.home.createLabel">Create new Subscribe</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {subscribeList && subscribeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="metalurgicaBckApp.subscribe.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="metalurgicaBckApp.subscribe.notification">Notification</Translate>
                </th>
                <th>
                  <Translate contentKey="metalurgicaBckApp.subscribe.operator">Operator</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {subscribeList.map((subscribe, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/subscribe/${subscribe.id}`} color="link" size="sm">
                      {subscribe.id}
                    </Button>
                  </td>
                  <td>
                    {subscribe.notification ? (
                      <Link to={`/notification/${subscribe.notification.id}`}>{subscribe.notification.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{subscribe.operator ? <Link to={`/operator/${subscribe.operator.id}`}>{subscribe.operator.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/subscribe/${subscribe.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/subscribe/${subscribe.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/subscribe/${subscribe.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="metalurgicaBckApp.subscribe.home.notFound">No Subscribes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Subscribe;
