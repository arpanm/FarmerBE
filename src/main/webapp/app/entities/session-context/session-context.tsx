import React, { useEffect, useState } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { TextFormat, Translate, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities, reset } from './session-context.reducer';

export const SessionContext = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const sessionContextList = useAppSelector(state => state.sessionContext.entities);
  const loading = useAppSelector(state => state.sessionContext.loading);
  const links = useAppSelector(state => state.sessionContext.links);
  const updateSuccess = useAppSelector(state => state.sessionContext.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    }
    return order === ASC ? faSortUp : faSortDown;
  };

  return (
    <div>
      <h2 id="session-context-heading" data-cy="SessionContextHeading">
        <Translate contentKey="farmerBeApp.sessionContext.home.title">Session Contexts</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="farmerBeApp.sessionContext.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/session-context/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="farmerBeApp.sessionContext.home.createLabel">Create new Session Context</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={sessionContextList ? sessionContextList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {sessionContextList && sessionContextList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="farmerBeApp.sessionContext.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('sessionId')}>
                    <Translate contentKey="farmerBeApp.sessionContext.sessionId">Session Id</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('sessionId')} />
                  </th>
                  <th className="hand" onClick={sort('language')}>
                    <Translate contentKey="farmerBeApp.sessionContext.language">Language</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('language')} />
                  </th>
                  <th className="hand" onClick={sort('isLoggedIn')}>
                    <Translate contentKey="farmerBeApp.sessionContext.isLoggedIn">Is Logged In</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isLoggedIn')} />
                  </th>
                  <th className="hand" onClick={sort('farmerId')}>
                    <Translate contentKey="farmerBeApp.sessionContext.farmerId">Farmer Id</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('farmerId')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="farmerBeApp.sessionContext.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('createddBy')}>
                    <Translate contentKey="farmerBeApp.sessionContext.createddBy">Createdd By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createddBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdTime')}>
                    <Translate contentKey="farmerBeApp.sessionContext.createdTime">Created Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdTime')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="farmerBeApp.sessionContext.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedTime')}>
                    <Translate contentKey="farmerBeApp.sessionContext.updatedTime">Updated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedTime')} />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {sessionContextList.map((sessionContext, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/session-context/${sessionContext.id}`} color="link" size="sm">
                        {sessionContext.id}
                      </Button>
                    </td>
                    <td>{sessionContext.sessionId}</td>
                    <td>
                      <Translate contentKey={`farmerBeApp.Language.${sessionContext.language}`} />
                    </td>
                    <td>{sessionContext.isLoggedIn ? 'true' : 'false'}</td>
                    <td>{sessionContext.farmerId}</td>
                    <td>{sessionContext.isActive ? 'true' : 'false'}</td>
                    <td>{sessionContext.createddBy}</td>
                    <td>
                      {sessionContext.createdTime ? (
                        <TextFormat type="date" value={sessionContext.createdTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{sessionContext.updatedBy}</td>
                    <td>
                      {sessionContext.updatedTime ? (
                        <TextFormat type="date" value={sessionContext.updatedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/session-context/${sessionContext.id}`}
                          color="info"
                          size="sm"
                          data-cy="entityDetailsButton"
                        >
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/session-context/${sessionContext.id}/edit`}
                          color="primary"
                          size="sm"
                          data-cy="entityEditButton"
                        >
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (window.location.href = `/session-context/${sessionContext.id}/delete`)}
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                        >
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="farmerBeApp.sessionContext.home.notFound">No Session Contexts found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default SessionContext;
