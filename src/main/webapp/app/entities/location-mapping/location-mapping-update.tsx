import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getCollectionCenters } from 'app/entities/collection-center/collection-center.reducer';
import { AreaType } from 'app/shared/model/enumerations/area-type.model';
import { createEntity, getEntity, updateEntity } from './location-mapping.reducer';

export const LocationMappingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const collectionCenters = useAppSelector(state => state.collectionCenter.entities);
  const locationMappingEntity = useAppSelector(state => state.locationMapping.entity);
  const loading = useAppSelector(state => state.locationMapping.loading);
  const updating = useAppSelector(state => state.locationMapping.updating);
  const updateSuccess = useAppSelector(state => state.locationMapping.updateSuccess);
  const areaTypeValues = Object.keys(AreaType);

  const handleClose = () => {
    navigate('/location-mapping');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getCollectionCenters({}));
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
    if (values.pincode !== undefined && typeof values.pincode !== 'number') {
      values.pincode = Number(values.pincode);
    }
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...locationMappingEntity,
      ...values,
      collectionCenter: collectionCenters.find(it => it.id.toString() === values.collectionCenter?.toString()),
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
          areaType: 'Pincode',
          ...locationMappingEntity,
          createdTime: convertDateTimeFromServer(locationMappingEntity.createdTime),
          updatedTime: convertDateTimeFromServer(locationMappingEntity.updatedTime),
          collectionCenter: locationMappingEntity?.collectionCenter?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="farmerBeApp.locationMapping.home.createOrEditLabel" data-cy="LocationMappingCreateUpdateHeading">
            <Translate contentKey="farmerBeApp.locationMapping.home.createOrEditLabel">Create or edit a LocationMapping</Translate>
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
                  id="location-mapping-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('farmerBeApp.locationMapping.areaName')}
                id="location-mapping-areaName"
                name="areaName"
                data-cy="areaName"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.locationMapping.areaType')}
                id="location-mapping-areaType"
                name="areaType"
                data-cy="areaType"
                type="select"
              >
                {areaTypeValues.map(areaType => (
                  <option value={areaType} key={areaType}>
                    {translate(`farmerBeApp.AreaType.${areaType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('farmerBeApp.locationMapping.pincode')}
                id="location-mapping-pincode"
                name="pincode"
                data-cy="pincode"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.locationMapping.isActive')}
                id="location-mapping-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('farmerBeApp.locationMapping.createddBy')}
                id="location-mapping-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.locationMapping.createdTime')}
                id="location-mapping-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.locationMapping.updatedBy')}
                id="location-mapping-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.locationMapping.updatedTime')}
                id="location-mapping-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="location-mapping-collectionCenter"
                name="collectionCenter"
                data-cy="collectionCenter"
                label={translate('farmerBeApp.locationMapping.collectionCenter')}
                type="select"
              >
                <option value="" key="0" />
                {collectionCenters
                  ? collectionCenters.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/location-mapping" replace color="info">
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

export default LocationMappingUpdate;
