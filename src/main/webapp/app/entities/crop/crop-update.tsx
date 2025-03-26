import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getCategories } from 'app/entities/category/category.reducer';
import { getEntities as getFarms } from 'app/entities/farm/farm.reducer';
import { getEntities as getCollectionCenters } from 'app/entities/collection-center/collection-center.reducer';
import { createEntity, getEntity, updateEntity } from './crop.reducer';

export const CropUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const categories = useAppSelector(state => state.category.entities);
  const farms = useAppSelector(state => state.farm.entities);
  const collectionCenters = useAppSelector(state => state.collectionCenter.entities);
  const cropEntity = useAppSelector(state => state.crop.entity);
  const loading = useAppSelector(state => state.crop.loading);
  const updating = useAppSelector(state => state.crop.updating);
  const updateSuccess = useAppSelector(state => state.crop.updateSuccess);

  const handleClose = () => {
    navigate('/crop');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getCategories({}));
    dispatch(getFarms({}));
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
    if (values.orderNo !== undefined && typeof values.orderNo !== 'number') {
      values.orderNo = Number(values.orderNo);
    }
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...cropEntity,
      ...values,
      category: categories.find(it => it.id.toString() === values.category?.toString()),
      farms: mapIdList(values.farms),
      collectionCenters: mapIdList(values.collectionCenters),
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
          ...cropEntity,
          createdTime: convertDateTimeFromServer(cropEntity.createdTime),
          updatedTime: convertDateTimeFromServer(cropEntity.updatedTime),
          category: cropEntity?.category?.id,
          farms: cropEntity?.farms?.map(e => e.id.toString()),
          collectionCenters: cropEntity?.collectionCenters?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="farmerBeApp.crop.home.createOrEditLabel" data-cy="CropCreateUpdateHeading">
            <Translate contentKey="farmerBeApp.crop.home.createOrEditLabel">Create or edit a Crop</Translate>
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
                  id="crop-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('farmerBeApp.crop.name')} id="crop-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                label={translate('farmerBeApp.crop.imagePath')}
                id="crop-imagePath"
                name="imagePath"
                data-cy="imagePath"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.crop.description')}
                id="crop-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.crop.orderNo')}
                id="crop-orderNo"
                name="orderNo"
                data-cy="orderNo"
                type="text"
              />
              <ValidatedField label={translate('farmerBeApp.crop.skuId')} id="crop-skuId" name="skuId" data-cy="skuId" type="text" />
              <ValidatedField
                label={translate('farmerBeApp.crop.isActive')}
                id="crop-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('farmerBeApp.crop.createddBy')}
                id="crop-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.crop.createdTime')}
                id="crop-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.crop.updatedBy')}
                id="crop-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.crop.updatedTime')}
                id="crop-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="crop-category"
                name="category"
                data-cy="category"
                label={translate('farmerBeApp.crop.category')}
                type="select"
              >
                <option value="" key="0" />
                {categories
                  ? categories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField label={translate('farmerBeApp.crop.farm')} id="crop-farm" data-cy="farm" type="select" multiple name="farms">
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
                label={translate('farmerBeApp.crop.collectionCenter')}
                id="crop-collectionCenter"
                data-cy="collectionCenter"
                type="select"
                multiple
                name="collectionCenters"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/crop" replace color="info">
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

export default CropUpdate;
