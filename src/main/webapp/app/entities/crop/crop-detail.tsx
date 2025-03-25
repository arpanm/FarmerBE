import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './crop.reducer';

export const CropDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cropEntity = useAppSelector(state => state.crop.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cropDetailsHeading">
          <Translate contentKey="farmerBeApp.crop.detail.title">Crop</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{cropEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="farmerBeApp.crop.name">Name</Translate>
            </span>
          </dt>
          <dd>{cropEntity.name}</dd>
          <dt>
            <span id="imagePath">
              <Translate contentKey="farmerBeApp.crop.imagePath">Image Path</Translate>
            </span>
          </dt>
          <dd>{cropEntity.imagePath}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="farmerBeApp.crop.description">Description</Translate>
            </span>
          </dt>
          <dd>{cropEntity.description}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="farmerBeApp.crop.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{cropEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="farmerBeApp.crop.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{cropEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="farmerBeApp.crop.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>{cropEntity.createdTime ? <TextFormat value={cropEntity.createdTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="farmerBeApp.crop.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{cropEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="farmerBeApp.crop.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>{cropEntity.updatedTime ? <TextFormat value={cropEntity.updatedTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="farmerBeApp.crop.category">Category</Translate>
          </dt>
          <dd>{cropEntity.category ? cropEntity.category.id : ''}</dd>
          <dt>
            <Translate contentKey="farmerBeApp.crop.farm">Farm</Translate>
          </dt>
          <dd>
            {cropEntity.farms
              ? cropEntity.farms.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {cropEntity.farms && i === cropEntity.farms.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/crop" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/crop/${cropEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CropDetail;
