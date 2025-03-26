import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getCollectionCenters } from 'app/entities/collection-center/collection-center.reducer';
import { Language } from 'app/shared/model/enumerations/language.model';
import { createEntity, getEntity, updateEntity } from './buyer.reducer';

export const BuyerUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const collectionCenters = useAppSelector(state => state.collectionCenter.entities);
  const buyerEntity = useAppSelector(state => state.buyer.entity);
  const loading = useAppSelector(state => state.buyer.loading);
  const updating = useAppSelector(state => state.buyer.updating);
  const updateSuccess = useAppSelector(state => state.buyer.updateSuccess);
  const languageValues = Object.keys(Language);

  const handleClose = () => {
    navigate('/buyer');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

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
    if (values.phone !== undefined && typeof values.phone !== 'number') {
      values.phone = Number(values.phone);
    }
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...buyerEntity,
      ...values,
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
          preferedLanguage: 'English',
          ...buyerEntity,
          createdTime: convertDateTimeFromServer(buyerEntity.createdTime),
          updatedTime: convertDateTimeFromServer(buyerEntity.updatedTime),
          collectionCenter: buyerEntity?.collectionCenter?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="farmerBeApp.buyer.home.createOrEditLabel" data-cy="BuyerCreateUpdateHeading">
            <Translate contentKey="farmerBeApp.buyer.home.createOrEditLabel">Create or edit a Buyer</Translate>
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
                  id="buyer-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('farmerBeApp.buyer.name')} id="buyer-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                label={translate('farmerBeApp.buyer.email')}
                id="buyer-email"
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
                label={translate('farmerBeApp.buyer.phone')}
                id="buyer-phone"
                name="phone"
                data-cy="phone"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 1000000000, message: translate('entity.validation.min', { min: 1000000000 }) },
                  max: { value: 9999999999, message: translate('entity.validation.max', { max: 9999999999 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.buyer.isWhatsAppEnabled')}
                id="buyer-isWhatsAppEnabled"
                name="isWhatsAppEnabled"
                data-cy="isWhatsAppEnabled"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('farmerBeApp.buyer.preferedLanguage')}
                id="buyer-preferedLanguage"
                name="preferedLanguage"
                data-cy="preferedLanguage"
                type="select"
              >
                {languageValues.map(language => (
                  <option value={language} key={language}>
                    {translate(`farmerBeApp.Language.${language}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('farmerBeApp.buyer.isActive')}
                id="buyer-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('farmerBeApp.buyer.createddBy')}
                id="buyer-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.buyer.createdTime')}
                id="buyer-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.buyer.updatedBy')}
                id="buyer-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.buyer.updatedTime')}
                id="buyer-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="buyer-collectionCenter"
                name="collectionCenter"
                data-cy="collectionCenter"
                label={translate('farmerBeApp.buyer.collectionCenter')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/buyer" replace color="info">
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

export default BuyerUpdate;
