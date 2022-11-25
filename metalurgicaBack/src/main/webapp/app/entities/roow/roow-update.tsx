import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISale } from 'app/shared/model/sale.model';
import { getEntities as getSales } from 'app/entities/sale/sale.reducer';
import { IProduct } from 'app/shared/model/product.model';
import { getEntities as getProducts } from 'app/entities/product/product.reducer';
import { IRoow } from 'app/shared/model/roow.model';
import { getEntity, updateEntity, createEntity, reset } from './roow.reducer';

export const RoowUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const sales = useAppSelector(state => state.sale.entities);
  const products = useAppSelector(state => state.product.entities);
  const roowEntity = useAppSelector(state => state.roow.entity);
  const loading = useAppSelector(state => state.roow.loading);
  const updating = useAppSelector(state => state.roow.updating);
  const updateSuccess = useAppSelector(state => state.roow.updateSuccess);

  const handleClose = () => {
    navigate('/roow');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getSales({}));
    dispatch(getProducts({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...roowEntity,
      ...values,
      sale: sales.find(it => it.id.toString() === values.sale.toString()),
      product: products.find(it => it.id.toString() === values.product.toString()),
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
          ...roowEntity,
          sale: roowEntity?.sale?.id,
          product: roowEntity?.product?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="metalurgicaBckApp.roow.home.createOrEditLabel" data-cy="RoowCreateUpdateHeading">
            <Translate contentKey="metalurgicaBckApp.roow.home.createOrEditLabel">Create or edit a Roow</Translate>
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
                  id="roow-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('metalurgicaBckApp.roow.amountProduct')}
                id="roow-amountProduct"
                name="amountProduct"
                data-cy="amountProduct"
                type="text"
              />
              <ValidatedField
                label={translate('metalurgicaBckApp.roow.subTotal')}
                id="roow-subTotal"
                name="subTotal"
                data-cy="subTotal"
                type="text"
              />
              <ValidatedField id="roow-sale" name="sale" data-cy="sale" label={translate('metalurgicaBckApp.roow.sale')} type="select">
                <option value="" key="0" />
                {sales
                  ? sales.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="roow-product"
                name="product"
                data-cy="product"
                label={translate('metalurgicaBckApp.roow.product')}
                type="select"
              >
                <option value="" key="0" />
                {products
                  ? products.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/roow" replace color="info">
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

export default RoowUpdate;
