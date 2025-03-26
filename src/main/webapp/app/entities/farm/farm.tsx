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

import { getEntities, reset } from './farm.reducer';

export const Farm = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const farmList = useAppSelector(state => state.farm.entities);
  const loading = useAppSelector(state => state.farm.loading);
  const links = useAppSelector(state => state.farm.links);
  const updateSuccess = useAppSelector(state => state.farm.updateSuccess);

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
      <h2 id="farm-heading" data-cy="FarmHeading">
        <Translate contentKey="farmerBeApp.farm.home.title">Farms</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="farmerBeApp.farm.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/farm/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="farmerBeApp.farm.home.createLabel">Create new Farm</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={farmList ? farmList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {farmList && farmList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="farmerBeApp.farm.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('farmType')}>
                    <Translate contentKey="farmerBeApp.farm.farmType">Farm Type</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('farmType')} />
                  </th>
                  <th className="hand" onClick={sort('ownerName')}>
                    <Translate contentKey="farmerBeApp.farm.ownerName">Owner Name</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('ownerName')} />
                  </th>
                  <th className="hand" onClick={sort('relationshipWithOwner')}>
                    <Translate contentKey="farmerBeApp.farm.relationshipWithOwner">Relationship With Owner</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('relationshipWithOwner')} />
                  </th>
                  <th className="hand" onClick={sort('areaInAcres')}>
                    <Translate contentKey="farmerBeApp.farm.areaInAcres">Area In Acres</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('areaInAcres')} />
                  </th>
                  <th className="hand" onClick={sort('farmDocumentNo')}>
                    <Translate contentKey="farmerBeApp.farm.farmDocumentNo">Farm Document No</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('farmDocumentNo')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="farmerBeApp.farm.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('createddBy')}>
                    <Translate contentKey="farmerBeApp.farm.createddBy">Createdd By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createddBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdTime')}>
                    <Translate contentKey="farmerBeApp.farm.createdTime">Created Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdTime')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="farmerBeApp.farm.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedTime')}>
                    <Translate contentKey="farmerBeApp.farm.updatedTime">Updated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedTime')} />
                  </th>
                  <th>
                    <Translate contentKey="farmerBeApp.farm.collectionCenter">Collection Center</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="farmerBeApp.farm.farmer">Farmer</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {farmList.map((farm, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/farm/${farm.id}`} color="link" size="sm">
                        {farm.id}
                      </Button>
                    </td>
                    <td>
                      <Translate contentKey={`farmerBeApp.FarmType.${farm.farmType}`} />
                    </td>
                    <td>{farm.ownerName}</td>
                    <td>{farm.relationshipWithOwner}</td>
                    <td>{farm.areaInAcres}</td>
                    <td>{farm.farmDocumentNo}</td>
                    <td>{farm.isActive ? 'true' : 'false'}</td>
                    <td>{farm.createddBy}</td>
                    <td>{farm.createdTime ? <TextFormat type="date" value={farm.createdTime} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{farm.updatedBy}</td>
                    <td>{farm.updatedTime ? <TextFormat type="date" value={farm.updatedTime} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>
                      {farm.collectionCenter ? (
                        <Link to={`/collection-center/${farm.collectionCenter.id}`}>{farm.collectionCenter.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>{farm.farmer ? <Link to={`/farmer/${farm.farmer.id}`}>{farm.farmer.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/farm/${farm.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/farm/${farm.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (window.location.href = `/farm/${farm.id}/delete`)}
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
                <Translate contentKey="farmerBeApp.farm.home.notFound">No Farms found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Farm;
