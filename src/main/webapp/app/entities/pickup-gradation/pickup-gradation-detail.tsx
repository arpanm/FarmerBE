import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './pickup-gradation.reducer';

export const PickupGradationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const pickupGradationEntity = useAppSelector(state => state.pickupGradation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pickupGradationDetailsHeading">
          <Translate contentKey="farmerBeApp.pickupGradation.detail.title">PickupGradation</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{pickupGradationEntity.id}</dd>
          <dt>
            <span id="itemGrade">
              <Translate contentKey="farmerBeApp.pickupGradation.itemGrade">Item Grade</Translate>
            </span>
          </dt>
          <dd>{pickupGradationEntity.itemGrade}</dd>
          <dt>
            <span id="gradedBy">
              <Translate contentKey="farmerBeApp.pickupGradation.gradedBy">Graded By</Translate>
            </span>
          </dt>
          <dd>{pickupGradationEntity.gradedBy}</dd>
          <dt>
            <span id="gradedTime">
              <Translate contentKey="farmerBeApp.pickupGradation.gradedTime">Graded Time</Translate>
            </span>
          </dt>
          <dd>
            {pickupGradationEntity.gradedTime ? (
              <TextFormat value={pickupGradationEntity.gradedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="farmerBeApp.pickupGradation.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{pickupGradationEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="farmerBeApp.pickupGradation.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{pickupGradationEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="farmerBeApp.pickupGradation.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {pickupGradationEntity.createdTime ? (
              <TextFormat value={pickupGradationEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="farmerBeApp.pickupGradation.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{pickupGradationEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="farmerBeApp.pickupGradation.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {pickupGradationEntity.updatedTime ? (
              <TextFormat value={pickupGradationEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/pickup-gradation" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pickup-gradation/${pickupGradationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PickupGradationDetail;
