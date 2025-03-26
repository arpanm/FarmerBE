import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './collection-center.reducer';

export const CollectionCenterDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const collectionCenterEntity = useAppSelector(state => state.collectionCenter.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="collectionCenterDetailsHeading">
          <Translate contentKey="farmerBeApp.collectionCenter.detail.title">CollectionCenter</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{collectionCenterEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="farmerBeApp.collectionCenter.name">Name</Translate>
            </span>
          </dt>
          <dd>{collectionCenterEntity.name}</dd>
          <dt>
            <span id="ccId">
              <Translate contentKey="farmerBeApp.collectionCenter.ccId">Cc Id</Translate>
            </span>
          </dt>
          <dd>{collectionCenterEntity.ccId}</dd>
          <dt>
            <span id="ffdcCode">
              <Translate contentKey="farmerBeApp.collectionCenter.ffdcCode">Ffdc Code</Translate>
            </span>
          </dt>
          <dd>{collectionCenterEntity.ffdcCode}</dd>
          <dt>
            <span id="ffdcName">
              <Translate contentKey="farmerBeApp.collectionCenter.ffdcName">Ffdc Name</Translate>
            </span>
          </dt>
          <dd>{collectionCenterEntity.ffdcName}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="farmerBeApp.collectionCenter.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{collectionCenterEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="farmerBeApp.collectionCenter.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{collectionCenterEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="farmerBeApp.collectionCenter.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {collectionCenterEntity.createdTime ? (
              <TextFormat value={collectionCenterEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="farmerBeApp.collectionCenter.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{collectionCenterEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="farmerBeApp.collectionCenter.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {collectionCenterEntity.updatedTime ? (
              <TextFormat value={collectionCenterEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="farmerBeApp.collectionCenter.crop">Crop</Translate>
          </dt>
          <dd>
            {collectionCenterEntity.crops
              ? collectionCenterEntity.crops.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {collectionCenterEntity.crops && i === collectionCenterEntity.crops.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/collection-center" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/collection-center/${collectionCenterEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CollectionCenterDetail;
