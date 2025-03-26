import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './buyer-target-achivement.reducer';

export const BuyerTargetAchivementDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const buyerTargetAchivementEntity = useAppSelector(state => state.buyerTargetAchivement.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="buyerTargetAchivementDetailsHeading">
          <Translate contentKey="farmerBeApp.buyerTargetAchivement.detail.title">BuyerTargetAchivement</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{buyerTargetAchivementEntity.id}</dd>
          <dt>
            <span id="labour">
              <Translate contentKey="farmerBeApp.buyerTargetAchivement.labour">Labour</Translate>
            </span>
          </dt>
          <dd>{buyerTargetAchivementEntity.labour}</dd>
          <dt>
            <span id="farmVisit">
              <Translate contentKey="farmerBeApp.buyerTargetAchivement.farmVisit">Farm Visit</Translate>
            </span>
          </dt>
          <dd>{buyerTargetAchivementEntity.farmVisit}</dd>
          <dt>
            <span id="totalCollection">
              <Translate contentKey="farmerBeApp.buyerTargetAchivement.totalCollection">Total Collection</Translate>
            </span>
          </dt>
          <dd>{buyerTargetAchivementEntity.totalCollection}</dd>
          <dt>
            <span id="targetDate">
              <Translate contentKey="farmerBeApp.buyerTargetAchivement.targetDate">Target Date</Translate>
            </span>
          </dt>
          <dd>
            {buyerTargetAchivementEntity.targetDate ? (
              <TextFormat value={buyerTargetAchivementEntity.targetDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="attendenceHours">
              <Translate contentKey="farmerBeApp.buyerTargetAchivement.attendenceHours">Attendence Hours</Translate>
            </span>
          </dt>
          <dd>{buyerTargetAchivementEntity.attendenceHours}</dd>
          <dt>
            <span id="achivementLabour">
              <Translate contentKey="farmerBeApp.buyerTargetAchivement.achivementLabour">Achivement Labour</Translate>
            </span>
          </dt>
          <dd>{buyerTargetAchivementEntity.achivementLabour}</dd>
          <dt>
            <span id="achivementFarmVisit">
              <Translate contentKey="farmerBeApp.buyerTargetAchivement.achivementFarmVisit">Achivement Farm Visit</Translate>
            </span>
          </dt>
          <dd>{buyerTargetAchivementEntity.achivementFarmVisit}</dd>
          <dt>
            <span id="achivementTotalCollection">
              <Translate contentKey="farmerBeApp.buyerTargetAchivement.achivementTotalCollection">Achivement Total Collection</Translate>
            </span>
          </dt>
          <dd>{buyerTargetAchivementEntity.achivementTotalCollection}</dd>
          <dt>
            <span id="achivementAttendenceHours">
              <Translate contentKey="farmerBeApp.buyerTargetAchivement.achivementAttendenceHours">Achivement Attendence Hours</Translate>
            </span>
          </dt>
          <dd>{buyerTargetAchivementEntity.achivementAttendenceHours}</dd>
          <dt>
            <span id="achivementScore">
              <Translate contentKey="farmerBeApp.buyerTargetAchivement.achivementScore">Achivement Score</Translate>
            </span>
          </dt>
          <dd>{buyerTargetAchivementEntity.achivementScore}</dd>
          <dt>
            <span id="incentive">
              <Translate contentKey="farmerBeApp.buyerTargetAchivement.incentive">Incentive</Translate>
            </span>
          </dt>
          <dd>{buyerTargetAchivementEntity.incentive}</dd>
          <dt>
            <span id="kmDriven">
              <Translate contentKey="farmerBeApp.buyerTargetAchivement.kmDriven">Km Driven</Translate>
            </span>
          </dt>
          <dd>{buyerTargetAchivementEntity.kmDriven}</dd>
          <dt>
            <span id="conveyance">
              <Translate contentKey="farmerBeApp.buyerTargetAchivement.conveyance">Conveyance</Translate>
            </span>
          </dt>
          <dd>{buyerTargetAchivementEntity.conveyance}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="farmerBeApp.buyerTargetAchivement.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{buyerTargetAchivementEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="farmerBeApp.buyerTargetAchivement.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{buyerTargetAchivementEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="farmerBeApp.buyerTargetAchivement.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {buyerTargetAchivementEntity.createdTime ? (
              <TextFormat value={buyerTargetAchivementEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="farmerBeApp.buyerTargetAchivement.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{buyerTargetAchivementEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="farmerBeApp.buyerTargetAchivement.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {buyerTargetAchivementEntity.updatedTime ? (
              <TextFormat value={buyerTargetAchivementEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="farmerBeApp.buyerTargetAchivement.buyer">Buyer</Translate>
          </dt>
          <dd>{buyerTargetAchivementEntity.buyer ? buyerTargetAchivementEntity.buyer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/buyer-target-achivement" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/buyer-target-achivement/${buyerTargetAchivementEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BuyerTargetAchivementDetail;
