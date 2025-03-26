import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { UploadStatus } from 'app/shared/model/enumerations/upload-status.model';
import { createEntity, getEntity, updateEntity } from './demand-data-file.reducer';

export const DemandDataFileUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const demandDataFileEntity = useAppSelector(state => state.demandDataFile.entity);
  const loading = useAppSelector(state => state.demandDataFile.loading);
  const updating = useAppSelector(state => state.demandDataFile.updating);
  const updateSuccess = useAppSelector(state => state.demandDataFile.updateSuccess);
  const uploadStatusValues = Object.keys(UploadStatus);

  const handleClose = () => {
    navigate('/demand-data-file');
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
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...demandDataFileEntity,
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
          createdTime: displayDefaultDateTime(),
          updatedTime: displayDefaultDateTime(),
        }
      : {
          status: 'Init',
          ...demandDataFileEntity,
          createdTime: convertDateTimeFromServer(demandDataFileEntity.createdTime),
          updatedTime: convertDateTimeFromServer(demandDataFileEntity.updatedTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="farmerBeApp.demandDataFile.home.createOrEditLabel" data-cy="DemandDataFileCreateUpdateHeading">
            <Translate contentKey="farmerBeApp.demandDataFile.home.createOrEditLabel">Create or edit a DemandDataFile</Translate>
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
                  id="demand-data-file-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('farmerBeApp.demandDataFile.uploadedDate')}
                id="demand-data-file-uploadedDate"
                name="uploadedDate"
                data-cy="uploadedDate"
                type="date"
              />
              <ValidatedField
                label={translate('farmerBeApp.demandDataFile.fileName')}
                id="demand-data-file-fileName"
                name="fileName"
                data-cy="fileName"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.demandDataFile.uploadedBy')}
                id="demand-data-file-uploadedBy"
                name="uploadedBy"
                data-cy="uploadedBy"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.demandDataFile.status')}
                id="demand-data-file-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {uploadStatusValues.map(uploadStatus => (
                  <option value={uploadStatus} key={uploadStatus}>
                    {translate(`farmerBeApp.UploadStatus.${uploadStatus}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('farmerBeApp.demandDataFile.isActive')}
                id="demand-data-file-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('farmerBeApp.demandDataFile.createddBy')}
                id="demand-data-file-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.demandDataFile.createdTime')}
                id="demand-data-file-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.demandDataFile.updatedBy')}
                id="demand-data-file-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.demandDataFile.updatedTime')}
                id="demand-data-file-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/demand-data-file" replace color="info">
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

export default DemandDataFileUpdate;
