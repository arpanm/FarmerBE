import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './document.reducer';

export const DocumentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const documentEntity = useAppSelector(state => state.document.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="documentDetailsHeading">
          <Translate contentKey="farmerBeApp.document.detail.title">Document</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{documentEntity.id}</dd>
          <dt>
            <span id="docPath">
              <Translate contentKey="farmerBeApp.document.docPath">Doc Path</Translate>
            </span>
          </dt>
          <dd>{documentEntity.docPath}</dd>
          <dt>
            <span id="docType">
              <Translate contentKey="farmerBeApp.document.docType">Doc Type</Translate>
            </span>
          </dt>
          <dd>{documentEntity.docType}</dd>
          <dt>
            <span id="docFormat">
              <Translate contentKey="farmerBeApp.document.docFormat">Doc Format</Translate>
            </span>
          </dt>
          <dd>{documentEntity.docFormat}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="farmerBeApp.document.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{documentEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="farmerBeApp.document.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{documentEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="farmerBeApp.document.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {documentEntity.createdTime ? <TextFormat value={documentEntity.createdTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="farmerBeApp.document.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{documentEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="farmerBeApp.document.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {documentEntity.updatedTime ? <TextFormat value={documentEntity.updatedTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="farmerBeApp.document.farmer">Farmer</Translate>
          </dt>
          <dd>{documentEntity.farmer ? documentEntity.farmer.id : ''}</dd>
          <dt>
            <Translate contentKey="farmerBeApp.document.farm">Farm</Translate>
          </dt>
          <dd>{documentEntity.farm ? documentEntity.farm.id : ''}</dd>
          <dt>
            <Translate contentKey="farmerBeApp.document.address">Address</Translate>
          </dt>
          <dd>{documentEntity.address ? documentEntity.address.id : ''}</dd>
          <dt>
            <Translate contentKey="farmerBeApp.document.panDetails">Pan Details</Translate>
          </dt>
          <dd>{documentEntity.panDetails ? documentEntity.panDetails.id : ''}</dd>
          <dt>
            <Translate contentKey="farmerBeApp.document.bankDetails">Bank Details</Translate>
          </dt>
          <dd>{documentEntity.bankDetails ? documentEntity.bankDetails.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/document" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/document/${documentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DocumentDetail;
