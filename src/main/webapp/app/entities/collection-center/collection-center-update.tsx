import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getCrops } from 'app/entities/crop/crop.reducer';
import { createEntity, getEntity, updateEntity } from './collection-center.reducer';

export const CollectionCenterUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const crops = useAppSelector(state => state.crop.entities);
  const collectionCenterEntity = useAppSelector(state => state.collectionCenter.entity);
  const loading = useAppSelector(state => state.collectionCenter.loading);
  const updating = useAppSelector(state => state.collectionCenter.updating);
  const updateSuccess = useAppSelector(state => state.collectionCenter.updateSuccess);

  const handleClose = () => {
    navigate('/collection-center');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

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
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...collectionCenterEntity,
      ...values,
      crops: mapIdList(values.crops),
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
          ...collectionCenterEntity,
          createdTime: convertDateTimeFromServer(collectionCenterEntity.createdTime),
          updatedTime: convertDateTimeFromServer(collectionCenterEntity.updatedTime),
          crops: collectionCenterEntity?.crops?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="farmerBeApp.collectionCenter.home.createOrEditLabel" data-cy="CollectionCenterCreateUpdateHeading">
            <Translate contentKey="farmerBeApp.collectionCenter.home.createOrEditLabel">Create or edit a CollectionCenter</Translate>
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
                  id="collection-center-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('farmerBeApp.collectionCenter.name')}
                id="collection-center-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.collectionCenter.ccId')}
                id="collection-center-ccId"
                name="ccId"
                data-cy="ccId"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.collectionCenter.ffdcCode')}
                id="collection-center-ffdcCode"
                name="ffdcCode"
                data-cy="ffdcCode"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.collectionCenter.ffdcName')}
                id="collection-center-ffdcName"
                name="ffdcName"
                data-cy="ffdcName"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.collectionCenter.isActive')}
                id="collection-center-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('farmerBeApp.collectionCenter.createddBy')}
                id="collection-center-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.collectionCenter.createdTime')}
                id="collection-center-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.collectionCenter.updatedBy')}
                id="collection-center-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.collectionCenter.updatedTime')}
                id="collection-center-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.collectionCenter.crop')}
                id="collection-center-crop"
                data-cy="crop"
                type="select"
                multiple
                name="crops"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/collection-center" replace color="info">
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

export default CollectionCenterUpdate;
