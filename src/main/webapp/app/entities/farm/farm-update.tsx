import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getCrops } from 'app/entities/crop/crop.reducer';
import { getEntities as getAccessories } from 'app/entities/accessories/accessories.reducer';
import { getEntities as getFarmers } from 'app/entities/farmer/farmer.reducer';
import { getEntities as getCollectionCenters } from 'app/entities/collection-center/collection-center.reducer';
import { getEntities as getBuyers } from 'app/entities/buyer/buyer.reducer';
import { FarmType } from 'app/shared/model/enumerations/farm-type.model';
import { createEntity, getEntity, updateEntity } from './farm.reducer';

export const FarmUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const crops = useAppSelector(state => state.crop.entities);
  const accessories = useAppSelector(state => state.accessories.entities);
  const farmers = useAppSelector(state => state.farmer.entities);
  const collectionCenters = useAppSelector(state => state.collectionCenter.entities);
  const buyers = useAppSelector(state => state.buyer.entities);
  const farmEntity = useAppSelector(state => state.farm.entity);
  const loading = useAppSelector(state => state.farm.loading);
  const updating = useAppSelector(state => state.farm.updating);
  const updateSuccess = useAppSelector(state => state.farm.updateSuccess);
  const farmTypeValues = Object.keys(FarmType);

  const handleClose = () => {
    navigate('/farm');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getCrops({}));
    dispatch(getAccessories({}));
    dispatch(getFarmers({}));
    dispatch(getCollectionCenters({}));
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
    if (values.areaInAcres !== undefined && typeof values.areaInAcres !== 'number') {
      values.areaInAcres = Number(values.areaInAcres);
    }
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...farmEntity,
      ...values,
      crops: mapIdList(values.crops),
      accessories: mapIdList(values.accessories),
      farmer: farmers.find(it => it.id.toString() === values.farmer?.toString()),
      collectionCenter: collectionCenters.find(it => it.id.toString() === values.collectionCenter?.toString()),
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
          createdTime: displayDefaultDateTime(),
          updatedTime: displayDefaultDateTime(),
        }
      : {
          farmType: 'Own',
          ...farmEntity,
          createdTime: convertDateTimeFromServer(farmEntity.createdTime),
          updatedTime: convertDateTimeFromServer(farmEntity.updatedTime),
          crops: farmEntity?.crops?.map(e => e.id.toString()),
          accessories: farmEntity?.accessories?.map(e => e.id.toString()),
          farmer: farmEntity?.farmer?.id,
          collectionCenter: farmEntity?.collectionCenter?.id,
          buyer: farmEntity?.buyer?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="farmerBeApp.farm.home.createOrEditLabel" data-cy="FarmCreateUpdateHeading">
            <Translate contentKey="farmerBeApp.farm.home.createOrEditLabel">Create or edit a Farm</Translate>
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
                  id="farm-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('farmerBeApp.farm.farmType')}
                id="farm-farmType"
                name="farmType"
                data-cy="farmType"
                type="select"
              >
                {farmTypeValues.map(farmType => (
                  <option value={farmType} key={farmType}>
                    {translate(`farmerBeApp.FarmType.${farmType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('farmerBeApp.farm.ownerName')}
                id="farm-ownerName"
                name="ownerName"
                data-cy="ownerName"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.farm.relationshipWithOwner')}
                id="farm-relationshipWithOwner"
                name="relationshipWithOwner"
                data-cy="relationshipWithOwner"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.farm.areaInAcres')}
                id="farm-areaInAcres"
                name="areaInAcres"
                data-cy="areaInAcres"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.farm.farmDocumentNo')}
                id="farm-farmDocumentNo"
                name="farmDocumentNo"
                data-cy="farmDocumentNo"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.farm.isActive')}
                id="farm-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('farmerBeApp.farm.createddBy')}
                id="farm-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.farm.createdTime')}
                id="farm-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.farm.updatedBy')}
                id="farm-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.farm.updatedTime')}
                id="farm-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField label={translate('farmerBeApp.farm.crop')} id="farm-crop" data-cy="crop" type="select" multiple name="crops">
                <option value="" key="0" />
                {crops
                  ? crops.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('farmerBeApp.farm.accessories')}
                id="farm-accessories"
                data-cy="accessories"
                type="select"
                multiple
                name="accessories"
              >
                <option value="" key="0" />
                {accessories
                  ? accessories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="farm-farmer" name="farmer" data-cy="farmer" label={translate('farmerBeApp.farm.farmer')} type="select">
                <option value="" key="0" />
                {farmers
                  ? farmers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="farm-collectionCenter"
                name="collectionCenter"
                data-cy="collectionCenter"
                label={translate('farmerBeApp.farm.collectionCenter')}
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
              <ValidatedField id="farm-buyer" name="buyer" data-cy="buyer" label={translate('farmerBeApp.farm.buyer')} type="select">
                <option value="" key="0" />
                {buyers
                  ? buyers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/farm" replace color="info">
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

export default FarmUpdate;
