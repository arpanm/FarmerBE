import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './pick-up-confirmation.reducer';

export const PickUpConfirmationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const pickUpConfirmationEntity = useAppSelector(state => state.pickUpConfirmation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pickUpConfirmationDetailsHeading">
          <Translate contentKey="farmerBeApp.pickUpConfirmation.detail.title">PickUpConfirmation</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{pickUpConfirmationEntity.id}</dd>
          <dt>
            <span id="confirmDate">
              <Translate contentKey="farmerBeApp.pickUpConfirmation.confirmDate">Confirm Date</Translate>
            </span>
          </dt>
          <dd>
            {pickUpConfirmationEntity.confirmDate ? (
              <TextFormat value={pickUpConfirmationEntity.confirmDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="confirmValue">
              <Translate contentKey="farmerBeApp.pickUpConfirmation.confirmValue">Confirm Value</Translate>
            </span>
          </dt>
          <dd>{pickUpConfirmationEntity.confirmValue}</dd>
          <dt>
            <span id="pickupBy">
              <Translate contentKey="farmerBeApp.pickUpConfirmation.pickupBy">Pickup By</Translate>
            </span>
          </dt>
          <dd>{pickUpConfirmationEntity.pickupBy}</dd>
          <dt>
            <span id="pickupTime">
              <Translate contentKey="farmerBeApp.pickUpConfirmation.pickupTime">Pickup Time</Translate>
            </span>
          </dt>
          <dd>{pickUpConfirmationEntity.pickupTime}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="farmerBeApp.pickUpConfirmation.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{pickUpConfirmationEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="farmerBeApp.pickUpConfirmation.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{pickUpConfirmationEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="farmerBeApp.pickUpConfirmation.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {pickUpConfirmationEntity.createdTime ? (
              <TextFormat value={pickUpConfirmationEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="farmerBeApp.pickUpConfirmation.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{pickUpConfirmationEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="farmerBeApp.pickUpConfirmation.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {pickUpConfirmationEntity.updatedTime ? (
              <TextFormat value={pickUpConfirmationEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="farmerBeApp.pickUpConfirmation.farm">Farm</Translate>
          </dt>
          <dd>{pickUpConfirmationEntity.farm ? pickUpConfirmationEntity.farm.id : ''}</dd>
          <dt>
            <Translate contentKey="farmerBeApp.pickUpConfirmation.crop">Crop</Translate>
          </dt>
          <dd>{pickUpConfirmationEntity.crop ? pickUpConfirmationEntity.crop.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/pick-up-confirmation" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pick-up-confirmation/${pickUpConfirmationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PickUpConfirmationDetail;
