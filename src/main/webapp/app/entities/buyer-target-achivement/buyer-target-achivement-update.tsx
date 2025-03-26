import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getBuyers } from 'app/entities/buyer/buyer.reducer';
import { createEntity, getEntity, updateEntity } from './buyer-target-achivement.reducer';

export const BuyerTargetAchivementUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const buyers = useAppSelector(state => state.buyer.entities);
  const buyerTargetAchivementEntity = useAppSelector(state => state.buyerTargetAchivement.entity);
  const loading = useAppSelector(state => state.buyerTargetAchivement.loading);
  const updating = useAppSelector(state => state.buyerTargetAchivement.updating);
  const updateSuccess = useAppSelector(state => state.buyerTargetAchivement.updateSuccess);

  const handleClose = () => {
    navigate('/buyer-target-achivement');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getBuyers({}));
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
    if (values.labour !== undefined && typeof values.labour !== 'number') {
      values.labour = Number(values.labour);
    }
    if (values.farmVisit !== undefined && typeof values.farmVisit !== 'number') {
      values.farmVisit = Number(values.farmVisit);
    }
    if (values.totalCollection !== undefined && typeof values.totalCollection !== 'number') {
      values.totalCollection = Number(values.totalCollection);
    }
    if (values.attendenceHours !== undefined && typeof values.attendenceHours !== 'number') {
      values.attendenceHours = Number(values.attendenceHours);
    }
    if (values.achivementLabour !== undefined && typeof values.achivementLabour !== 'number') {
      values.achivementLabour = Number(values.achivementLabour);
    }
    if (values.achivementFarmVisit !== undefined && typeof values.achivementFarmVisit !== 'number') {
      values.achivementFarmVisit = Number(values.achivementFarmVisit);
    }
    if (values.achivementTotalCollection !== undefined && typeof values.achivementTotalCollection !== 'number') {
      values.achivementTotalCollection = Number(values.achivementTotalCollection);
    }
    if (values.achivementAttendenceHours !== undefined && typeof values.achivementAttendenceHours !== 'number') {
      values.achivementAttendenceHours = Number(values.achivementAttendenceHours);
    }
    if (values.achivementScore !== undefined && typeof values.achivementScore !== 'number') {
      values.achivementScore = Number(values.achivementScore);
    }
    if (values.incentive !== undefined && typeof values.incentive !== 'number') {
      values.incentive = Number(values.incentive);
    }
    if (values.kmDriven !== undefined && typeof values.kmDriven !== 'number') {
      values.kmDriven = Number(values.kmDriven);
    }
    if (values.conveyance !== undefined && typeof values.conveyance !== 'number') {
      values.conveyance = Number(values.conveyance);
    }
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...buyerTargetAchivementEntity,
      ...values,
      buyer: buyers.find(it => it.id.toString() === values.buyer?.toString()),
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
          ...buyerTargetAchivementEntity,
          createdTime: convertDateTimeFromServer(buyerTargetAchivementEntity.createdTime),
          updatedTime: convertDateTimeFromServer(buyerTargetAchivementEntity.updatedTime),
          buyer: buyerTargetAchivementEntity?.buyer?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="farmerBeApp.buyerTargetAchivement.home.createOrEditLabel" data-cy="BuyerTargetAchivementCreateUpdateHeading">
            <Translate contentKey="farmerBeApp.buyerTargetAchivement.home.createOrEditLabel">
              Create or edit a BuyerTargetAchivement
            </Translate>
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
                  id="buyer-target-achivement-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('farmerBeApp.buyerTargetAchivement.labour')}
                id="buyer-target-achivement-labour"
                name="labour"
                data-cy="labour"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.buyerTargetAchivement.farmVisit')}
                id="buyer-target-achivement-farmVisit"
                name="farmVisit"
                data-cy="farmVisit"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.buyerTargetAchivement.totalCollection')}
                id="buyer-target-achivement-totalCollection"
                name="totalCollection"
                data-cy="totalCollection"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.buyerTargetAchivement.targetDate')}
                id="buyer-target-achivement-targetDate"
                name="targetDate"
                data-cy="targetDate"
                type="date"
              />
              <ValidatedField
                label={translate('farmerBeApp.buyerTargetAchivement.attendenceHours')}
                id="buyer-target-achivement-attendenceHours"
                name="attendenceHours"
                data-cy="attendenceHours"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.buyerTargetAchivement.achivementLabour')}
                id="buyer-target-achivement-achivementLabour"
                name="achivementLabour"
                data-cy="achivementLabour"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.buyerTargetAchivement.achivementFarmVisit')}
                id="buyer-target-achivement-achivementFarmVisit"
                name="achivementFarmVisit"
                data-cy="achivementFarmVisit"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.buyerTargetAchivement.achivementTotalCollection')}
                id="buyer-target-achivement-achivementTotalCollection"
                name="achivementTotalCollection"
                data-cy="achivementTotalCollection"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.buyerTargetAchivement.achivementAttendenceHours')}
                id="buyer-target-achivement-achivementAttendenceHours"
                name="achivementAttendenceHours"
                data-cy="achivementAttendenceHours"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.buyerTargetAchivement.achivementScore')}
                id="buyer-target-achivement-achivementScore"
                name="achivementScore"
                data-cy="achivementScore"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.buyerTargetAchivement.incentive')}
                id="buyer-target-achivement-incentive"
                name="incentive"
                data-cy="incentive"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.buyerTargetAchivement.kmDriven')}
                id="buyer-target-achivement-kmDriven"
                name="kmDriven"
                data-cy="kmDriven"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.buyerTargetAchivement.conveyance')}
                id="buyer-target-achivement-conveyance"
                name="conveyance"
                data-cy="conveyance"
                type="text"
              />
              <ValidatedField
                label={translate('farmerBeApp.buyerTargetAchivement.isActive')}
                id="buyer-target-achivement-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('farmerBeApp.buyerTargetAchivement.createddBy')}
                id="buyer-target-achivement-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.buyerTargetAchivement.createdTime')}
                id="buyer-target-achivement-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.buyerTargetAchivement.updatedBy')}
                id="buyer-target-achivement-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('farmerBeApp.buyerTargetAchivement.updatedTime')}
                id="buyer-target-achivement-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="buyer-target-achivement-buyer"
                name="buyer"
                data-cy="buyer"
                label={translate('farmerBeApp.buyerTargetAchivement.buyer')}
                type="select"
              >
                <option value="" key="0" />
                {buyers
                  ? buyers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/buyer-target-achivement" replace color="info">
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

export default BuyerTargetAchivementUpdate;
