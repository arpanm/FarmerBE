import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bank-details.reducer';

export const BankDetailsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bankDetailsEntity = useAppSelector(state => state.bankDetails.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bankDetailsDetailsHeading">
          <Translate contentKey="farmerBeApp.bankDetails.detail.title">BankDetails</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bankDetailsEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="farmerBeApp.bankDetails.name">Name</Translate>
            </span>
          </dt>
          <dd>{bankDetailsEntity.name}</dd>
          <dt>
            <span id="accountNumber">
              <Translate contentKey="farmerBeApp.bankDetails.accountNumber">Account Number</Translate>
            </span>
          </dt>
          <dd>{bankDetailsEntity.accountNumber}</dd>
          <dt>
            <span id="ifsC">
              <Translate contentKey="farmerBeApp.bankDetails.ifsC">Ifs C</Translate>
            </span>
          </dt>
          <dd>{bankDetailsEntity.ifsC}</dd>
          <dt>
            <span id="bankName">
              <Translate contentKey="farmerBeApp.bankDetails.bankName">Bank Name</Translate>
            </span>
          </dt>
          <dd>{bankDetailsEntity.bankName}</dd>
          <dt>
            <span id="branchName">
              <Translate contentKey="farmerBeApp.bankDetails.branchName">Branch Name</Translate>
            </span>
          </dt>
          <dd>{bankDetailsEntity.branchName}</dd>
          <dt>
            <span id="isVerified">
              <Translate contentKey="farmerBeApp.bankDetails.isVerified">Is Verified</Translate>
            </span>
          </dt>
          <dd>{bankDetailsEntity.isVerified ? 'true' : 'false'}</dd>
          <dt>
            <span id="verificationTime">
              <Translate contentKey="farmerBeApp.bankDetails.verificationTime">Verification Time</Translate>
            </span>
          </dt>
          <dd>
            {bankDetailsEntity.verificationTime ? (
              <TextFormat value={bankDetailsEntity.verificationTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="farmerBeApp.bankDetails.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{bankDetailsEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="farmerBeApp.bankDetails.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{bankDetailsEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="farmerBeApp.bankDetails.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {bankDetailsEntity.createdTime ? (
              <TextFormat value={bankDetailsEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="farmerBeApp.bankDetails.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{bankDetailsEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="farmerBeApp.bankDetails.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {bankDetailsEntity.updatedTime ? (
              <TextFormat value={bankDetailsEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="farmerBeApp.bankDetails.farmer">Farmer</Translate>
          </dt>
          <dd>{bankDetailsEntity.farmer ? bankDetailsEntity.farmer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/bank-details" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bank-details/${bankDetailsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BankDetailsDetail;
