import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getDemandDataFiles } from 'app/entities/demand-data-file/demand-data-file.reducer';
import { getEntities as getCollectionCenters } from 'app/entities/collection-center/collection-center.reducer';
import { createEntity, getEntity, updateEntity } from './demand-data.reducer';

export const DemandDataUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const demandDataFiles = useAppSelector(state => state.demandDataFile.entities);
  const collectionCenters = useAppSelector(state => state.collectionCenter.entities);
  const demandDataEntity = useAppSelector(state => state.demandData.entity);
  const loading = useAppSelector(state => state.demandData.loading);
  const updating = useAppSelector(state => state.demandData.updating);
  const updateSuccess = useAppSelector(state => state.demandData.updateSuccess);

  const handleClose = () => {
    navigate('/demand-data');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getDemandDataFiles({}));
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
    if (values.netWeightGrams !== undefined && typeof values.netWeightGrams !== 'number') {
      values.netWeightGrams = Number(values.netWeightGrams);
    }
    if (values.crateSize !== undefined && typeof values.crateSize !== 'number') {
      values.crateSize = Number(values.crateSize);
    }
    if (values.indentUom !== undefined && typeof values.indentUom !== 'number') {
      values.indentUom = Number(values.indentUom);
    }
    if (values.indentKg !== undefined && typeof values.indentKg !== 'number') {
      values.indentKg = Number(values.indentKg);
    }
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...demandDataEntity,
      ...values,
      file: demandDataFiles.find(it => it.id.toString() === values.file?.toString()),
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
          ...demandDataEntity,
          createdTime: convertDateTimeFromServer(demandDataEntity.createdTime),
          updatedTime: convertDateTimeFromServer(demandDataEntity.updatedTime),
          file: demandDataEntity?.file?.id,
          collectionCenter: demandDataEntity?.collectionCenter?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="farmerBeApp.demandData.home.createOrEditLabel" data-cy="DemandDataCreateUpdateHeading">
            <Translate contentKey="farmerBeApp.demandData.home.createOrEditLabel">Create or edit a DemandData</Translate>
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
                  id="demand-data-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('farmerBeApp.demandData.fromCPC')}
                id="demand-data-fromCPC"
                name="fromCPC"
                data-cy="fromCPC"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.demandData.toCC')}
                id="demand-data-toCC"
                name="toCC"
                data-cy="toCC"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.demandData.pCode')}
                id="demand-data-pCode"
                name="pCode"
                data-cy="pCode"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.demandData.article')}
                id="demand-data-article"
                name="article"
                data-cy="article"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.demandData.description')}
                id="demand-data-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField label={translate('farmerBeApp.demandData.uom')} id="demand-data-uom" name="uom" data-cy="uom" type="text" />
              <ValidatedField
                label={translate('farmerBeApp.demandData.netWeightGrams')}
                id="demand-data-netWeightGrams"
                name="netWeightGrams"
                data-cy="netWeightGrams"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.demandData.crateSize')}
                id="demand-data-crateSize"
                name="crateSize"
                data-cy="crateSize"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.demandData.indentUom')}
                id="demand-data-indentUom"
                name="indentUom"
                data-cy="indentUom"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.demandData.indentKg')}
                id="demand-data-indentKg"
                name="indentKg"
                data-cy="indentKg"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.demandData.isActive')}
                id="demand-data-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('farmerBeApp.demandData.createddBy')}
                id="demand-data-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.demandData.createdTime')}
                id="demand-data-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.demandData.updatedBy')}
                id="demand-data-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.demandData.updatedTime')}
                id="demand-data-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="demand-data-file"
                name="file"
                data-cy="file"
                label={translate('farmerBeApp.demandData.file')}
                type="select"
              >
                <option value="" key="0" />
                {demandDataFiles
                  ? demandDataFiles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="demand-data-collectionCenter"
                name="collectionCenter"
                data-cy="collectionCenter"
                label={translate('farmerBeApp.demandData.collectionCenter')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/demand-data" replace color="info">
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

export default DemandDataUpdate;
