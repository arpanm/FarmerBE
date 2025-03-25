import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { createEntity, getEntity, updateEntity } from './carousel-content.reducer';

export const CarouselContentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const carouselContentEntity = useAppSelector(state => state.carouselContent.entity);
  const loading = useAppSelector(state => state.carouselContent.loading);
  const updating = useAppSelector(state => state.carouselContent.updating);
  const updateSuccess = useAppSelector(state => state.carouselContent.updateSuccess);

  const handleClose = () => {
    navigate('/carousel-content');
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
      ...carouselContentEntity,
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
          ...carouselContentEntity,
          createdTime: convertDateTimeFromServer(carouselContentEntity.createdTime),
          updatedTime: convertDateTimeFromServer(carouselContentEntity.updatedTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="farmerBeApp.carouselContent.home.createOrEditLabel" data-cy="CarouselContentCreateUpdateHeading">
            <Translate contentKey="farmerBeApp.carouselContent.home.createOrEditLabel">Create or edit a CarouselContent</Translate>
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
                  id="carousel-content-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('farmerBeApp.carouselContent.carouselTag')}
                id="carousel-content-carouselTag"
                name="carouselTag"
                data-cy="carouselTag"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.carouselContent.showViewMore')}
                id="carousel-content-showViewMore"
                name="showViewMore"
                data-cy="showViewMore"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('farmerBeApp.carouselContent.viewMoreLink')}
                id="carousel-content-viewMoreLink"
                name="viewMoreLink"
                data-cy="viewMoreLink"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.carouselContent.viewMoreUtm')}
                id="carousel-content-viewMoreUtm"
                name="viewMoreUtm"
                data-cy="viewMoreUtm"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.carouselContent.pixelLink')}
                id="carousel-content-pixelLink"
                name="pixelLink"
                data-cy="pixelLink"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.carouselContent.isActive')}
                id="carousel-content-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('farmerBeApp.carouselContent.createddBy')}
                id="carousel-content-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.carouselContent.createdTime')}
                id="carousel-content-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.carouselContent.updatedBy')}
                id="carousel-content-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.carouselContent.updatedTime')}
                id="carousel-content-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/carousel-content" replace color="info">
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

export default CarouselContentUpdate;
