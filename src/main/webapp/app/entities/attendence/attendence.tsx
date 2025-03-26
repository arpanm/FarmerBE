import React, { useEffect, useState } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { TextFormat, Translate, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities, reset } from './attendence.reducer';

export const Attendence = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const attendenceList = useAppSelector(state => state.attendence.entities);
  const loading = useAppSelector(state => state.attendence.loading);
  const links = useAppSelector(state => state.attendence.links);
  const updateSuccess = useAppSelector(state => state.attendence.updateSuccess);

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
      <h2 id="attendence-heading" data-cy="AttendenceHeading">
        <Translate contentKey="farmerBeApp.attendence.home.title">Attendences</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="farmerBeApp.attendence.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/attendence/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="farmerBeApp.attendence.home.createLabel">Create new Attendence</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={attendenceList ? attendenceList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {attendenceList && attendenceList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="farmerBeApp.attendence.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('attendenceType')}>
                    <Translate contentKey="farmerBeApp.attendence.attendenceType">Attendence Type</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('attendenceType')} />
                  </th>
                  <th className="hand" onClick={sort('attendenceDate')}>
                    <Translate contentKey="farmerBeApp.attendence.attendenceDate">Attendence Date</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('attendenceDate')} />
                  </th>
                  <th className="hand" onClick={sort('attendenceTime')}>
                    <Translate contentKey="farmerBeApp.attendence.attendenceTime">Attendence Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('attendenceTime')} />
                  </th>
                  <th className="hand" onClick={sort('lat')}>
                    <Translate contentKey="farmerBeApp.attendence.lat">Lat</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('lat')} />
                  </th>
                  <th className="hand" onClick={sort('lon')}>
                    <Translate contentKey="farmerBeApp.attendence.lon">Lon</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('lon')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="farmerBeApp.attendence.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('createddBy')}>
                    <Translate contentKey="farmerBeApp.attendence.createddBy">Createdd By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createddBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdTime')}>
                    <Translate contentKey="farmerBeApp.attendence.createdTime">Created Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdTime')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="farmerBeApp.attendence.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedTime')}>
                    <Translate contentKey="farmerBeApp.attendence.updatedTime">Updated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedTime')} />
                  </th>
                  <th>
                    <Translate contentKey="farmerBeApp.attendence.buyer">Buyer</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {attendenceList.map((attendence, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/attendence/${attendence.id}`} color="link" size="sm">
                        {attendence.id}
                      </Button>
                    </td>
                    <td>
                      <Translate contentKey={`farmerBeApp.AttendenceType.${attendence.attendenceType}`} />
                    </td>
                    <td>
                      {attendence.attendenceDate ? (
                        <TextFormat type="date" value={attendence.attendenceDate} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {attendence.attendenceTime ? (
                        <TextFormat type="date" value={attendence.attendenceTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{attendence.lat}</td>
                    <td>{attendence.lon}</td>
                    <td>{attendence.isActive ? 'true' : 'false'}</td>
                    <td>{attendence.createddBy}</td>
                    <td>
                      {attendence.createdTime ? <TextFormat type="date" value={attendence.createdTime} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>{attendence.updatedBy}</td>
                    <td>
                      {attendence.updatedTime ? <TextFormat type="date" value={attendence.updatedTime} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>{attendence.buyer ? <Link to={`/buyer/${attendence.buyer.id}`}>{attendence.buyer.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/attendence/${attendence.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/attendence/${attendence.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (window.location.href = `/attendence/${attendence.id}/delete`)}
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
                <Translate contentKey="farmerBeApp.attendence.home.notFound">No Attendences found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Attendence;
