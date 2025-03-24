import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './session-context.reducer';

export const SessionContextDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const sessionContextEntity = useAppSelector(state => state.sessionContext.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sessionContextDetailsHeading">
          <Translate contentKey="farmerBeApp.sessionContext.detail.title">SessionContext</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{sessionContextEntity.id}</dd>
          <dt>
            <span id="sessionId">
              <Translate contentKey="farmerBeApp.sessionContext.sessionId">Session Id</Translate>
            </span>
          </dt>
          <dd>{sessionContextEntity.sessionId}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="farmerBeApp.sessionContext.language">Language</Translate>
            </span>
          </dt>
          <dd>{sessionContextEntity.language}</dd>
          <dt>
            <span id="isLoggedIn">
              <Translate contentKey="farmerBeApp.sessionContext.isLoggedIn">Is Logged In</Translate>
            </span>
          </dt>
          <dd>{sessionContextEntity.isLoggedIn ? 'true' : 'false'}</dd>
          <dt>
            <span id="farmerId">
              <Translate contentKey="farmerBeApp.sessionContext.farmerId">Farmer Id</Translate>
            </span>
          </dt>
          <dd>{sessionContextEntity.farmerId}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="farmerBeApp.sessionContext.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{sessionContextEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="farmerBeApp.sessionContext.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{sessionContextEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="farmerBeApp.sessionContext.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {sessionContextEntity.createdTime ? (
              <TextFormat value={sessionContextEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="farmerBeApp.sessionContext.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{sessionContextEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="farmerBeApp.sessionContext.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {sessionContextEntity.updatedTime ? (
              <TextFormat value={sessionContextEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/session-context" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/session-context/${sessionContextEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SessionContextDetail;
