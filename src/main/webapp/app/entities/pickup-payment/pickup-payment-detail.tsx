import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './pickup-payment.reducer';

export const PickupPaymentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const pickupPaymentEntity = useAppSelector(state => state.pickupPayment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pickupPaymentDetailsHeading">
          <Translate contentKey="farmerBeApp.pickupPayment.detail.title">PickupPayment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{pickupPaymentEntity.id}</dd>
          <dt>
            <span id="paymentStatus">
              <Translate contentKey="farmerBeApp.pickupPayment.paymentStatus">Payment Status</Translate>
            </span>
          </dt>
          <dd>{pickupPaymentEntity.paymentStatus}</dd>
          <dt>
            <span id="transactionId">
              <Translate contentKey="farmerBeApp.pickupPayment.transactionId">Transaction Id</Translate>
            </span>
          </dt>
          <dd>{pickupPaymentEntity.transactionId}</dd>
          <dt>
            <span id="paymentDate">
              <Translate contentKey="farmerBeApp.pickupPayment.paymentDate">Payment Date</Translate>
            </span>
          </dt>
          <dd>
            {pickupPaymentEntity.paymentDate ? (
              <TextFormat value={pickupPaymentEntity.paymentDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="details">
              <Translate contentKey="farmerBeApp.pickupPayment.details">Details</Translate>
            </span>
          </dt>
          <dd>{pickupPaymentEntity.details}</dd>
          <dt>
            <span id="paymentUpdatedBy">
              <Translate contentKey="farmerBeApp.pickupPayment.paymentUpdatedBy">Payment Updated By</Translate>
            </span>
          </dt>
          <dd>{pickupPaymentEntity.paymentUpdatedBy}</dd>
          <dt>
            <span id="paymentUpdatedTime">
              <Translate contentKey="farmerBeApp.pickupPayment.paymentUpdatedTime">Payment Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {pickupPaymentEntity.paymentUpdatedTime ? (
              <TextFormat value={pickupPaymentEntity.paymentUpdatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="farmerBeApp.pickupPayment.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{pickupPaymentEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="farmerBeApp.pickupPayment.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{pickupPaymentEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="farmerBeApp.pickupPayment.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {pickupPaymentEntity.createdTime ? (
              <TextFormat value={pickupPaymentEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="farmerBeApp.pickupPayment.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{pickupPaymentEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="farmerBeApp.pickupPayment.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {pickupPaymentEntity.updatedTime ? (
              <TextFormat value={pickupPaymentEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/pickup-payment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pickup-payment/${pickupPaymentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PickupPaymentDetail;
