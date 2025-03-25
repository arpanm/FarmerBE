import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getFarms } from 'app/entities/farm/farm.reducer';
import { getEntities as getCrops } from 'app/entities/crop/crop.reducer';
import { createEntity, getEntity, updateEntity } from './supply-confirmation.reducer';

export const SupplyConfirmationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const farms = useAppSelector(state => state.farm.entities);
  const crops = useAppSelector(state => state.crop.entities);
  const supplyConfirmationEntity = useAppSelector(state => state.supplyConfirmation.entity);
  const loading = useAppSelector(state => state.supplyConfirmation.loading);
  const updating = useAppSelector(state => state.supplyConfirmation.updating);
  const updateSuccess = useAppSelector(state => state.supplyConfirmation.updateSuccess);

  const handleClose = () => {
    navigate('/supply-confirmation');
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
    if (values.confirmValue !== undefined && typeof values.confirmValue !== 'number') {
      values.confirmValue = Number(values.confirmValue);
    }
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...supplyConfirmationEntity,
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
          ...supplyConfirmationEntity,
          createdTime: convertDateTimeFromServer(supplyConfirmationEntity.createdTime),
          updatedTime: convertDateTimeFromServer(supplyConfirmationEntity.updatedTime),
          farm: supplyConfirmationEntity?.farm?.id,
          crop: supplyConfirmationEntity?.crop?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="farmerBeApp.supplyConfirmation.home.createOrEditLabel" data-cy="SupplyConfirmationCreateUpdateHeading">
            <Translate contentKey="farmerBeApp.supplyConfirmation.home.createOrEditLabel">Create or edit a SupplyConfirmation</Translate>
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
                  id="supply-confirmation-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('farmerBeApp.supplyConfirmation.confirmDate')}
                id="supply-confirmation-confirmDate"
                name="confirmDate"
                data-cy="confirmDate"
                type="date"
              />
              <ValidatedField
                label={translate('farmerBeApp.supplyConfirmation.confirmValue')}
                id="supply-confirmation-confirmValue"
                name="confirmValue"
                data-cy="confirmValue"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.supplyConfirmation.isActive')}
                id="supply-confirmation-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('farmerBeApp.supplyConfirmation.createddBy')}
                id="supply-confirmation-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.supplyConfirmation.createdTime')}
                id="supply-confirmation-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.supplyConfirmation.updatedBy')}
                id="supply-confirmation-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.supplyConfirmation.updatedTime')}
                id="supply-confirmation-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="supply-confirmation-farm"
                name="farm"
                data-cy="farm"
                label={translate('farmerBeApp.supplyConfirmation.farm')}
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
                id="supply-confirmation-crop"
                name="crop"
                data-cy="crop"
                label={translate('farmerBeApp.supplyConfirmation.crop')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/supply-confirmation" replace color="info">
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

export default SupplyConfirmationUpdate;
