import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './roow.reducer';

export const RoowDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const roowEntity = useAppSelector(state => state.roow.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="roowDetailsHeading">
          <Translate contentKey="metalurgicaBckApp.roow.detail.title">Roow</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{roowEntity.id}</dd>
          <dt>
            <span id="amountProduct">
              <Translate contentKey="metalurgicaBckApp.roow.amountProduct">Amount Product</Translate>
            </span>
          </dt>
          <dd>{roowEntity.amountProduct}</dd>
          <dt>
            <span id="subTotal">
              <Translate contentKey="metalurgicaBckApp.roow.subTotal">Sub Total</Translate>
            </span>
          </dt>
          <dd>{roowEntity.subTotal}</dd>
          <dt>
            <Translate contentKey="metalurgicaBckApp.roow.sale">Sale</Translate>
          </dt>
          <dd>{roowEntity.sale ? roowEntity.sale.id : ''}</dd>
          <dt>
            <Translate contentKey="metalurgicaBckApp.roow.product">Product</Translate>
          </dt>
          <dd>{roowEntity.product ? roowEntity.product.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/roow" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/roow/${roowEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RoowDetail;
