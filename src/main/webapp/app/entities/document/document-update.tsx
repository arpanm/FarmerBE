import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getFarmers } from 'app/entities/farmer/farmer.reducer';
import { getEntities as getFarms } from 'app/entities/farm/farm.reducer';
import { getEntities as getAddresses } from 'app/entities/address/address.reducer';
import { getEntities as getPanDetails } from 'app/entities/pan-details/pan-details.reducer';
import { getEntities as getBankDetails } from 'app/entities/bank-details/bank-details.reducer';
import { getEntities as getFieldVisits } from 'app/entities/field-visit/field-visit.reducer';
import { DocumentType } from 'app/shared/model/enumerations/document-type.model';
import { DocumentFormat } from 'app/shared/model/enumerations/document-format.model';
import { createEntity, getEntity, updateEntity } from './document.reducer';

export const DocumentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const farmers = useAppSelector(state => state.farmer.entities);
  const farms = useAppSelector(state => state.farm.entities);
  const addresses = useAppSelector(state => state.address.entities);
  const panDetails = useAppSelector(state => state.panDetails.entities);
  const bankDetails = useAppSelector(state => state.bankDetails.entities);
  const fieldVisits = useAppSelector(state => state.fieldVisit.entities);
  const documentEntity = useAppSelector(state => state.document.entity);
  const loading = useAppSelector(state => state.document.loading);
  const updating = useAppSelector(state => state.document.updating);
  const updateSuccess = useAppSelector(state => state.document.updateSuccess);
  const documentTypeValues = Object.keys(DocumentType);
  const documentFormatValues = Object.keys(DocumentFormat);

  const handleClose = () => {
    navigate('/document');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getFarmers({}));
    dispatch(getFarms({}));
    dispatch(getAddresses({}));
    dispatch(getPanDetails({}));
    dispatch(getBankDetails({}));
    dispatch(getFieldVisits({}));
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
      ...documentEntity,
      ...values,
      farmer: farmers.find(it => it.id.toString() === values.farmer?.toString()),
      farm: farms.find(it => it.id.toString() === values.farm?.toString()),
      address: addresses.find(it => it.id.toString() === values.address?.toString()),
      panDetails: panDetails.find(it => it.id.toString() === values.panDetails?.toString()),
      bankDetails: bankDetails.find(it => it.id.toString() === values.bankDetails?.toString()),
      fieldVisit: fieldVisits.find(it => it.id.toString() === values.fieldVisit?.toString()),
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
          docType: 'AddressProof',
          docFormat: 'Pdf',
          ...documentEntity,
          createdTime: convertDateTimeFromServer(documentEntity.createdTime),
          updatedTime: convertDateTimeFromServer(documentEntity.updatedTime),
          farmer: documentEntity?.farmer?.id,
          farm: documentEntity?.farm?.id,
          address: documentEntity?.address?.id,
          panDetails: documentEntity?.panDetails?.id,
          bankDetails: documentEntity?.bankDetails?.id,
          fieldVisit: documentEntity?.fieldVisit?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="farmerBeApp.document.home.createOrEditLabel" data-cy="DocumentCreateUpdateHeading">
            <Translate contentKey="farmerBeApp.document.home.createOrEditLabel">Create or edit a Document</Translate>
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
                  id="document-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('farmerBeApp.document.docPath')}
                id="document-docPath"
                name="docPath"
                data-cy="docPath"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.document.docType')}
                id="document-docType"
                name="docType"
                data-cy="docType"
                type="select"
              >
                {documentTypeValues.map(documentType => (
                  <option value={documentType} key={documentType}>
                    {translate(`farmerBeApp.DocumentType.${documentType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('farmerBeApp.document.docFormat')}
                id="document-docFormat"
                name="docFormat"
                data-cy="docFormat"
                type="select"
              >
                {documentFormatValues.map(documentFormat => (
                  <option value={documentFormat} key={documentFormat}>
                    {translate(`farmerBeApp.DocumentFormat.${documentFormat}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('farmerBeApp.document.isActive')}
                id="document-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('farmerBeApp.document.createddBy')}
                id="document-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.document.createdTime')}
                id="document-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.document.updatedBy')}
                id="document-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.document.updatedTime')}
                id="document-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="document-farmer"
                name="farmer"
                data-cy="farmer"
                label={translate('farmerBeApp.document.farmer')}
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
              <ValidatedField id="document-farm" name="farm" data-cy="farm" label={translate('farmerBeApp.document.farm')} type="select">
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
                id="document-address"
                name="address"
                data-cy="address"
                label={translate('farmerBeApp.document.address')}
                type="select"
              >
                <option value="" key="0" />
                {addresses
                  ? addresses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="document-panDetails"
                name="panDetails"
                data-cy="panDetails"
                label={translate('farmerBeApp.document.panDetails')}
                type="select"
              >
                <option value="" key="0" />
                {panDetails
                  ? panDetails.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="document-bankDetails"
                name="bankDetails"
                data-cy="bankDetails"
                label={translate('farmerBeApp.document.bankDetails')}
                type="select"
              >
                <option value="" key="0" />
                {bankDetails
                  ? bankDetails.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="document-fieldVisit"
                name="fieldVisit"
                data-cy="fieldVisit"
                label={translate('farmerBeApp.document.fieldVisit')}
                type="select"
              >
                <option value="" key="0" />
                {fieldVisits
                  ? fieldVisits.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/document" replace color="info">
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

export default DocumentUpdate;
