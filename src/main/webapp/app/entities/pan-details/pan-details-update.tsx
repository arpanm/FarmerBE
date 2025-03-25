import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getFarmers } from 'app/entities/farmer/farmer.reducer';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { createEntity, getEntity, updateEntity } from './pan-details.reducer';

export const PanDetailsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const farmers = useAppSelector(state => state.farmer.entities);
  const panDetailsEntity = useAppSelector(state => state.panDetails.entity);
  const loading = useAppSelector(state => state.panDetails.loading);
  const updating = useAppSelector(state => state.panDetails.updating);
  const updateSuccess = useAppSelector(state => state.panDetails.updateSuccess);
  const genderValues = Object.keys(Gender);

  const handleClose = () => {
    navigate('/pan-details');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getFarmers({}));
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
    values.verificationTime = convertDateTimeToServer(values.verificationTime);
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...panDetailsEntity,
      ...values,
      farmer: farmers.find(it => it.id.toString() === values.farmer?.toString()),
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
          verificationTime: displayDefaultDateTime(),
          createdTime: displayDefaultDateTime(),
          updatedTime: displayDefaultDateTime(),
        }
      : {
          gender: 'Male',
          ...panDetailsEntity,
          verificationTime: convertDateTimeFromServer(panDetailsEntity.verificationTime),
          createdTime: convertDateTimeFromServer(panDetailsEntity.createdTime),
          updatedTime: convertDateTimeFromServer(panDetailsEntity.updatedTime),
          farmer: panDetailsEntity?.farmer?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="farmerBeApp.panDetails.home.createOrEditLabel" data-cy="PanDetailsCreateUpdateHeading">
            <Translate contentKey="farmerBeApp.panDetails.home.createOrEditLabel">Create or edit a PanDetails</Translate>
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
                  id="pan-details-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('farmerBeApp.panDetails.pan')}
                id="pan-details-pan"
                name="pan"
                data-cy="pan"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.panDetails.name')}
                id="pan-details-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.panDetails.dateOfBirth')}
                id="pan-details-dateOfBirth"
                name="dateOfBirth"
                data-cy="dateOfBirth"
                type="date"
              />
              <ValidatedField
                label={translate('farmerBeApp.panDetails.gender')}
                id="pan-details-gender"
                name="gender"
                data-cy="gender"
                type="select"
              >
                {genderValues.map(gender => (
                  <option value={gender} key={gender}>
                    {translate(`farmerBeApp.Gender.${gender}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('farmerBeApp.panDetails.country')}
                id="pan-details-country"
                name="country"
                data-cy="country"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.panDetails.isVerified')}
                id="pan-details-isVerified"
                name="isVerified"
                data-cy="isVerified"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('farmerBeApp.panDetails.verificationTime')}
                id="pan-details-verificationTime"
                name="verificationTime"
                data-cy="verificationTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('farmerBeApp.panDetails.isActive')}
                id="pan-details-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('farmerBeApp.panDetails.createddBy')}
                id="pan-details-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.panDetails.createdTime')}
                id="pan-details-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.panDetails.updatedBy')}
                id="pan-details-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.panDetails.updatedTime')}
                id="pan-details-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="pan-details-farmer"
                name="farmer"
                data-cy="farmer"
                label={translate('farmerBeApp.panDetails.farmer')}
                type="select"
              >
                <option value="" key="0" />
                {farmers
                  ? farmers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/pan-details" replace color="info">
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

export default PanDetailsUpdate;
