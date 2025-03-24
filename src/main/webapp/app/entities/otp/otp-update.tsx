import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getFarmers } from 'app/entities/farmer/farmer.reducer';
import { createEntity, getEntity, updateEntity } from './otp.reducer';

export const OtpUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const farmers = useAppSelector(state => state.farmer.entities);
  const otpEntity = useAppSelector(state => state.otp.entity);
  const loading = useAppSelector(state => state.otp.loading);
  const updating = useAppSelector(state => state.otp.updating);
  const updateSuccess = useAppSelector(state => state.otp.updateSuccess);

  const handleClose = () => {
    navigate('/otp');
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
    if (values.emailOtp !== undefined && typeof values.emailOtp !== 'number') {
      values.emailOtp = Number(values.emailOtp);
    }
    if (values.phone !== undefined && typeof values.phone !== 'number') {
      values.phone = Number(values.phone);
    }
    if (values.phoneOtp !== undefined && typeof values.phoneOtp !== 'number') {
      values.phoneOtp = Number(values.phoneOtp);
    }
    values.expiryTime = convertDateTimeToServer(values.expiryTime);
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...otpEntity,
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
          expiryTime: displayDefaultDateTime(),
          createdTime: displayDefaultDateTime(),
          updatedTime: displayDefaultDateTime(),
        }
      : {
          ...otpEntity,
          expiryTime: convertDateTimeFromServer(otpEntity.expiryTime),
          createdTime: convertDateTimeFromServer(otpEntity.createdTime),
          updatedTime: convertDateTimeFromServer(otpEntity.updatedTime),
          farmer: otpEntity?.farmer?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="farmerBeApp.otp.home.createOrEditLabel" data-cy="OtpCreateUpdateHeading">
            <Translate contentKey="farmerBeApp.otp.home.createOrEditLabel">Create or edit a Otp</Translate>
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
                  id="otp-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('farmerBeApp.otp.email')}
                id="otp-email"
                name="email"
                data-cy="email"
                type="text"
                validate={{
                  pattern: {
                    value: /^[^@\s]+@[^@\s]+\.[^@\s]+$/,
                    message: translate('entity.validation.pattern', { pattern: '^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$' }),
                  },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.otp.emailOtp')}
                id="otp-emailOtp"
                name="emailOtp"
                data-cy="emailOtp"
                type="text"
              />
              <ValidatedField label={translate('farmerBeApp.otp.phone')} id="otp-phone" name="phone" data-cy="phone" type="text" />
              <ValidatedField
                label={translate('farmerBeApp.otp.phoneOtp')}
                id="otp-phoneOtp"
                name="phoneOtp"
                data-cy="phoneOtp"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.otp.isValidated')}
                id="otp-isValidated"
                name="isValidated"
                data-cy="isValidated"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('farmerBeApp.otp.expiryTime')}
                id="otp-expiryTime"
                name="expiryTime"
                data-cy="expiryTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('farmerBeApp.otp.isActive')}
                id="otp-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('farmerBeApp.otp.createddBy')}
                id="otp-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.otp.createdTime')}
                id="otp-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.otp.updatedBy')}
                id="otp-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.otp.updatedTime')}
                id="otp-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField id="otp-farmer" name="farmer" data-cy="farmer" label={translate('farmerBeApp.otp.farmer')} type="select">
                <option value="" key="0" />
                {farmers
                  ? farmers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/otp" replace color="info">
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

export default OtpUpdate;
