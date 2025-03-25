import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getCarouselContents } from 'app/entities/carousel-content/carousel-content.reducer';
import { createEntity, getEntity, updateEntity } from './banner-content.reducer';

export const BannerContentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const carouselContents = useAppSelector(state => state.carouselContent.entities);
  const bannerContentEntity = useAppSelector(state => state.bannerContent.entity);
  const loading = useAppSelector(state => state.bannerContent.loading);
  const updating = useAppSelector(state => state.bannerContent.updating);
  const updateSuccess = useAppSelector(state => state.bannerContent.updateSuccess);

  const handleClose = () => {
    navigate('/banner-content');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getCarouselContents({}));
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
    values.startTime = convertDateTimeToServer(values.startTime);
    values.endTime = convertDateTimeToServer(values.endTime);
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...bannerContentEntity,
      ...values,
      holdingCarousel: carouselContents.find(it => it.id.toString() === values.holdingCarousel?.toString()),
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
          startTime: displayDefaultDateTime(),
          endTime: displayDefaultDateTime(),
          createdTime: displayDefaultDateTime(),
          updatedTime: displayDefaultDateTime(),
        }
      : {
          ...bannerContentEntity,
          startTime: convertDateTimeFromServer(bannerContentEntity.startTime),
          endTime: convertDateTimeFromServer(bannerContentEntity.endTime),
          createdTime: convertDateTimeFromServer(bannerContentEntity.createdTime),
          updatedTime: convertDateTimeFromServer(bannerContentEntity.updatedTime),
          holdingCarousel: bannerContentEntity?.holdingCarousel?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="farmerBeApp.bannerContent.home.createOrEditLabel" data-cy="BannerContentCreateUpdateHeading">
            <Translate contentKey="farmerBeApp.bannerContent.home.createOrEditLabel">Create or edit a BannerContent</Translate>
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
                  id="banner-content-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('farmerBeApp.bannerContent.bannerTag')}
                id="banner-content-bannerTag"
                name="bannerTag"
                data-cy="bannerTag"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.bannerContent.logoPath')}
                id="banner-content-logoPath"
                name="logoPath"
                data-cy="logoPath"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.bannerContent.imagePath')}
                id="banner-content-imagePath"
                name="imagePath"
                data-cy="imagePath"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.bannerContent.heading')}
                id="banner-content-heading"
                name="heading"
                data-cy="heading"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.bannerContent.subHeading')}
                id="banner-content-subHeading"
                name="subHeading"
                data-cy="subHeading"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.bannerContent.description')}
                id="banner-content-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.bannerContent.landingLink')}
                id="banner-content-landingLink"
                name="landingLink"
                data-cy="landingLink"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.bannerContent.landingUtm')}
                id="banner-content-landingUtm"
                name="landingUtm"
                data-cy="landingUtm"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.bannerContent.pixelLink')}
                id="banner-content-pixelLink"
                name="pixelLink"
                data-cy="pixelLink"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.bannerContent.startTime')}
                id="banner-content-startTime"
                name="startTime"
                data-cy="startTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('farmerBeApp.bannerContent.endTime')}
                id="banner-content-endTime"
                name="endTime"
                data-cy="endTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('farmerBeApp.bannerContent.isActive')}
                id="banner-content-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('farmerBeApp.bannerContent.createddBy')}
                id="banner-content-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.bannerContent.createdTime')}
                id="banner-content-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.bannerContent.updatedBy')}
                id="banner-content-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.bannerContent.updatedTime')}
                id="banner-content-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="banner-content-holdingCarousel"
                name="holdingCarousel"
                data-cy="holdingCarousel"
                label={translate('farmerBeApp.bannerContent.holdingCarousel')}
                type="select"
              >
                <option value="" key="0" />
                {carouselContents
                  ? carouselContents.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/banner-content" replace color="info">
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

export default BannerContentUpdate;
