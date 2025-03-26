import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getBuyers } from 'app/entities/buyer/buyer.reducer';
import { AttendenceType } from 'app/shared/model/enumerations/attendence-type.model';
import { createEntity, getEntity, updateEntity } from './attendence.reducer';

export const AttendenceUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const buyers = useAppSelector(state => state.buyer.entities);
  const attendenceEntity = useAppSelector(state => state.attendence.entity);
  const loading = useAppSelector(state => state.attendence.loading);
  const updating = useAppSelector(state => state.attendence.updating);
  const updateSuccess = useAppSelector(state => state.attendence.updateSuccess);
  const attendenceTypeValues = Object.keys(AttendenceType);

  const handleClose = () => {
    navigate('/attendence');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getBuyers({}));
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
    values.attendenceTime = convertDateTimeToServer(values.attendenceTime);
    if (values.lat !== undefined && typeof values.lat !== 'number') {
      values.lat = Number(values.lat);
    }
    if (values.lon !== undefined && typeof values.lon !== 'number') {
      values.lon = Number(values.lon);
    }
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...attendenceEntity,
      ...values,
      buyer: buyers.find(it => it.id.toString() === values.buyer?.toString()),
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
          attendenceTime: displayDefaultDateTime(),
          createdTime: displayDefaultDateTime(),
          updatedTime: displayDefaultDateTime(),
        }
      : {
          attendenceType: 'CheckIn',
          ...attendenceEntity,
          attendenceTime: convertDateTimeFromServer(attendenceEntity.attendenceTime),
          createdTime: convertDateTimeFromServer(attendenceEntity.createdTime),
          updatedTime: convertDateTimeFromServer(attendenceEntity.updatedTime),
          buyer: attendenceEntity?.buyer?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="farmerBeApp.attendence.home.createOrEditLabel" data-cy="AttendenceCreateUpdateHeading">
            <Translate contentKey="farmerBeApp.attendence.home.createOrEditLabel">Create or edit a Attendence</Translate>
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
                  id="attendence-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('farmerBeApp.attendence.attendenceType')}
                id="attendence-attendenceType"
                name="attendenceType"
                data-cy="attendenceType"
                type="select"
              >
                {attendenceTypeValues.map(attendenceType => (
                  <option value={attendenceType} key={attendenceType}>
                    {translate(`farmerBeApp.AttendenceType.${attendenceType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('farmerBeApp.attendence.attendenceDate')}
                id="attendence-attendenceDate"
                name="attendenceDate"
                data-cy="attendenceDate"
                type="date"
              />
              <ValidatedField
                label={translate('farmerBeApp.attendence.attendenceTime')}
                id="attendence-attendenceTime"
                name="attendenceTime"
                data-cy="attendenceTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label={translate('farmerBeApp.attendence.lat')} id="attendence-lat" name="lat" data-cy="lat" type="text" />
              <ValidatedField label={translate('farmerBeApp.attendence.lon')} id="attendence-lon" name="lon" data-cy="lon" type="text" />
              <ValidatedField
                label={translate('farmerBeApp.attendence.isActive')}
                id="attendence-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('farmerBeApp.attendence.createddBy')}
                id="attendence-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.attendence.createdTime')}
                id="attendence-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.attendence.updatedBy')}
                id="attendence-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.attendence.updatedTime')}
                id="attendence-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="attendence-buyer"
                name="buyer"
                data-cy="buyer"
                label={translate('farmerBeApp.attendence.buyer')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/attendence" replace color="info">
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

export default AttendenceUpdate;
