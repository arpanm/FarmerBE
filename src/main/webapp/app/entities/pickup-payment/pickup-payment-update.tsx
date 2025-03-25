import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { PaymentStatus } from 'app/shared/model/enumerations/payment-status.model';
import { createEntity, getEntity, updateEntity } from './pickup-payment.reducer';

export const PickupPaymentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const pickupPaymentEntity = useAppSelector(state => state.pickupPayment.entity);
  const loading = useAppSelector(state => state.pickupPayment.loading);
  const updating = useAppSelector(state => state.pickupPayment.updating);
  const updateSuccess = useAppSelector(state => state.pickupPayment.updateSuccess);
  const paymentStatusValues = Object.keys(PaymentStatus);

  const handleClose = () => {
    navigate('/pickup-payment');
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
    values.paymentUpdatedTime = convertDateTimeToServer(values.paymentUpdatedTime);
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...pickupPaymentEntity,
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
          paymentUpdatedTime: displayDefaultDateTime(),
          createdTime: displayDefaultDateTime(),
          updatedTime: displayDefaultDateTime(),
        }
      : {
          paymentStatus: 'Pending',
          ...pickupPaymentEntity,
          paymentUpdatedTime: convertDateTimeFromServer(pickupPaymentEntity.paymentUpdatedTime),
          createdTime: convertDateTimeFromServer(pickupPaymentEntity.createdTime),
          updatedTime: convertDateTimeFromServer(pickupPaymentEntity.updatedTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="farmerBeApp.pickupPayment.home.createOrEditLabel" data-cy="PickupPaymentCreateUpdateHeading">
            <Translate contentKey="farmerBeApp.pickupPayment.home.createOrEditLabel">Create or edit a PickupPayment</Translate>
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
                  id="pickup-payment-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('farmerBeApp.pickupPayment.paymentStatus')}
                id="pickup-payment-paymentStatus"
                name="paymentStatus"
                data-cy="paymentStatus"
                type="select"
              >
                {paymentStatusValues.map(paymentStatus => (
                  <option value={paymentStatus} key={paymentStatus}>
                    {translate(`farmerBeApp.PaymentStatus.${paymentStatus}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('farmerBeApp.pickupPayment.transactionId')}
                id="pickup-payment-transactionId"
                name="transactionId"
                data-cy="transactionId"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.pickupPayment.paymentDate')}
                id="pickup-payment-paymentDate"
                name="paymentDate"
                data-cy="paymentDate"
                type="date"
              />
              <ValidatedField
                label={translate('farmerBeApp.pickupPayment.details')}
                id="pickup-payment-details"
                name="details"
                data-cy="details"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.pickupPayment.paymentUpdatedBy')}
                id="pickup-payment-paymentUpdatedBy"
                name="paymentUpdatedBy"
                data-cy="paymentUpdatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.pickupPayment.paymentUpdatedTime')}
                id="pickup-payment-paymentUpdatedTime"
                name="paymentUpdatedTime"
                data-cy="paymentUpdatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('farmerBeApp.pickupPayment.isActive')}
                id="pickup-payment-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('farmerBeApp.pickupPayment.createddBy')}
                id="pickup-payment-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.pickupPayment.createdTime')}
                id="pickup-payment-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.pickupPayment.updatedBy')}
                id="pickup-payment-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.pickupPayment.updatedTime')}
                id="pickup-payment-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/pickup-payment" replace color="info">
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

export default PickupPaymentUpdate;
