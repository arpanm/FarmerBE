import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { Language } from 'app/shared/model/enumerations/language.model';
import { createEntity, getEntity, updateEntity } from './session-context.reducer';

export const SessionContextUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const sessionContextEntity = useAppSelector(state => state.sessionContext.entity);
  const loading = useAppSelector(state => state.sessionContext.loading);
  const updating = useAppSelector(state => state.sessionContext.updating);
  const updateSuccess = useAppSelector(state => state.sessionContext.updateSuccess);
  const languageValues = Object.keys(Language);

  const handleClose = () => {
    navigate('/session-context');
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
      ...sessionContextEntity,
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
          language: 'English',
          ...sessionContextEntity,
          createdTime: convertDateTimeFromServer(sessionContextEntity.createdTime),
          updatedTime: convertDateTimeFromServer(sessionContextEntity.updatedTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="farmerBeApp.sessionContext.home.createOrEditLabel" data-cy="SessionContextCreateUpdateHeading">
            <Translate contentKey="farmerBeApp.sessionContext.home.createOrEditLabel">Create or edit a SessionContext</Translate>
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
                  id="session-context-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('farmerBeApp.sessionContext.sessionId')}
                id="session-context-sessionId"
                name="sessionId"
                data-cy="sessionId"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.sessionContext.language')}
                id="session-context-language"
                name="language"
                data-cy="language"
                type="select"
              >
                {languageValues.map(language => (
                  <option value={language} key={language}>
                    {translate(`farmerBeApp.Language.${language}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('farmerBeApp.sessionContext.isLoggedIn')}
                id="session-context-isLoggedIn"
                name="isLoggedIn"
                data-cy="isLoggedIn"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('farmerBeApp.sessionContext.farmerId')}
                id="session-context-farmerId"
                name="farmerId"
                data-cy="farmerId"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.sessionContext.isActive')}
                id="session-context-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('farmerBeApp.sessionContext.createddBy')}
                id="session-context-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.sessionContext.createdTime')}
                id="session-context-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.sessionContext.updatedBy')}
                id="session-context-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.sessionContext.updatedTime')}
                id="session-context-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/session-context" replace color="info">
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

export default SessionContextUpdate;
