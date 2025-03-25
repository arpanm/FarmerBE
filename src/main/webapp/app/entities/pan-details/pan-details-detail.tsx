import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './pan-details.reducer';

export const PanDetailsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const panDetailsEntity = useAppSelector(state => state.panDetails.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="panDetailsDetailsHeading">
          <Translate contentKey="farmerBeApp.panDetails.detail.title">PanDetails</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{panDetailsEntity.id}</dd>
          <dt>
            <span id="pan">
              <Translate contentKey="farmerBeApp.panDetails.pan">Pan</Translate>
            </span>
          </dt>
          <dd>{panDetailsEntity.pan}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="farmerBeApp.panDetails.name">Name</Translate>
            </span>
          </dt>
          <dd>{panDetailsEntity.name}</dd>
          <dt>
            <span id="dateOfBirth">
              <Translate contentKey="farmerBeApp.panDetails.dateOfBirth">Date Of Birth</Translate>
            </span>
          </dt>
          <dd>
            {panDetailsEntity.dateOfBirth ? (
              <TextFormat value={panDetailsEntity.dateOfBirth} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="gender">
              <Translate contentKey="farmerBeApp.panDetails.gender">Gender</Translate>
            </span>
          </dt>
          <dd>{panDetailsEntity.gender}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="farmerBeApp.panDetails.country">Country</Translate>
            </span>
          </dt>
          <dd>{panDetailsEntity.country}</dd>
          <dt>
            <span id="isVerified">
              <Translate contentKey="farmerBeApp.panDetails.isVerified">Is Verified</Translate>
            </span>
          </dt>
          <dd>{panDetailsEntity.isVerified ? 'true' : 'false'}</dd>
          <dt>
            <span id="verificationTime">
              <Translate contentKey="farmerBeApp.panDetails.verificationTime">Verification Time</Translate>
            </span>
          </dt>
          <dd>
            {panDetailsEntity.verificationTime ? (
              <TextFormat value={panDetailsEntity.verificationTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="farmerBeApp.panDetails.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{panDetailsEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="farmerBeApp.panDetails.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{panDetailsEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="farmerBeApp.panDetails.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {panDetailsEntity.createdTime ? <TextFormat value={panDetailsEntity.createdTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="farmerBeApp.panDetails.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{panDetailsEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="farmerBeApp.panDetails.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {panDetailsEntity.updatedTime ? <TextFormat value={panDetailsEntity.updatedTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="farmerBeApp.panDetails.farmer">Farmer</Translate>
          </dt>
          <dd>{panDetailsEntity.farmer ? panDetailsEntity.farmer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/pan-details" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pan-details/${panDetailsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PanDetailsDetail;
