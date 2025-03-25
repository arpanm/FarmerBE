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
import { createEntity, getEntity, updateEntity } from './accessories.reducer';

export const AccessoriesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const categories = useAppSelector(state => state.category.entities);
  const farms = useAppSelector(state => state.farm.entities);
  const accessoriesEntity = useAppSelector(state => state.accessories.entity);
  const loading = useAppSelector(state => state.accessories.loading);
  const updating = useAppSelector(state => state.accessories.updating);
  const updateSuccess = useAppSelector(state => state.accessories.updateSuccess);

  const handleClose = () => {
    navigate('/accessories');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getCategories({}));
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
    if (values.orderNo !== undefined && typeof values.orderNo !== 'number') {
      values.orderNo = Number(values.orderNo);
    }
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...accessoriesEntity,
      ...values,
      category: categories.find(it => it.id.toString() === values.category?.toString()),
      farms: mapIdList(values.farms),
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
          ...accessoriesEntity,
          createdTime: convertDateTimeFromServer(accessoriesEntity.createdTime),
          updatedTime: convertDateTimeFromServer(accessoriesEntity.updatedTime),
          category: accessoriesEntity?.category?.id,
          farms: accessoriesEntity?.farms?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="farmerBeApp.accessories.home.createOrEditLabel" data-cy="AccessoriesCreateUpdateHeading">
            <Translate contentKey="farmerBeApp.accessories.home.createOrEditLabel">Create or edit a Accessories</Translate>
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
                  id="accessories-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('farmerBeApp.accessories.name')}
                id="accessories-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.accessories.imagePath')}
                id="accessories-imagePath"
                name="imagePath"
                data-cy="imagePath"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.accessories.description')}
                id="accessories-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.accessories.orderNo')}
                id="accessories-orderNo"
                name="orderNo"
                data-cy="orderNo"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.accessories.skuId')}
                id="accessories-skuId"
                name="skuId"
                data-cy="skuId"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.accessories.isActive')}
                id="accessories-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('farmerBeApp.accessories.createddBy')}
                id="accessories-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.accessories.createdTime')}
                id="accessories-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.accessories.updatedBy')}
                id="accessories-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.accessories.updatedTime')}
                id="accessories-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="accessories-category"
                name="category"
                data-cy="category"
                label={translate('farmerBeApp.accessories.category')}
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
              <ValidatedField
                label={translate('farmerBeApp.accessories.farm')}
                id="accessories-farm"
                data-cy="farm"
                type="select"
                multiple
                name="farms"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/accessories" replace color="info">
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

export default AccessoriesUpdate;
