import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './otp.reducer';

export const OtpDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const otpEntity = useAppSelector(state => state.otp.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="otpDetailsHeading">
          <Translate contentKey="farmerBeApp.otp.detail.title">Otp</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{otpEntity.id}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="farmerBeApp.otp.email">Email</Translate>
            </span>
          </dt>
          <dd>{otpEntity.email}</dd>
          <dt>
            <span id="emailOtp">
              <Translate contentKey="farmerBeApp.otp.emailOtp">Email Otp</Translate>
            </span>
          </dt>
          <dd>{otpEntity.emailOtp}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="farmerBeApp.otp.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{otpEntity.phone}</dd>
          <dt>
            <span id="phoneOtp">
              <Translate contentKey="farmerBeApp.otp.phoneOtp">Phone Otp</Translate>
            </span>
          </dt>
          <dd>{otpEntity.phoneOtp}</dd>
          <dt>
            <span id="isValidated">
              <Translate contentKey="farmerBeApp.otp.isValidated">Is Validated</Translate>
            </span>
          </dt>
          <dd>{otpEntity.isValidated ? 'true' : 'false'}</dd>
          <dt>
            <span id="expiryTime">
              <Translate contentKey="farmerBeApp.otp.expiryTime">Expiry Time</Translate>
            </span>
          </dt>
          <dd>{otpEntity.expiryTime ? <TextFormat value={otpEntity.expiryTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="farmerBeApp.otp.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{otpEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="farmerBeApp.otp.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{otpEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="farmerBeApp.otp.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>{otpEntity.createdTime ? <TextFormat value={otpEntity.createdTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="farmerBeApp.otp.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{otpEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="farmerBeApp.otp.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>{otpEntity.updatedTime ? <TextFormat value={otpEntity.updatedTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="farmerBeApp.otp.farmer">Farmer</Translate>
          </dt>
          <dd>{otpEntity.farmer ? otpEntity.farmer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/otp" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/otp/${otpEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OtpDetail;
