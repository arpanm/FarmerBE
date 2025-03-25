import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getFarmers } from 'app/entities/farmer/farmer.reducer';
import { createEntity, getEntity, updateEntity } from './terms-and-condition.reducer';

export const TermsAndConditionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const farmers = useAppSelector(state => state.farmer.entities);
  const termsAndConditionEntity = useAppSelector(state => state.termsAndCondition.entity);
  const loading = useAppSelector(state => state.termsAndCondition.loading);
  const updating = useAppSelector(state => state.termsAndCondition.updating);
  const updateSuccess = useAppSelector(state => state.termsAndCondition.updateSuccess);

  const handleClose = () => {
    navigate('/terms-and-condition');
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
    values.aggreedOn = convertDateTimeToServer(values.aggreedOn);
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...termsAndConditionEntity,
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
          aggreedOn: displayDefaultDateTime(),
          createdTime: displayDefaultDateTime(),
          updatedTime: displayDefaultDateTime(),
        }
      : {
          ...termsAndConditionEntity,
          aggreedOn: convertDateTimeFromServer(termsAndConditionEntity.aggreedOn),
          createdTime: convertDateTimeFromServer(termsAndConditionEntity.createdTime),
          updatedTime: convertDateTimeFromServer(termsAndConditionEntity.updatedTime),
          farmer: termsAndConditionEntity?.farmer?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="farmerBeApp.termsAndCondition.home.createOrEditLabel" data-cy="TermsAndConditionCreateUpdateHeading">
            <Translate contentKey="farmerBeApp.termsAndCondition.home.createOrEditLabel">Create or edit a TermsAndCondition</Translate>
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
                  id="terms-and-condition-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('farmerBeApp.termsAndCondition.termsLink')}
                id="terms-and-condition-termsLink"
                name="termsLink"
                data-cy="termsLink"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.termsAndCondition.aggreedOn')}
                id="terms-and-condition-aggreedOn"
                name="aggreedOn"
                data-cy="aggreedOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('farmerBeApp.termsAndCondition.isActive')}
                id="terms-and-condition-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('farmerBeApp.termsAndCondition.createddBy')}
                id="terms-and-condition-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.termsAndCondition.createdTime')}
                id="terms-and-condition-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.termsAndCondition.updatedBy')}
                id="terms-and-condition-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.termsAndCondition.updatedTime')}
                id="terms-and-condition-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="terms-and-condition-farmer"
                name="farmer"
                data-cy="farmer"
                label={translate('farmerBeApp.termsAndCondition.farmer')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/terms-and-condition" replace color="info">
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

export default TermsAndConditionUpdate;
