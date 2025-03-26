import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './location-mapping.reducer';

export const LocationMappingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const locationMappingEntity = useAppSelector(state => state.locationMapping.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="locationMappingDetailsHeading">
          <Translate contentKey="farmerBeApp.locationMapping.detail.title">LocationMapping</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{locationMappingEntity.id}</dd>
          <dt>
            <span id="areaName">
              <Translate contentKey="farmerBeApp.locationMapping.areaName">Area Name</Translate>
            </span>
          </dt>
          <dd>{locationMappingEntity.areaName}</dd>
          <dt>
            <span id="areaType">
              <Translate contentKey="farmerBeApp.locationMapping.areaType">Area Type</Translate>
            </span>
          </dt>
          <dd>{locationMappingEntity.areaType}</dd>
          <dt>
            <span id="pincode">
              <Translate contentKey="farmerBeApp.locationMapping.pincode">Pincode</Translate>
            </span>
          </dt>
          <dd>{locationMappingEntity.pincode}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="farmerBeApp.locationMapping.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{locationMappingEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="farmerBeApp.locationMapping.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{locationMappingEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="farmerBeApp.locationMapping.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {locationMappingEntity.createdTime ? (
              <TextFormat value={locationMappingEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="farmerBeApp.locationMapping.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{locationMappingEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="farmerBeApp.locationMapping.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {locationMappingEntity.updatedTime ? (
              <TextFormat value={locationMappingEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="farmerBeApp.locationMapping.collectionCenter">Collection Center</Translate>
          </dt>
          <dd>{locationMappingEntity.collectionCenter ? locationMappingEntity.collectionCenter.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/location-mapping" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/location-mapping/${locationMappingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LocationMappingDetail;
