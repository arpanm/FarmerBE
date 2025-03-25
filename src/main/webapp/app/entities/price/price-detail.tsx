import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './price.reducer';

export const PriceDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const priceEntity = useAppSelector(state => state.price.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="priceDetailsHeading">
          <Translate contentKey="farmerBeApp.price.detail.title">Price</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{priceEntity.id}</dd>
          <dt>
            <span id="priceDate">
              <Translate contentKey="farmerBeApp.price.priceDate">Price Date</Translate>
            </span>
          </dt>
          <dd>{priceEntity.priceDate ? <TextFormat value={priceEntity.priceDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="priceValue">
              <Translate contentKey="farmerBeApp.price.priceValue">Price Value</Translate>
            </span>
          </dt>
          <dd>{priceEntity.priceValue}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="farmerBeApp.price.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{priceEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="farmerBeApp.price.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{priceEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="farmerBeApp.price.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>{priceEntity.createdTime ? <TextFormat value={priceEntity.createdTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="farmerBeApp.price.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{priceEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="farmerBeApp.price.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>{priceEntity.updatedTime ? <TextFormat value={priceEntity.updatedTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="farmerBeApp.price.crop">Crop</Translate>
          </dt>
          <dd>{priceEntity.crop ? priceEntity.crop.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/price" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/price/${priceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PriceDetail;
