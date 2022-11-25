import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './made-of.reducer';

export const MadeOfDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const madeOfEntity = useAppSelector(state => state.madeOf.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="madeOfDetailsHeading">
          <Translate contentKey="metalurgicaBckApp.madeOf.detail.title">MadeOf</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{madeOfEntity.id}</dd>
          <dt>
            <span id="amountMaterial">
              <Translate contentKey="metalurgicaBckApp.madeOf.amountMaterial">Amount Material</Translate>
            </span>
          </dt>
          <dd>{madeOfEntity.amountMaterial}</dd>
          <dt>
            <Translate contentKey="metalurgicaBckApp.madeOf.rawMaterial">Raw Material</Translate>
          </dt>
          <dd>{madeOfEntity.rawMaterial ? madeOfEntity.rawMaterial.id : ''}</dd>
          <dt>
            <Translate contentKey="metalurgicaBckApp.madeOf.product">Product</Translate>
          </dt>
          <dd>{madeOfEntity.product ? madeOfEntity.product.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/made-of" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/made-of/${madeOfEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MadeOfDetail;
