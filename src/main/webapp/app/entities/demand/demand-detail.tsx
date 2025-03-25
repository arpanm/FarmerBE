import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './demand.reducer';

export const DemandDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const demandEntity = useAppSelector(state => state.demand.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="demandDetailsHeading">
          <Translate contentKey="farmerBeApp.demand.detail.title">Demand</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{demandEntity.id}</dd>
          <dt>
            <span id="demandDate">
              <Translate contentKey="farmerBeApp.demand.demandDate">Demand Date</Translate>
            </span>
          </dt>
          <dd>
            {demandEntity.demandDate ? <TextFormat value={demandEntity.demandDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="demandValue">
              <Translate contentKey="farmerBeApp.demand.demandValue">Demand Value</Translate>
            </span>
          </dt>
          <dd>{demandEntity.demandValue}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="farmerBeApp.demand.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{demandEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="farmerBeApp.demand.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{demandEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="farmerBeApp.demand.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>{demandEntity.createdTime ? <TextFormat value={demandEntity.createdTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="farmerBeApp.demand.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{demandEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="farmerBeApp.demand.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>{demandEntity.updatedTime ? <TextFormat value={demandEntity.updatedTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="farmerBeApp.demand.crop">Crop</Translate>
          </dt>
          <dd>{demandEntity.crop ? demandEntity.crop.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/demand" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/demand/${demandEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DemandDetail;
