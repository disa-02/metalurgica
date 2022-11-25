import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRawMaterial } from 'app/shared/model/raw-material.model';
import { getEntities as getRawMaterials } from 'app/entities/raw-material/raw-material.reducer';
import { IProduct } from 'app/shared/model/product.model';
import { getEntities as getProducts } from 'app/entities/product/product.reducer';
import { IMadeOf } from 'app/shared/model/made-of.model';
import { getEntity, updateEntity, createEntity, reset } from './made-of.reducer';

export const MadeOfUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const rawMaterials = useAppSelector(state => state.rawMaterial.entities);
  const products = useAppSelector(state => state.product.entities);
  const madeOfEntity = useAppSelector(state => state.madeOf.entity);
  const loading = useAppSelector(state => state.madeOf.loading);
  const updating = useAppSelector(state => state.madeOf.updating);
  const updateSuccess = useAppSelector(state => state.madeOf.updateSuccess);

  const handleClose = () => {
    navigate('/made-of');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRawMaterials({}));
    dispatch(getProducts({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...madeOfEntity,
      ...values,
      rawMaterial: rawMaterials.find(it => it.id.toString() === values.rawMaterial.toString()),
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
          ...madeOfEntity,
          rawMaterial: madeOfEntity?.rawMaterial?.id,
          product: madeOfEntity?.product?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="metalurgicaBckApp.madeOf.home.createOrEditLabel" data-cy="MadeOfCreateUpdateHeading">
            <Translate contentKey="metalurgicaBckApp.madeOf.home.createOrEditLabel">Create or edit a MadeOf</Translate>
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
                  id="made-of-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('metalurgicaBckApp.madeOf.amountMaterial')}
                id="made-of-amountMaterial"
                name="amountMaterial"
                data-cy="amountMaterial"
                type="text"
              />
              <ValidatedField
                id="made-of-rawMaterial"
                name="rawMaterial"
                data-cy="rawMaterial"
                label={translate('metalurgicaBckApp.madeOf.rawMaterial')}
                type="select"
              >
                <option value="" key="0" />
                {rawMaterials
                  ? rawMaterials.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="made-of-product"
                name="product"
                data-cy="product"
                label={translate('metalurgicaBckApp.madeOf.product')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/made-of" replace color="info">
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

export default MadeOfUpdate;
