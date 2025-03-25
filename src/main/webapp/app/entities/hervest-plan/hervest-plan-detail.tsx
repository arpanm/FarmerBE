import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './hervest-plan.reducer';

export const HervestPlanDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const hervestPlanEntity = useAppSelector(state => state.hervestPlan.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="hervestPlanDetailsHeading">
          <Translate contentKey="farmerBeApp.hervestPlan.detail.title">HervestPlan</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{hervestPlanEntity.id}</dd>
          <dt>
            <span id="hervestPlanDate">
              <Translate contentKey="farmerBeApp.hervestPlan.hervestPlanDate">Hervest Plan Date</Translate>
            </span>
          </dt>
          <dd>
            {hervestPlanEntity.hervestPlanDate ? (
              <TextFormat value={hervestPlanEntity.hervestPlanDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="hervestPlanValue">
              <Translate contentKey="farmerBeApp.hervestPlan.hervestPlanValue">Hervest Plan Value</Translate>
            </span>
          </dt>
          <dd>{hervestPlanEntity.hervestPlanValue}</dd>
          <dt>
            <span id="hervestPlanValueMin">
              <Translate contentKey="farmerBeApp.hervestPlan.hervestPlanValueMin">Hervest Plan Value Min</Translate>
            </span>
          </dt>
          <dd>{hervestPlanEntity.hervestPlanValueMin}</dd>
          <dt>
            <span id="hervestPlanValueMax">
              <Translate contentKey="farmerBeApp.hervestPlan.hervestPlanValueMax">Hervest Plan Value Max</Translate>
            </span>
          </dt>
          <dd>{hervestPlanEntity.hervestPlanValueMax}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="farmerBeApp.hervestPlan.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{hervestPlanEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="farmerBeApp.hervestPlan.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{hervestPlanEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="farmerBeApp.hervestPlan.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {hervestPlanEntity.createdTime ? (
              <TextFormat value={hervestPlanEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="farmerBeApp.hervestPlan.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{hervestPlanEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="farmerBeApp.hervestPlan.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {hervestPlanEntity.updatedTime ? (
              <TextFormat value={hervestPlanEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="farmerBeApp.hervestPlan.farm">Farm</Translate>
          </dt>
          <dd>{hervestPlanEntity.farm ? hervestPlanEntity.farm.id : ''}</dd>
          <dt>
            <Translate contentKey="farmerBeApp.hervestPlan.crop">Crop</Translate>
          </dt>
          <dd>{hervestPlanEntity.crop ? hervestPlanEntity.crop.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/hervest-plan" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/hervest-plan/${hervestPlanEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default HervestPlanDetail;
