import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './farmer.reducer';

export const FarmerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const farmerEntity = useAppSelector(state => state.farmer.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="farmerDetailsHeading">
          <Translate contentKey="farmerBeApp.farmer.detail.title">Farmer</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{farmerEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="farmerBeApp.farmer.name">Name</Translate>
            </span>
          </dt>
          <dd>{farmerEntity.name}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="farmerBeApp.farmer.email">Email</Translate>
            </span>
          </dt>
          <dd>{farmerEntity.email}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="farmerBeApp.farmer.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{farmerEntity.phone}</dd>
          <dt>
            <span id="isWhatsAppEnabled">
              <Translate contentKey="farmerBeApp.farmer.isWhatsAppEnabled">Is Whats App Enabled</Translate>
            </span>
          </dt>
          <dd>{farmerEntity.isWhatsAppEnabled ? 'true' : 'false'}</dd>
          <dt>
            <span id="preferedLanguage">
              <Translate contentKey="farmerBeApp.farmer.preferedLanguage">Prefered Language</Translate>
            </span>
          </dt>
          <dd>{farmerEntity.preferedLanguage}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="farmerBeApp.farmer.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{farmerEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="farmerBeApp.farmer.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{farmerEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="farmerBeApp.farmer.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>{farmerEntity.createdTime ? <TextFormat value={farmerEntity.createdTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="farmerBeApp.farmer.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{farmerEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="farmerBeApp.farmer.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>{farmerEntity.updatedTime ? <TextFormat value={farmerEntity.updatedTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/farmer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/farmer/${farmerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FarmerDetail;
