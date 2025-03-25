import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './supply-confirmation.reducer';

export const SupplyConfirmationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const supplyConfirmationEntity = useAppSelector(state => state.supplyConfirmation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="supplyConfirmationDetailsHeading">
          <Translate contentKey="farmerBeApp.supplyConfirmation.detail.title">SupplyConfirmation</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{supplyConfirmationEntity.id}</dd>
          <dt>
            <span id="confirmDate">
              <Translate contentKey="farmerBeApp.supplyConfirmation.confirmDate">Confirm Date</Translate>
            </span>
          </dt>
          <dd>
            {supplyConfirmationEntity.confirmDate ? (
              <TextFormat value={supplyConfirmationEntity.confirmDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="confirmValue">
              <Translate contentKey="farmerBeApp.supplyConfirmation.confirmValue">Confirm Value</Translate>
            </span>
          </dt>
          <dd>{supplyConfirmationEntity.confirmValue}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="farmerBeApp.supplyConfirmation.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{supplyConfirmationEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="farmerBeApp.supplyConfirmation.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{supplyConfirmationEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="farmerBeApp.supplyConfirmation.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {supplyConfirmationEntity.createdTime ? (
              <TextFormat value={supplyConfirmationEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="farmerBeApp.supplyConfirmation.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{supplyConfirmationEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="farmerBeApp.supplyConfirmation.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {supplyConfirmationEntity.updatedTime ? (
              <TextFormat value={supplyConfirmationEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="farmerBeApp.supplyConfirmation.farm">Farm</Translate>
          </dt>
          <dd>{supplyConfirmationEntity.farm ? supplyConfirmationEntity.farm.id : ''}</dd>
          <dt>
            <Translate contentKey="farmerBeApp.supplyConfirmation.crop">Crop</Translate>
          </dt>
          <dd>{supplyConfirmationEntity.crop ? supplyConfirmationEntity.crop.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/supply-confirmation" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/supply-confirmation/${supplyConfirmationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SupplyConfirmationDetail;
