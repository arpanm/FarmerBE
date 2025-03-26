import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './buyer.reducer';

export const BuyerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const buyerEntity = useAppSelector(state => state.buyer.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="buyerDetailsHeading">
          <Translate contentKey="farmerBeApp.buyer.detail.title">Buyer</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{buyerEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="farmerBeApp.buyer.name">Name</Translate>
            </span>
          </dt>
          <dd>{buyerEntity.name}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="farmerBeApp.buyer.email">Email</Translate>
            </span>
          </dt>
          <dd>{buyerEntity.email}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="farmerBeApp.buyer.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{buyerEntity.phone}</dd>
          <dt>
            <span id="isWhatsAppEnabled">
              <Translate contentKey="farmerBeApp.buyer.isWhatsAppEnabled">Is Whats App Enabled</Translate>
            </span>
          </dt>
          <dd>{buyerEntity.isWhatsAppEnabled ? 'true' : 'false'}</dd>
          <dt>
            <span id="preferedLanguage">
              <Translate contentKey="farmerBeApp.buyer.preferedLanguage">Prefered Language</Translate>
            </span>
          </dt>
          <dd>{buyerEntity.preferedLanguage}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="farmerBeApp.buyer.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{buyerEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="farmerBeApp.buyer.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{buyerEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="farmerBeApp.buyer.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>{buyerEntity.createdTime ? <TextFormat value={buyerEntity.createdTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="farmerBeApp.buyer.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{buyerEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="farmerBeApp.buyer.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>{buyerEntity.updatedTime ? <TextFormat value={buyerEntity.updatedTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="farmerBeApp.buyer.collectionCenter">Collection Center</Translate>
          </dt>
          <dd>{buyerEntity.collectionCenter ? buyerEntity.collectionCenter.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/buyer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/buyer/${buyerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BuyerDetail;
