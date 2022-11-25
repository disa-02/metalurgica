import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './record.reducer';

export const RecordDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const recordEntity = useAppSelector(state => state.record.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="recordDetailsHeading">
          <Translate contentKey="metalurgicaBckApp.record.detail.title">Record</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{recordEntity.id}</dd>
          <dt>
            <span id="dateRange">
              <Translate contentKey="metalurgicaBckApp.record.dateRange">Date Range</Translate>
            </span>
          </dt>
          <dd>
            {recordEntity.dateRange ? <TextFormat value={recordEntity.dateRange} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="amount">
              <Translate contentKey="metalurgicaBckApp.record.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{recordEntity.amount}</dd>
        </dl>
        <Button tag={Link} to="/record" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/record/${recordEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RecordDetail;
