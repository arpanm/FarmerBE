import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './demand-data-file.reducer';

export const DemandDataFileDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const demandDataFileEntity = useAppSelector(state => state.demandDataFile.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="demandDataFileDetailsHeading">
          <Translate contentKey="farmerBeApp.demandDataFile.detail.title">DemandDataFile</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{demandDataFileEntity.id}</dd>
          <dt>
            <span id="uploadedDate">
              <Translate contentKey="farmerBeApp.demandDataFile.uploadedDate">Uploaded Date</Translate>
            </span>
          </dt>
          <dd>
            {demandDataFileEntity.uploadedDate ? (
              <TextFormat value={demandDataFileEntity.uploadedDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fileName">
              <Translate contentKey="farmerBeApp.demandDataFile.fileName">File Name</Translate>
            </span>
          </dt>
          <dd>{demandDataFileEntity.fileName}</dd>
          <dt>
            <span id="uploadedBy">
              <Translate contentKey="farmerBeApp.demandDataFile.uploadedBy">Uploaded By</Translate>
            </span>
          </dt>
          <dd>{demandDataFileEntity.uploadedBy}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="farmerBeApp.demandDataFile.status">Status</Translate>
            </span>
          </dt>
          <dd>{demandDataFileEntity.status}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="farmerBeApp.demandDataFile.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{demandDataFileEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="farmerBeApp.demandDataFile.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{demandDataFileEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="farmerBeApp.demandDataFile.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {demandDataFileEntity.createdTime ? (
              <TextFormat value={demandDataFileEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="farmerBeApp.demandDataFile.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{demandDataFileEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="farmerBeApp.demandDataFile.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {demandDataFileEntity.updatedTime ? (
              <TextFormat value={demandDataFileEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/demand-data-file" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/demand-data-file/${demandDataFileEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DemandDataFileDetail;
