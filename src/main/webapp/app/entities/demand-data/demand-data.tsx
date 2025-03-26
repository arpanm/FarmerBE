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

import { getEntities, reset } from './demand-data.reducer';

export const DemandData = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const demandDataList = useAppSelector(state => state.demandData.entities);
  const loading = useAppSelector(state => state.demandData.loading);
  const links = useAppSelector(state => state.demandData.links);
  const updateSuccess = useAppSelector(state => state.demandData.updateSuccess);

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
      <h2 id="demand-data-heading" data-cy="DemandDataHeading">
        <Translate contentKey="farmerBeApp.demandData.home.title">Demand Data</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="farmerBeApp.demandData.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/demand-data/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="farmerBeApp.demandData.home.createLabel">Create new Demand Data</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={demandDataList ? demandDataList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {demandDataList && demandDataList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="farmerBeApp.demandData.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('fromCPC')}>
                    <Translate contentKey="farmerBeApp.demandData.fromCPC">From CPC</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('fromCPC')} />
                  </th>
                  <th className="hand" onClick={sort('toCC')}>
                    <Translate contentKey="farmerBeApp.demandData.toCC">To CC</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('toCC')} />
                  </th>
                  <th className="hand" onClick={sort('pCode')}>
                    <Translate contentKey="farmerBeApp.demandData.pCode">P Code</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('pCode')} />
                  </th>
                  <th className="hand" onClick={sort('article')}>
                    <Translate contentKey="farmerBeApp.demandData.article">Article</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('article')} />
                  </th>
                  <th className="hand" onClick={sort('description')}>
                    <Translate contentKey="farmerBeApp.demandData.description">Description</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                  </th>
                  <th className="hand" onClick={sort('uom')}>
                    <Translate contentKey="farmerBeApp.demandData.uom">Uom</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('uom')} />
                  </th>
                  <th className="hand" onClick={sort('netWeightGrams')}>
                    <Translate contentKey="farmerBeApp.demandData.netWeightGrams">Net Weight Grams</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('netWeightGrams')} />
                  </th>
                  <th className="hand" onClick={sort('crateSize')}>
                    <Translate contentKey="farmerBeApp.demandData.crateSize">Crate Size</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('crateSize')} />
                  </th>
                  <th className="hand" onClick={sort('indentUom')}>
                    <Translate contentKey="farmerBeApp.demandData.indentUom">Indent Uom</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('indentUom')} />
                  </th>
                  <th className="hand" onClick={sort('indentKg')}>
                    <Translate contentKey="farmerBeApp.demandData.indentKg">Indent Kg</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('indentKg')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="farmerBeApp.demandData.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('createddBy')}>
                    <Translate contentKey="farmerBeApp.demandData.createddBy">Createdd By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createddBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdTime')}>
                    <Translate contentKey="farmerBeApp.demandData.createdTime">Created Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdTime')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="farmerBeApp.demandData.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedTime')}>
                    <Translate contentKey="farmerBeApp.demandData.updatedTime">Updated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedTime')} />
                  </th>
                  <th>
                    <Translate contentKey="farmerBeApp.demandData.crop">Crop</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="farmerBeApp.demandData.collectionCenter">Collection Center</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="farmerBeApp.demandData.file">File</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {demandDataList.map((demandData, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/demand-data/${demandData.id}`} color="link" size="sm">
                        {demandData.id}
                      </Button>
                    </td>
                    <td>{demandData.fromCPC}</td>
                    <td>{demandData.toCC}</td>
                    <td>{demandData.pCode}</td>
                    <td>{demandData.article}</td>
                    <td>{demandData.description}</td>
                    <td>{demandData.uom}</td>
                    <td>{demandData.netWeightGrams}</td>
                    <td>{demandData.crateSize}</td>
                    <td>{demandData.indentUom}</td>
                    <td>{demandData.indentKg}</td>
                    <td>{demandData.isActive ? 'true' : 'false'}</td>
                    <td>{demandData.createddBy}</td>
                    <td>
                      {demandData.createdTime ? <TextFormat type="date" value={demandData.createdTime} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>{demandData.updatedBy}</td>
                    <td>
                      {demandData.updatedTime ? <TextFormat type="date" value={demandData.updatedTime} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>{demandData.crop ? <Link to={`/crop/${demandData.crop.id}`}>{demandData.crop.id}</Link> : ''}</td>
                    <td>
                      {demandData.collectionCenter ? (
                        <Link to={`/collection-center/${demandData.collectionCenter.id}`}>{demandData.collectionCenter.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>{demandData.file ? <Link to={`/demand-data-file/${demandData.file.id}`}>{demandData.file.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/demand-data/${demandData.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/demand-data/${demandData.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (window.location.href = `/demand-data/${demandData.id}/delete`)}
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
                <Translate contentKey="farmerBeApp.demandData.home.notFound">No Demand Data found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default DemandData;
