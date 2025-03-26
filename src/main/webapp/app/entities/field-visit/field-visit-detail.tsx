import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './field-visit.reducer';

export const FieldVisitDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const fieldVisitEntity = useAppSelector(state => state.fieldVisit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fieldVisitDetailsHeading">
          <Translate contentKey="farmerBeApp.fieldVisit.detail.title">FieldVisit</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{fieldVisitEntity.id}</dd>
          <dt>
            <span id="fieldVisitDate">
              <Translate contentKey="farmerBeApp.fieldVisit.fieldVisitDate">Field Visit Date</Translate>
            </span>
          </dt>
          <dd>
            {fieldVisitEntity.fieldVisitDate ? (
              <TextFormat value={fieldVisitEntity.fieldVisitDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fieldVisitTime">
              <Translate contentKey="farmerBeApp.fieldVisit.fieldVisitTime">Field Visit Time</Translate>
            </span>
          </dt>
          <dd>
            {fieldVisitEntity.fieldVisitTime ? (
              <TextFormat value={fieldVisitEntity.fieldVisitTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="lat">
              <Translate contentKey="farmerBeApp.fieldVisit.lat">Lat</Translate>
            </span>
          </dt>
          <dd>{fieldVisitEntity.lat}</dd>
          <dt>
            <span id="lon">
              <Translate contentKey="farmerBeApp.fieldVisit.lon">Lon</Translate>
            </span>
          </dt>
          <dd>{fieldVisitEntity.lon}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="farmerBeApp.fieldVisit.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{fieldVisitEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="farmerBeApp.fieldVisit.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{fieldVisitEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="farmerBeApp.fieldVisit.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {fieldVisitEntity.createdTime ? <TextFormat value={fieldVisitEntity.createdTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="farmerBeApp.fieldVisit.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{fieldVisitEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="farmerBeApp.fieldVisit.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {fieldVisitEntity.updatedTime ? <TextFormat value={fieldVisitEntity.updatedTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="farmerBeApp.fieldVisit.buyer">Buyer</Translate>
          </dt>
          <dd>{fieldVisitEntity.buyer ? fieldVisitEntity.buyer.id : ''}</dd>
          <dt>
            <Translate contentKey="farmerBeApp.fieldVisit.farm">Farm</Translate>
          </dt>
          <dd>{fieldVisitEntity.farm ? fieldVisitEntity.farm.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/field-visit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/field-visit/${fieldVisitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FieldVisitDetail;
