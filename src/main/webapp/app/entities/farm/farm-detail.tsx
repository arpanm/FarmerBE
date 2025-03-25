import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './farm.reducer';

export const FarmDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const farmEntity = useAppSelector(state => state.farm.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="farmDetailsHeading">
          <Translate contentKey="farmerBeApp.farm.detail.title">Farm</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{farmEntity.id}</dd>
          <dt>
            <span id="farmType">
              <Translate contentKey="farmerBeApp.farm.farmType">Farm Type</Translate>
            </span>
          </dt>
          <dd>{farmEntity.farmType}</dd>
          <dt>
            <span id="ownerName">
              <Translate contentKey="farmerBeApp.farm.ownerName">Owner Name</Translate>
            </span>
          </dt>
          <dd>{farmEntity.ownerName}</dd>
          <dt>
            <span id="relationshipWithOwner">
              <Translate contentKey="farmerBeApp.farm.relationshipWithOwner">Relationship With Owner</Translate>
            </span>
          </dt>
          <dd>{farmEntity.relationshipWithOwner}</dd>
          <dt>
            <span id="areaInAcres">
              <Translate contentKey="farmerBeApp.farm.areaInAcres">Area In Acres</Translate>
            </span>
          </dt>
          <dd>{farmEntity.areaInAcres}</dd>
          <dt>
            <span id="farmDocumentNo">
              <Translate contentKey="farmerBeApp.farm.farmDocumentNo">Farm Document No</Translate>
            </span>
          </dt>
          <dd>{farmEntity.farmDocumentNo}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="farmerBeApp.farm.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{farmEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="farmerBeApp.farm.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{farmEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="farmerBeApp.farm.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>{farmEntity.createdTime ? <TextFormat value={farmEntity.createdTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="farmerBeApp.farm.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{farmEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="farmerBeApp.farm.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>{farmEntity.updatedTime ? <TextFormat value={farmEntity.updatedTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="farmerBeApp.farm.accessories">Accessories</Translate>
          </dt>
          <dd>
            {farmEntity.accessories
              ? farmEntity.accessories.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {farmEntity.accessories && i === farmEntity.accessories.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="farmerBeApp.farm.crop">Crop</Translate>
          </dt>
          <dd>
            {farmEntity.crops
              ? farmEntity.crops.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {farmEntity.crops && i === farmEntity.crops.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="farmerBeApp.farm.farmer">Farmer</Translate>
          </dt>
          <dd>{farmEntity.farmer ? farmEntity.farmer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/farm" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/farm/${farmEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FarmDetail;
