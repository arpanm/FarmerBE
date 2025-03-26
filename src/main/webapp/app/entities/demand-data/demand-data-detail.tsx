import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './demand-data.reducer';

export const DemandDataDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const demandDataEntity = useAppSelector(state => state.demandData.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="demandDataDetailsHeading">
          <Translate contentKey="farmerBeApp.demandData.detail.title">DemandData</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{demandDataEntity.id}</dd>
          <dt>
            <span id="fromCPC">
              <Translate contentKey="farmerBeApp.demandData.fromCPC">From CPC</Translate>
            </span>
          </dt>
          <dd>{demandDataEntity.fromCPC}</dd>
          <dt>
            <span id="toCC">
              <Translate contentKey="farmerBeApp.demandData.toCC">To CC</Translate>
            </span>
          </dt>
          <dd>{demandDataEntity.toCC}</dd>
          <dt>
            <span id="pCode">
              <Translate contentKey="farmerBeApp.demandData.pCode">P Code</Translate>
            </span>
          </dt>
          <dd>{demandDataEntity.pCode}</dd>
          <dt>
            <span id="article">
              <Translate contentKey="farmerBeApp.demandData.article">Article</Translate>
            </span>
          </dt>
          <dd>{demandDataEntity.article}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="farmerBeApp.demandData.description">Description</Translate>
            </span>
          </dt>
          <dd>{demandDataEntity.description}</dd>
          <dt>
            <span id="uom">
              <Translate contentKey="farmerBeApp.demandData.uom">Uom</Translate>
            </span>
          </dt>
          <dd>{demandDataEntity.uom}</dd>
          <dt>
            <span id="netWeightGrams">
              <Translate contentKey="farmerBeApp.demandData.netWeightGrams">Net Weight Grams</Translate>
            </span>
          </dt>
          <dd>{demandDataEntity.netWeightGrams}</dd>
          <dt>
            <span id="crateSize">
              <Translate contentKey="farmerBeApp.demandData.crateSize">Crate Size</Translate>
            </span>
          </dt>
          <dd>{demandDataEntity.crateSize}</dd>
          <dt>
            <span id="indentUom">
              <Translate contentKey="farmerBeApp.demandData.indentUom">Indent Uom</Translate>
            </span>
          </dt>
          <dd>{demandDataEntity.indentUom}</dd>
          <dt>
            <span id="indentKg">
              <Translate contentKey="farmerBeApp.demandData.indentKg">Indent Kg</Translate>
            </span>
          </dt>
          <dd>{demandDataEntity.indentKg}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="farmerBeApp.demandData.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{demandDataEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="farmerBeApp.demandData.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{demandDataEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="farmerBeApp.demandData.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {demandDataEntity.createdTime ? <TextFormat value={demandDataEntity.createdTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="farmerBeApp.demandData.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{demandDataEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="farmerBeApp.demandData.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {demandDataEntity.updatedTime ? <TextFormat value={demandDataEntity.updatedTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="farmerBeApp.demandData.crop">Crop</Translate>
          </dt>
          <dd>{demandDataEntity.crop ? demandDataEntity.crop.id : ''}</dd>
          <dt>
            <Translate contentKey="farmerBeApp.demandData.collectionCenter">Collection Center</Translate>
          </dt>
          <dd>{demandDataEntity.collectionCenter ? demandDataEntity.collectionCenter.id : ''}</dd>
          <dt>
            <Translate contentKey="farmerBeApp.demandData.file">File</Translate>
          </dt>
          <dd>{demandDataEntity.file ? demandDataEntity.file.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/demand-data" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/demand-data/${demandDataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DemandDataDetail;
