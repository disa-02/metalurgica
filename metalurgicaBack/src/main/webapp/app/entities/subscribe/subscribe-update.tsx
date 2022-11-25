import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INotification } from 'app/shared/model/notification.model';
import { getEntities as getNotifications } from 'app/entities/notification/notification.reducer';
import { IOperator } from 'app/shared/model/operator.model';
import { getEntities as getOperators } from 'app/entities/operator/operator.reducer';
import { ISubscribe } from 'app/shared/model/subscribe.model';
import { getEntity, updateEntity, createEntity, reset } from './subscribe.reducer';

export const SubscribeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const notifications = useAppSelector(state => state.notification.entities);
  const operators = useAppSelector(state => state.operator.entities);
  const subscribeEntity = useAppSelector(state => state.subscribe.entity);
  const loading = useAppSelector(state => state.subscribe.loading);
  const updating = useAppSelector(state => state.subscribe.updating);
  const updateSuccess = useAppSelector(state => state.subscribe.updateSuccess);

  const handleClose = () => {
    navigate('/subscribe');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getNotifications({}));
    dispatch(getOperators({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...subscribeEntity,
      ...values,
      notification: notifications.find(it => it.id.toString() === values.notification.toString()),
      operator: operators.find(it => it.id.toString() === values.operator.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...subscribeEntity,
          notification: subscribeEntity?.notification?.id,
          operator: subscribeEntity?.operator?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="metalurgicaBckApp.subscribe.home.createOrEditLabel" data-cy="SubscribeCreateUpdateHeading">
            <Translate contentKey="metalurgicaBckApp.subscribe.home.createOrEditLabel">Create or edit a Subscribe</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="subscribe-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                id="subscribe-notification"
                name="notification"
                data-cy="notification"
                label={translate('metalurgicaBckApp.subscribe.notification')}
                type="select"
              >
                <option value="" key="0" />
                {notifications
                  ? notifications.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="subscribe-operator"
                name="operator"
                data-cy="operator"
                label={translate('metalurgicaBckApp.subscribe.operator')}
                type="select"
              >
                <option value="" key="0" />
                {operators
                  ? operators.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/subscribe" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default SubscribeUpdate;
