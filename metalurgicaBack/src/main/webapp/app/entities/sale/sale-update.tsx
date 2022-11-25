import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISalesPerson } from 'app/shared/model/sales-person.model';
import { getEntities as getSalesPeople } from 'app/entities/sales-person/sales-person.reducer';
import { IRecord } from 'app/shared/model/record.model';
import { getEntities as getRecords } from 'app/entities/record/record.reducer';
import { ISale } from 'app/shared/model/sale.model';
import { getEntity, updateEntity, createEntity, reset } from './sale.reducer';

export const SaleUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const salesPeople = useAppSelector(state => state.salesPerson.entities);
  const records = useAppSelector(state => state.record.entities);
  const saleEntity = useAppSelector(state => state.sale.entity);
  const loading = useAppSelector(state => state.sale.loading);
  const updating = useAppSelector(state => state.sale.updating);
  const updateSuccess = useAppSelector(state => state.sale.updateSuccess);

  const handleClose = () => {
    navigate('/sale');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getSalesPeople({}));
    dispatch(getRecords({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...saleEntity,
      ...values,
      salesPerson: salesPeople.find(it => it.id.toString() === values.salesPerson.toString()),
      record: records.find(it => it.id.toString() === values.record.toString()),
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
          ...saleEntity,
          salesPerson: saleEntity?.salesPerson?.id,
          record: saleEntity?.record?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="metalurgicaBckApp.sale.home.createOrEditLabel" data-cy="SaleCreateUpdateHeading">
            <Translate contentKey="metalurgicaBckApp.sale.home.createOrEditLabel">Create or edit a Sale</Translate>
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
                  id="sale-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('metalurgicaBckApp.sale.saleCode')}
                id="sale-saleCode"
                name="saleCode"
                data-cy="saleCode"
                type="text"
              />
              <ValidatedField label={translate('metalurgicaBckApp.sale.date')} id="sale-date" name="date" data-cy="date" type="date" />
              <ValidatedField label={translate('metalurgicaBckApp.sale.total')} id="sale-total" name="total" data-cy="total" type="text" />
              <ValidatedField
                id="sale-salesPerson"
                name="salesPerson"
                data-cy="salesPerson"
                label={translate('metalurgicaBckApp.sale.salesPerson')}
                type="select"
              >
                <option value="" key="0" />
                {salesPeople
                  ? salesPeople.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="sale-record"
                name="record"
                data-cy="record"
                label={translate('metalurgicaBckApp.sale.record')}
                type="select"
              >
                <option value="" key="0" />
                {records
                  ? records.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/sale" replace color="info">
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

export default SaleUpdate;
