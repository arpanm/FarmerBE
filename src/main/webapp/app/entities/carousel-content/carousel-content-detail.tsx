import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './carousel-content.reducer';

export const CarouselContentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const carouselContentEntity = useAppSelector(state => state.carouselContent.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="carouselContentDetailsHeading">
          <Translate contentKey="farmerBeApp.carouselContent.detail.title">CarouselContent</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{carouselContentEntity.id}</dd>
          <dt>
            <span id="carouselTag">
              <Translate contentKey="farmerBeApp.carouselContent.carouselTag">Carousel Tag</Translate>
            </span>
          </dt>
          <dd>{carouselContentEntity.carouselTag}</dd>
          <dt>
            <span id="showViewMore">
              <Translate contentKey="farmerBeApp.carouselContent.showViewMore">Show View More</Translate>
            </span>
          </dt>
          <dd>{carouselContentEntity.showViewMore ? 'true' : 'false'}</dd>
          <dt>
            <span id="viewMoreLink">
              <Translate contentKey="farmerBeApp.carouselContent.viewMoreLink">View More Link</Translate>
            </span>
          </dt>
          <dd>{carouselContentEntity.viewMoreLink}</dd>
          <dt>
            <span id="viewMoreUtm">
              <Translate contentKey="farmerBeApp.carouselContent.viewMoreUtm">View More Utm</Translate>
            </span>
          </dt>
          <dd>{carouselContentEntity.viewMoreUtm}</dd>
          <dt>
            <span id="pixelLink">
              <Translate contentKey="farmerBeApp.carouselContent.pixelLink">Pixel Link</Translate>
            </span>
          </dt>
          <dd>{carouselContentEntity.pixelLink}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="farmerBeApp.carouselContent.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{carouselContentEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="farmerBeApp.carouselContent.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{carouselContentEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="farmerBeApp.carouselContent.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {carouselContentEntity.createdTime ? (
              <TextFormat value={carouselContentEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="farmerBeApp.carouselContent.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{carouselContentEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="farmerBeApp.carouselContent.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {carouselContentEntity.updatedTime ? (
              <TextFormat value={carouselContentEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/carousel-content" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/carousel-content/${carouselContentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CarouselContentDetail;
