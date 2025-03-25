import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './hervest-plan-rule.reducer';

export const HervestPlanRuleDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const hervestPlanRuleEntity = useAppSelector(state => state.hervestPlanRule.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="hervestPlanRuleDetailsHeading">
          <Translate contentKey="farmerBeApp.hervestPlanRule.detail.title">HervestPlanRule</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{hervestPlanRuleEntity.id}</dd>
          <dt>
            <span id="frequencyType">
              <Translate contentKey="farmerBeApp.hervestPlanRule.frequencyType">Frequency Type</Translate>
            </span>
          </dt>
          <dd>{hervestPlanRuleEntity.frequencyType}</dd>
          <dt>
            <span id="hervestPlanValue">
              <Translate contentKey="farmerBeApp.hervestPlanRule.hervestPlanValue">Hervest Plan Value</Translate>
            </span>
          </dt>
          <dd>{hervestPlanRuleEntity.hervestPlanValue}</dd>
          <dt>
            <span id="hervestPlanValueMin">
              <Translate contentKey="farmerBeApp.hervestPlanRule.hervestPlanValueMin">Hervest Plan Value Min</Translate>
            </span>
          </dt>
          <dd>{hervestPlanRuleEntity.hervestPlanValueMin}</dd>
          <dt>
            <span id="hervestPlanValueMax">
              <Translate contentKey="farmerBeApp.hervestPlanRule.hervestPlanValueMax">Hervest Plan Value Max</Translate>
            </span>
          </dt>
          <dd>{hervestPlanRuleEntity.hervestPlanValueMax}</dd>
          <dt>
            <span id="daysOfWeek">
              <Translate contentKey="farmerBeApp.hervestPlanRule.daysOfWeek">Days Of Week</Translate>
            </span>
          </dt>
          <dd>{hervestPlanRuleEntity.daysOfWeek}</dd>
          <dt>
            <span id="daysOfMonth">
              <Translate contentKey="farmerBeApp.hervestPlanRule.daysOfMonth">Days Of Month</Translate>
            </span>
          </dt>
          <dd>{hervestPlanRuleEntity.daysOfMonth}</dd>
          <dt>
            <span id="daysOfYear">
              <Translate contentKey="farmerBeApp.hervestPlanRule.daysOfYear">Days Of Year</Translate>
            </span>
          </dt>
          <dd>{hervestPlanRuleEntity.daysOfYear}</dd>
          <dt>
            <span id="alternateXdays">
              <Translate contentKey="farmerBeApp.hervestPlanRule.alternateXdays">Alternate Xdays</Translate>
            </span>
          </dt>
          <dd>{hervestPlanRuleEntity.alternateXdays}</dd>
          <dt>
            <span id="startDate">
              <Translate contentKey="farmerBeApp.hervestPlanRule.startDate">Start Date</Translate>
            </span>
          </dt>
          <dd>
            {hervestPlanRuleEntity.startDate ? (
              <TextFormat value={hervestPlanRuleEntity.startDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="hasEndDate">
              <Translate contentKey="farmerBeApp.hervestPlanRule.hasEndDate">Has End Date</Translate>
            </span>
          </dt>
          <dd>{hervestPlanRuleEntity.hasEndDate ? 'true' : 'false'}</dd>
          <dt>
            <span id="endDate">
              <Translate contentKey="farmerBeApp.hervestPlanRule.endDate">End Date</Translate>
            </span>
          </dt>
          <dd>
            {hervestPlanRuleEntity.endDate ? (
              <TextFormat value={hervestPlanRuleEntity.endDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="farmerBeApp.hervestPlanRule.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{hervestPlanRuleEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="farmerBeApp.hervestPlanRule.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{hervestPlanRuleEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="farmerBeApp.hervestPlanRule.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {hervestPlanRuleEntity.createdTime ? (
              <TextFormat value={hervestPlanRuleEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="farmerBeApp.hervestPlanRule.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{hervestPlanRuleEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="farmerBeApp.hervestPlanRule.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {hervestPlanRuleEntity.updatedTime ? (
              <TextFormat value={hervestPlanRuleEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="farmerBeApp.hervestPlanRule.farm">Farm</Translate>
          </dt>
          <dd>{hervestPlanRuleEntity.farm ? hervestPlanRuleEntity.farm.id : ''}</dd>
          <dt>
            <Translate contentKey="farmerBeApp.hervestPlanRule.crop">Crop</Translate>
          </dt>
          <dd>{hervestPlanRuleEntity.crop ? hervestPlanRuleEntity.crop.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/hervest-plan-rule" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/hervest-plan-rule/${hervestPlanRuleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default HervestPlanRuleDetail;
