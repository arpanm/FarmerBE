import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './attendence.reducer';

export const AttendenceDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const attendenceEntity = useAppSelector(state => state.attendence.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="attendenceDetailsHeading">
          <Translate contentKey="farmerBeApp.attendence.detail.title">Attendence</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{attendenceEntity.id}</dd>
          <dt>
            <span id="attendenceType">
              <Translate contentKey="farmerBeApp.attendence.attendenceType">Attendence Type</Translate>
            </span>
          </dt>
          <dd>{attendenceEntity.attendenceType}</dd>
          <dt>
            <span id="attendenceDate">
              <Translate contentKey="farmerBeApp.attendence.attendenceDate">Attendence Date</Translate>
            </span>
          </dt>
          <dd>
            {attendenceEntity.attendenceDate ? (
              <TextFormat value={attendenceEntity.attendenceDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="attendenceTime">
              <Translate contentKey="farmerBeApp.attendence.attendenceTime">Attendence Time</Translate>
            </span>
          </dt>
          <dd>
            {attendenceEntity.attendenceTime ? (
              <TextFormat value={attendenceEntity.attendenceTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="lat">
              <Translate contentKey="farmerBeApp.attendence.lat">Lat</Translate>
            </span>
          </dt>
          <dd>{attendenceEntity.lat}</dd>
          <dt>
            <span id="lon">
              <Translate contentKey="farmerBeApp.attendence.lon">Lon</Translate>
            </span>
          </dt>
          <dd>{attendenceEntity.lon}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="farmerBeApp.attendence.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{attendenceEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="farmerBeApp.attendence.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{attendenceEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="farmerBeApp.attendence.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {attendenceEntity.createdTime ? <TextFormat value={attendenceEntity.createdTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="farmerBeApp.attendence.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{attendenceEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="farmerBeApp.attendence.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {attendenceEntity.updatedTime ? <TextFormat value={attendenceEntity.updatedTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="farmerBeApp.attendence.buyer">Buyer</Translate>
          </dt>
          <dd>{attendenceEntity.buyer ? attendenceEntity.buyer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/attendence" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/attendence/${attendenceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AttendenceDetail;
