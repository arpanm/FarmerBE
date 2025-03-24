import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './address.reducer';

export const AddressDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const addressEntity = useAppSelector(state => state.address.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="addressDetailsHeading">
          <Translate contentKey="farmerBeApp.address.detail.title">Address</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{addressEntity.id}</dd>
          <dt>
            <span id="line1">
              <Translate contentKey="farmerBeApp.address.line1">Line 1</Translate>
            </span>
          </dt>
          <dd>{addressEntity.line1}</dd>
          <dt>
            <span id="line2">
              <Translate contentKey="farmerBeApp.address.line2">Line 2</Translate>
            </span>
          </dt>
          <dd>{addressEntity.line2}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="farmerBeApp.address.state">State</Translate>
            </span>
          </dt>
          <dd>{addressEntity.state}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="farmerBeApp.address.country">Country</Translate>
            </span>
          </dt>
          <dd>{addressEntity.country}</dd>
          <dt>
            <span id="pincode">
              <Translate contentKey="farmerBeApp.address.pincode">Pincode</Translate>
            </span>
          </dt>
          <dd>{addressEntity.pincode}</dd>
          <dt>
            <span id="lat">
              <Translate contentKey="farmerBeApp.address.lat">Lat</Translate>
            </span>
          </dt>
          <dd>{addressEntity.lat}</dd>
          <dt>
            <span id="lon">
              <Translate contentKey="farmerBeApp.address.lon">Lon</Translate>
            </span>
          </dt>
          <dd>{addressEntity.lon}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="farmerBeApp.address.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{addressEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="farmerBeApp.address.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{addressEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="farmerBeApp.address.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {addressEntity.createdTime ? <TextFormat value={addressEntity.createdTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="farmerBeApp.address.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{addressEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="farmerBeApp.address.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {addressEntity.updatedTime ? <TextFormat value={addressEntity.updatedTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="farmerBeApp.address.farmer">Farmer</Translate>
          </dt>
          <dd>{addressEntity.farmer ? addressEntity.farmer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/address" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/address/${addressEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AddressDetail;
