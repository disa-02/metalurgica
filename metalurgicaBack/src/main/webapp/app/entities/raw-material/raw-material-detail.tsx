import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './raw-material.reducer';

export const RawMaterialDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const rawMaterialEntity = useAppSelector(state => state.rawMaterial.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="rawMaterialDetailsHeading">
          <Translate contentKey="metalurgicaBckApp.rawMaterial.detail.title">RawMaterial</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{rawMaterialEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="metalurgicaBckApp.rawMaterial.name">Name</Translate>
            </span>
          </dt>
          <dd>{rawMaterialEntity.name}</dd>
          <dt>
            <span id="stock">
              <Translate contentKey="metalurgicaBckApp.rawMaterial.stock">Stock</Translate>
            </span>
          </dt>
          <dd>{rawMaterialEntity.stock}</dd>
        </dl>
        <Button tag={Link} to="/raw-material" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/raw-material/${rawMaterialEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RawMaterialDetail;
