import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './accessories.reducer';

export const AccessoriesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const accessoriesEntity = useAppSelector(state => state.accessories.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="accessoriesDetailsHeading">
          <Translate contentKey="farmerBeApp.accessories.detail.title">Accessories</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{accessoriesEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="farmerBeApp.accessories.name">Name</Translate>
            </span>
          </dt>
          <dd>{accessoriesEntity.name}</dd>
          <dt>
            <span id="imagePath">
              <Translate contentKey="farmerBeApp.accessories.imagePath">Image Path</Translate>
            </span>
          </dt>
          <dd>{accessoriesEntity.imagePath}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="farmerBeApp.accessories.description">Description</Translate>
            </span>
          </dt>
          <dd>{accessoriesEntity.description}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="farmerBeApp.accessories.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{accessoriesEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="farmerBeApp.accessories.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{accessoriesEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="farmerBeApp.accessories.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {accessoriesEntity.createdTime ? (
              <TextFormat value={accessoriesEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="farmerBeApp.accessories.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{accessoriesEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="farmerBeApp.accessories.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {accessoriesEntity.updatedTime ? (
              <TextFormat value={accessoriesEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="farmerBeApp.accessories.category">Category</Translate>
          </dt>
          <dd>{accessoriesEntity.category ? accessoriesEntity.category.id : ''}</dd>
          <dt>
            <Translate contentKey="farmerBeApp.accessories.farm">Farm</Translate>
          </dt>
          <dd>
            {accessoriesEntity.farms
              ? accessoriesEntity.farms.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {accessoriesEntity.farms && i === accessoriesEntity.farms.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/accessories" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/accessories/${accessoriesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AccessoriesDetail;
