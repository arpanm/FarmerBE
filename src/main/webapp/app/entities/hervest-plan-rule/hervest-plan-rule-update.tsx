import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getFarms } from 'app/entities/farm/farm.reducer';
import { getEntities as getCrops } from 'app/entities/crop/crop.reducer';
import { FrequencyType } from 'app/shared/model/enumerations/frequency-type.model';
import { createEntity, getEntity, updateEntity } from './hervest-plan-rule.reducer';

export const HervestPlanRuleUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const farms = useAppSelector(state => state.farm.entities);
  const crops = useAppSelector(state => state.crop.entities);
  const hervestPlanRuleEntity = useAppSelector(state => state.hervestPlanRule.entity);
  const loading = useAppSelector(state => state.hervestPlanRule.loading);
  const updating = useAppSelector(state => state.hervestPlanRule.updating);
  const updateSuccess = useAppSelector(state => state.hervestPlanRule.updateSuccess);
  const frequencyTypeValues = Object.keys(FrequencyType);

  const handleClose = () => {
    navigate('/hervest-plan-rule');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getFarms({}));
    dispatch(getCrops({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.hervestPlanValue !== undefined && typeof values.hervestPlanValue !== 'number') {
      values.hervestPlanValue = Number(values.hervestPlanValue);
    }
    if (values.hervestPlanValueMin !== undefined && typeof values.hervestPlanValueMin !== 'number') {
      values.hervestPlanValueMin = Number(values.hervestPlanValueMin);
    }
    if (values.hervestPlanValueMax !== undefined && typeof values.hervestPlanValueMax !== 'number') {
      values.hervestPlanValueMax = Number(values.hervestPlanValueMax);
    }
    if (values.alternateXdays !== undefined && typeof values.alternateXdays !== 'number') {
      values.alternateXdays = Number(values.alternateXdays);
    }
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...hervestPlanRuleEntity,
      ...values,
      farm: farms.find(it => it.id.toString() === values.farm?.toString()),
      crop: crops.find(it => it.id.toString() === values.crop?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          createdTime: displayDefaultDateTime(),
          updatedTime: displayDefaultDateTime(),
        }
      : {
          frequencyType: 'DaysOfWeek',
          ...hervestPlanRuleEntity,
          createdTime: convertDateTimeFromServer(hervestPlanRuleEntity.createdTime),
          updatedTime: convertDateTimeFromServer(hervestPlanRuleEntity.updatedTime),
          farm: hervestPlanRuleEntity?.farm?.id,
          crop: hervestPlanRuleEntity?.crop?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="farmerBeApp.hervestPlanRule.home.createOrEditLabel" data-cy="HervestPlanRuleCreateUpdateHeading">
            <Translate contentKey="farmerBeApp.hervestPlanRule.home.createOrEditLabel">Create or edit a HervestPlanRule</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="hervest-plan-rule-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('farmerBeApp.hervestPlanRule.frequencyType')}
                id="hervest-plan-rule-frequencyType"
                name="frequencyType"
                data-cy="frequencyType"
                type="select"
              >
                {frequencyTypeValues.map(frequencyType => (
                  <option value={frequencyType} key={frequencyType}>
                    {translate(`farmerBeApp.FrequencyType.${frequencyType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('farmerBeApp.hervestPlanRule.hervestPlanValue')}
                id="hervest-plan-rule-hervestPlanValue"
                name="hervestPlanValue"
                data-cy="hervestPlanValue"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.hervestPlanRule.hervestPlanValueMin')}
                id="hervest-plan-rule-hervestPlanValueMin"
                name="hervestPlanValueMin"
                data-cy="hervestPlanValueMin"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.hervestPlanRule.hervestPlanValueMax')}
                id="hervest-plan-rule-hervestPlanValueMax"
                name="hervestPlanValueMax"
                data-cy="hervestPlanValueMax"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.hervestPlanRule.daysOfWeek')}
                id="hervest-plan-rule-daysOfWeek"
                name="daysOfWeek"
                data-cy="daysOfWeek"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.hervestPlanRule.daysOfMonth')}
                id="hervest-plan-rule-daysOfMonth"
                name="daysOfMonth"
                data-cy="daysOfMonth"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.hervestPlanRule.daysOfYear')}
                id="hervest-plan-rule-daysOfYear"
                name="daysOfYear"
                data-cy="daysOfYear"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.hervestPlanRule.alternateXdays')}
                id="hervest-plan-rule-alternateXdays"
                name="alternateXdays"
                data-cy="alternateXdays"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.hervestPlanRule.startDate')}
                id="hervest-plan-rule-startDate"
                name="startDate"
                data-cy="startDate"
                type="date"
              />
              <ValidatedField
                label={translate('farmerBeApp.hervestPlanRule.hasEndDate')}
                id="hervest-plan-rule-hasEndDate"
                name="hasEndDate"
                data-cy="hasEndDate"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('farmerBeApp.hervestPlanRule.endDate')}
                id="hervest-plan-rule-endDate"
                name="endDate"
                data-cy="endDate"
                type="date"
              />
              <ValidatedField
                label={translate('farmerBeApp.hervestPlanRule.isActive')}
                id="hervest-plan-rule-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('farmerBeApp.hervestPlanRule.createddBy')}
                id="hervest-plan-rule-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.hervestPlanRule.createdTime')}
                id="hervest-plan-rule-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.hervestPlanRule.updatedBy')}
                id="hervest-plan-rule-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.hervestPlanRule.updatedTime')}
                id="hervest-plan-rule-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="hervest-plan-rule-farm"
                name="farm"
                data-cy="farm"
                label={translate('farmerBeApp.hervestPlanRule.farm')}
                type="select"
              >
                <option value="" key="0" />
                {farms
                  ? farms.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="hervest-plan-rule-crop"
                name="crop"
                data-cy="crop"
                label={translate('farmerBeApp.hervestPlanRule.crop')}
                type="select"
              >
                <option value="" key="0" />
                {crops
                  ? crops.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/hervest-plan-rule" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default HervestPlanRuleUpdate;
