import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './terms-and-condition.reducer';

export const TermsAndConditionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const termsAndConditionEntity = useAppSelector(state => state.termsAndCondition.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="termsAndConditionDetailsHeading">
          <Translate contentKey="farmerBeApp.termsAndCondition.detail.title">TermsAndCondition</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{termsAndConditionEntity.id}</dd>
          <dt>
            <span id="termsLink">
              <Translate contentKey="farmerBeApp.termsAndCondition.termsLink">Terms Link</Translate>
            </span>
          </dt>
          <dd>{termsAndConditionEntity.termsLink}</dd>
          <dt>
            <span id="aggreedOn">
              <Translate contentKey="farmerBeApp.termsAndCondition.aggreedOn">Aggreed On</Translate>
            </span>
          </dt>
          <dd>
            {termsAndConditionEntity.aggreedOn ? (
              <TextFormat value={termsAndConditionEntity.aggreedOn} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="farmerBeApp.termsAndCondition.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{termsAndConditionEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="farmerBeApp.termsAndCondition.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{termsAndConditionEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="farmerBeApp.termsAndCondition.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {termsAndConditionEntity.createdTime ? (
              <TextFormat value={termsAndConditionEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="farmerBeApp.termsAndCondition.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{termsAndConditionEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="farmerBeApp.termsAndCondition.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {termsAndConditionEntity.updatedTime ? (
              <TextFormat value={termsAndConditionEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="farmerBeApp.termsAndCondition.farmer">Farmer</Translate>
          </dt>
          <dd>{termsAndConditionEntity.farmer ? termsAndConditionEntity.farmer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/terms-and-condition" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/terms-and-condition/${termsAndConditionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TermsAndConditionDetail;
