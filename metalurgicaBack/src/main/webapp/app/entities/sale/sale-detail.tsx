import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './sale.reducer';

export const SaleDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const saleEntity = useAppSelector(state => state.sale.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="saleDetailsHeading">
          <Translate contentKey="metalurgicaBckApp.sale.detail.title">Sale</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="saleCode">
              <Translate contentKey="metalurgicaBckApp.sale.saleCode">Sale Code</Translate>
            </span>
          </dt>
          <dd>{saleEntity.saleCode}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="metalurgicaBckApp.sale.date">Date</Translate>
            </span>
          </dt>
          <dd>{saleEntity.date ? <TextFormat value={saleEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="total">
              <Translate contentKey="metalurgicaBckApp.sale.total">Total</Translate>
            </span>
          </dt>
          <dd>{saleEntity.total}</dd>
          <dt>
            <Translate contentKey="metalurgicaBckApp.sale.salesPerson">Sales Person</Translate>
          </dt>
          <dd>{saleEntity.salesPerson ? saleEntity.salesPerson.id : ''}</dd>
          <dt>
            <Translate contentKey="metalurgicaBckApp.sale.record">Record</Translate>
          </dt>
          <dd>{saleEntity.record ? saleEntity.record.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/sale" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sale/${saleEntity.saleCode}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SaleDetail;
