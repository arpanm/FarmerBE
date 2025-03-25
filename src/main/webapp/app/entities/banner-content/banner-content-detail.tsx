import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './banner-content.reducer';

export const BannerContentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bannerContentEntity = useAppSelector(state => state.bannerContent.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bannerContentDetailsHeading">
          <Translate contentKey="farmerBeApp.bannerContent.detail.title">BannerContent</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bannerContentEntity.id}</dd>
          <dt>
            <span id="bannerTag">
              <Translate contentKey="farmerBeApp.bannerContent.bannerTag">Banner Tag</Translate>
            </span>
          </dt>
          <dd>{bannerContentEntity.bannerTag}</dd>
          <dt>
            <span id="logoPath">
              <Translate contentKey="farmerBeApp.bannerContent.logoPath">Logo Path</Translate>
            </span>
          </dt>
          <dd>{bannerContentEntity.logoPath}</dd>
          <dt>
            <span id="imagePath">
              <Translate contentKey="farmerBeApp.bannerContent.imagePath">Image Path</Translate>
            </span>
          </dt>
          <dd>{bannerContentEntity.imagePath}</dd>
          <dt>
            <span id="heading">
              <Translate contentKey="farmerBeApp.bannerContent.heading">Heading</Translate>
            </span>
          </dt>
          <dd>{bannerContentEntity.heading}</dd>
          <dt>
            <span id="subHeading">
              <Translate contentKey="farmerBeApp.bannerContent.subHeading">Sub Heading</Translate>
            </span>
          </dt>
          <dd>{bannerContentEntity.subHeading}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="farmerBeApp.bannerContent.description">Description</Translate>
            </span>
          </dt>
          <dd>{bannerContentEntity.description}</dd>
          <dt>
            <span id="landingLink">
              <Translate contentKey="farmerBeApp.bannerContent.landingLink">Landing Link</Translate>
            </span>
          </dt>
          <dd>{bannerContentEntity.landingLink}</dd>
          <dt>
            <span id="landingUtm">
              <Translate contentKey="farmerBeApp.bannerContent.landingUtm">Landing Utm</Translate>
            </span>
          </dt>
          <dd>{bannerContentEntity.landingUtm}</dd>
          <dt>
            <span id="pixelLink">
              <Translate contentKey="farmerBeApp.bannerContent.pixelLink">Pixel Link</Translate>
            </span>
          </dt>
          <dd>{bannerContentEntity.pixelLink}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="farmerBeApp.bannerContent.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>
            {bannerContentEntity.startTime ? (
              <TextFormat value={bannerContentEntity.startTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="farmerBeApp.bannerContent.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>
            {bannerContentEntity.endTime ? <TextFormat value={bannerContentEntity.endTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="farmerBeApp.bannerContent.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{bannerContentEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="farmerBeApp.bannerContent.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{bannerContentEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="farmerBeApp.bannerContent.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {bannerContentEntity.createdTime ? (
              <TextFormat value={bannerContentEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="farmerBeApp.bannerContent.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{bannerContentEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="farmerBeApp.bannerContent.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {bannerContentEntity.updatedTime ? (
              <TextFormat value={bannerContentEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="farmerBeApp.bannerContent.holdingCarousel">Holding Carousel</Translate>
          </dt>
          <dd>{bannerContentEntity.holdingCarousel ? bannerContentEntity.holdingCarousel.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/banner-content" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/banner-content/${bannerContentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BannerContentDetail;
