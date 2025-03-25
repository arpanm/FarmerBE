import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getFarms } from 'app/entities/farm/farm.reducer';
import { getEntities as getCrops } from 'app/entities/crop/crop.reducer';
import { createEntity, getEntity, updateEntity } from './hervest-plan.reducer';

export const HervestPlanUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const farms = useAppSelector(state => state.farm.entities);
  const crops = useAppSelector(state => state.crop.entities);
  const hervestPlanEntity = useAppSelector(state => state.hervestPlan.entity);
  const loading = useAppSelector(state => state.hervestPlan.loading);
  const updating = useAppSelector(state => state.hervestPlan.updating);
  const updateSuccess = useAppSelector(state => state.hervestPlan.updateSuccess);

  const handleClose = () => {
    navigate('/hervest-plan');
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
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...hervestPlanEntity,
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
          ...hervestPlanEntity,
          createdTime: convertDateTimeFromServer(hervestPlanEntity.createdTime),
          updatedTime: convertDateTimeFromServer(hervestPlanEntity.updatedTime),
          farm: hervestPlanEntity?.farm?.id,
          crop: hervestPlanEntity?.crop?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="farmerBeApp.hervestPlan.home.createOrEditLabel" data-cy="HervestPlanCreateUpdateHeading">
            <Translate contentKey="farmerBeApp.hervestPlan.home.createOrEditLabel">Create or edit a HervestPlan</Translate>
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
                  id="hervest-plan-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('farmerBeApp.hervestPlan.hervestPlanDate')}
                id="hervest-plan-hervestPlanDate"
                name="hervestPlanDate"
                data-cy="hervestPlanDate"
                type="date"
              />
              <ValidatedField
                label={translate('farmerBeApp.hervestPlan.hervestPlanValue')}
                id="hervest-plan-hervestPlanValue"
                name="hervestPlanValue"
                data-cy="hervestPlanValue"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.hervestPlan.isActive')}
                id="hervest-plan-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('farmerBeApp.hervestPlan.createddBy')}
                id="hervest-plan-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.hervestPlan.createdTime')}
                id="hervest-plan-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.hervestPlan.updatedBy')}
                id="hervest-plan-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.hervestPlan.updatedTime')}
                id="hervest-plan-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="hervest-plan-farm"
                name="farm"
                data-cy="farm"
                label={translate('farmerBeApp.hervestPlan.farm')}
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
                id="hervest-plan-crop"
                name="crop"
                data-cy="crop"
                label={translate('farmerBeApp.hervestPlan.crop')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/hervest-plan" replace color="info">
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

export default HervestPlanUpdate;
