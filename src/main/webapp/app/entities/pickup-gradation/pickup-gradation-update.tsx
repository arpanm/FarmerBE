import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ItemGrade } from 'app/shared/model/enumerations/item-grade.model';
import { createEntity, getEntity, updateEntity } from './pickup-gradation.reducer';

export const PickupGradationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const pickupGradationEntity = useAppSelector(state => state.pickupGradation.entity);
  const loading = useAppSelector(state => state.pickupGradation.loading);
  const updating = useAppSelector(state => state.pickupGradation.updating);
  const updateSuccess = useAppSelector(state => state.pickupGradation.updateSuccess);
  const itemGradeValues = Object.keys(ItemGrade);

  const handleClose = () => {
    navigate('/pickup-gradation');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }
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
    values.gradedTime = convertDateTimeToServer(values.gradedTime);
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...pickupGradationEntity,
      ...values,
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
          gradedTime: displayDefaultDateTime(),
          createdTime: displayDefaultDateTime(),
          updatedTime: displayDefaultDateTime(),
        }
      : {
          itemGrade: 'GoodQuality',
          ...pickupGradationEntity,
          gradedTime: convertDateTimeFromServer(pickupGradationEntity.gradedTime),
          createdTime: convertDateTimeFromServer(pickupGradationEntity.createdTime),
          updatedTime: convertDateTimeFromServer(pickupGradationEntity.updatedTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="farmerBeApp.pickupGradation.home.createOrEditLabel" data-cy="PickupGradationCreateUpdateHeading">
            <Translate contentKey="farmerBeApp.pickupGradation.home.createOrEditLabel">Create or edit a PickupGradation</Translate>
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
                  id="pickup-gradation-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('farmerBeApp.pickupGradation.itemGrade')}
                id="pickup-gradation-itemGrade"
                name="itemGrade"
                data-cy="itemGrade"
                type="select"
              >
                {itemGradeValues.map(itemGrade => (
                  <option value={itemGrade} key={itemGrade}>
                    {translate(`farmerBeApp.ItemGrade.${itemGrade}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('farmerBeApp.pickupGradation.gradedBy')}
                id="pickup-gradation-gradedBy"
                name="gradedBy"
                data-cy="gradedBy"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.pickupGradation.gradedTime')}
                id="pickup-gradation-gradedTime"
                name="gradedTime"
                data-cy="gradedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('farmerBeApp.pickupGradation.isActive')}
                id="pickup-gradation-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('farmerBeApp.pickupGradation.createddBy')}
                id="pickup-gradation-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.pickupGradation.createdTime')}
                id="pickup-gradation-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.pickupGradation.updatedBy')}
                id="pickup-gradation-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.pickupGradation.updatedTime')}
                id="pickup-gradation-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/pickup-gradation" replace color="info">
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

export default PickupGradationUpdate;
