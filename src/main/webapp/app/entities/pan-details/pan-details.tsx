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

import { getEntities, reset } from './pan-details.reducer';

export const PanDetails = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const panDetailsList = useAppSelector(state => state.panDetails.entities);
  const loading = useAppSelector(state => state.panDetails.loading);
  const links = useAppSelector(state => state.panDetails.links);
  const updateSuccess = useAppSelector(state => state.panDetails.updateSuccess);

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
      <h2 id="pan-details-heading" data-cy="PanDetailsHeading">
        <Translate contentKey="farmerBeApp.panDetails.home.title">Pan Details</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="farmerBeApp.panDetails.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/pan-details/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="farmerBeApp.panDetails.home.createLabel">Create new Pan Details</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={panDetailsList ? panDetailsList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {panDetailsList && panDetailsList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="farmerBeApp.panDetails.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('pan')}>
                    <Translate contentKey="farmerBeApp.panDetails.pan">Pan</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('pan')} />
                  </th>
                  <th className="hand" onClick={sort('name')}>
                    <Translate contentKey="farmerBeApp.panDetails.name">Name</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                  </th>
                  <th className="hand" onClick={sort('dateOfBirth')}>
                    <Translate contentKey="farmerBeApp.panDetails.dateOfBirth">Date Of Birth</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('dateOfBirth')} />
                  </th>
                  <th className="hand" onClick={sort('gender')}>
                    <Translate contentKey="farmerBeApp.panDetails.gender">Gender</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('gender')} />
                  </th>
                  <th className="hand" onClick={sort('country')}>
                    <Translate contentKey="farmerBeApp.panDetails.country">Country</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('country')} />
                  </th>
                  <th className="hand" onClick={sort('isVerified')}>
                    <Translate contentKey="farmerBeApp.panDetails.isVerified">Is Verified</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isVerified')} />
                  </th>
                  <th className="hand" onClick={sort('verificationTime')}>
                    <Translate contentKey="farmerBeApp.panDetails.verificationTime">Verification Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('verificationTime')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="farmerBeApp.panDetails.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('createddBy')}>
                    <Translate contentKey="farmerBeApp.panDetails.createddBy">Createdd By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createddBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdTime')}>
                    <Translate contentKey="farmerBeApp.panDetails.createdTime">Created Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdTime')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="farmerBeApp.panDetails.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedTime')}>
                    <Translate contentKey="farmerBeApp.panDetails.updatedTime">Updated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedTime')} />
                  </th>
                  <th>
                    <Translate contentKey="farmerBeApp.panDetails.farmer">Farmer</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {panDetailsList.map((panDetails, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/pan-details/${panDetails.id}`} color="link" size="sm">
                        {panDetails.id}
                      </Button>
                    </td>
                    <td>{panDetails.pan}</td>
                    <td>{panDetails.name}</td>
                    <td>
                      {panDetails.dateOfBirth ? (
                        <TextFormat type="date" value={panDetails.dateOfBirth} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      <Translate contentKey={`farmerBeApp.Gender.${panDetails.gender}`} />
                    </td>
                    <td>{panDetails.country}</td>
                    <td>{panDetails.isVerified ? 'true' : 'false'}</td>
                    <td>
                      {panDetails.verificationTime ? (
                        <TextFormat type="date" value={panDetails.verificationTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{panDetails.isActive ? 'true' : 'false'}</td>
                    <td>{panDetails.createddBy}</td>
                    <td>
                      {panDetails.createdTime ? <TextFormat type="date" value={panDetails.createdTime} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>{panDetails.updatedBy}</td>
                    <td>
                      {panDetails.updatedTime ? <TextFormat type="date" value={panDetails.updatedTime} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>{panDetails.farmer ? <Link to={`/farmer/${panDetails.farmer.id}`}>{panDetails.farmer.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/pan-details/${panDetails.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/pan-details/${panDetails.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (window.location.href = `/pan-details/${panDetails.id}/delete`)}
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
                <Translate contentKey="farmerBeApp.panDetails.home.notFound">No Pan Details found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default PanDetails;
