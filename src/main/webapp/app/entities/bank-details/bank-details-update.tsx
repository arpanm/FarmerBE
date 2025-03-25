import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getFarmers } from 'app/entities/farmer/farmer.reducer';
import { createEntity, getEntity, updateEntity } from './bank-details.reducer';

export const BankDetailsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const farmers = useAppSelector(state => state.farmer.entities);
  const bankDetailsEntity = useAppSelector(state => state.bankDetails.entity);
  const loading = useAppSelector(state => state.bankDetails.loading);
  const updating = useAppSelector(state => state.bankDetails.updating);
  const updateSuccess = useAppSelector(state => state.bankDetails.updateSuccess);

  const handleClose = () => {
    navigate('/bank-details');
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
      ...bankDetailsEntity,
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
          ...bankDetailsEntity,
          verificationTime: convertDateTimeFromServer(bankDetailsEntity.verificationTime),
          createdTime: convertDateTimeFromServer(bankDetailsEntity.createdTime),
          updatedTime: convertDateTimeFromServer(bankDetailsEntity.updatedTime),
          farmer: bankDetailsEntity?.farmer?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="farmerBeApp.bankDetails.home.createOrEditLabel" data-cy="BankDetailsCreateUpdateHeading">
            <Translate contentKey="farmerBeApp.bankDetails.home.createOrEditLabel">Create or edit a BankDetails</Translate>
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
                  id="bank-details-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('farmerBeApp.bankDetails.name')}
                id="bank-details-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.bankDetails.accountNumber')}
                id="bank-details-accountNumber"
                name="accountNumber"
                data-cy="accountNumber"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.bankDetails.ifsC')}
                id="bank-details-ifsC"
                name="ifsC"
                data-cy="ifsC"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.bankDetails.bankName')}
                id="bank-details-bankName"
                name="bankName"
                data-cy="bankName"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.bankDetails.branchName')}
                id="bank-details-branchName"
                name="branchName"
                data-cy="branchName"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.bankDetails.isVerified')}
                id="bank-details-isVerified"
                name="isVerified"
                data-cy="isVerified"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('farmerBeApp.bankDetails.verificationTime')}
                id="bank-details-verificationTime"
                name="verificationTime"
                data-cy="verificationTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('farmerBeApp.bankDetails.isActive')}
                id="bank-details-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('farmerBeApp.bankDetails.createddBy')}
                id="bank-details-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.bankDetails.createdTime')}
                id="bank-details-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.bankDetails.updatedBy')}
                id="bank-details-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.bankDetails.updatedTime')}
                id="bank-details-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="bank-details-farmer"
                name="farmer"
                data-cy="farmer"
                label={translate('farmerBeApp.bankDetails.farmer')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bank-details" replace color="info">
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

export default BankDetailsUpdate;
