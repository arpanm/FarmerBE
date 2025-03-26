import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getBuyers } from 'app/entities/buyer/buyer.reducer';
import { getEntities as getFarms } from 'app/entities/farm/farm.reducer';
import { createEntity, getEntity, updateEntity } from './field-visit.reducer';

export const FieldVisitUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const buyers = useAppSelector(state => state.buyer.entities);
  const farms = useAppSelector(state => state.farm.entities);
  const fieldVisitEntity = useAppSelector(state => state.fieldVisit.entity);
  const loading = useAppSelector(state => state.fieldVisit.loading);
  const updating = useAppSelector(state => state.fieldVisit.updating);
  const updateSuccess = useAppSelector(state => state.fieldVisit.updateSuccess);

  const handleClose = () => {
    navigate('/field-visit');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getBuyers({}));
    dispatch(getFarms({}));
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
    values.fieldVisitTime = convertDateTimeToServer(values.fieldVisitTime);
    if (values.lat !== undefined && typeof values.lat !== 'number') {
      values.lat = Number(values.lat);
    }
    if (values.lon !== undefined && typeof values.lon !== 'number') {
      values.lon = Number(values.lon);
    }
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...fieldVisitEntity,
      ...values,
      buyer: buyers.find(it => it.id.toString() === values.buyer?.toString()),
      farm: farms.find(it => it.id.toString() === values.farm?.toString()),
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
          fieldVisitTime: displayDefaultDateTime(),
          createdTime: displayDefaultDateTime(),
          updatedTime: displayDefaultDateTime(),
        }
      : {
          ...fieldVisitEntity,
          fieldVisitTime: convertDateTimeFromServer(fieldVisitEntity.fieldVisitTime),
          createdTime: convertDateTimeFromServer(fieldVisitEntity.createdTime),
          updatedTime: convertDateTimeFromServer(fieldVisitEntity.updatedTime),
          buyer: fieldVisitEntity?.buyer?.id,
          farm: fieldVisitEntity?.farm?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="farmerBeApp.fieldVisit.home.createOrEditLabel" data-cy="FieldVisitCreateUpdateHeading">
            <Translate contentKey="farmerBeApp.fieldVisit.home.createOrEditLabel">Create or edit a FieldVisit</Translate>
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
                  id="field-visit-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('farmerBeApp.fieldVisit.fieldVisitDate')}
                id="field-visit-fieldVisitDate"
                name="fieldVisitDate"
                data-cy="fieldVisitDate"
                type="date"
              />
              <ValidatedField
                label={translate('farmerBeApp.fieldVisit.fieldVisitTime')}
                id="field-visit-fieldVisitTime"
                name="fieldVisitTime"
                data-cy="fieldVisitTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label={translate('farmerBeApp.fieldVisit.lat')} id="field-visit-lat" name="lat" data-cy="lat" type="text" />
              <ValidatedField label={translate('farmerBeApp.fieldVisit.lon')} id="field-visit-lon" name="lon" data-cy="lon" type="text" />
              <ValidatedField
                label={translate('farmerBeApp.fieldVisit.isActive')}
                id="field-visit-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('farmerBeApp.fieldVisit.createddBy')}
                id="field-visit-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.fieldVisit.createdTime')}
                id="field-visit-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.fieldVisit.updatedBy')}
                id="field-visit-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.fieldVisit.updatedTime')}
                id="field-visit-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="field-visit-buyer"
                name="buyer"
                data-cy="buyer"
                label={translate('farmerBeApp.fieldVisit.buyer')}
                type="select"
              >
                <option value="" key="0" />
                {buyers
                  ? buyers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="field-visit-farm"
                name="farm"
                data-cy="farm"
                label={translate('farmerBeApp.fieldVisit.farm')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/field-visit" replace color="info">
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

export default FieldVisitUpdate;
