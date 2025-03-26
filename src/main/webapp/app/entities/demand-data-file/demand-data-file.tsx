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

import { getEntities, reset } from './demand-data-file.reducer';

export const DemandDataFile = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const demandDataFileList = useAppSelector(state => state.demandDataFile.entities);
  const loading = useAppSelector(state => state.demandDataFile.loading);
  const links = useAppSelector(state => state.demandDataFile.links);
  const updateSuccess = useAppSelector(state => state.demandDataFile.updateSuccess);

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
      <h2 id="demand-data-file-heading" data-cy="DemandDataFileHeading">
        <Translate contentKey="farmerBeApp.demandDataFile.home.title">Demand Data Files</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="farmerBeApp.demandDataFile.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/demand-data-file/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="farmerBeApp.demandDataFile.home.createLabel">Create new Demand Data File</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={demandDataFileList ? demandDataFileList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {demandDataFileList && demandDataFileList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="farmerBeApp.demandDataFile.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('uploadedDate')}>
                    <Translate contentKey="farmerBeApp.demandDataFile.uploadedDate">Uploaded Date</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('uploadedDate')} />
                  </th>
                  <th className="hand" onClick={sort('uploadedTime')}>
                    <Translate contentKey="farmerBeApp.demandDataFile.uploadedTime">Uploaded Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('uploadedTime')} />
                  </th>
                  <th className="hand" onClick={sort('fileName')}>
                    <Translate contentKey="farmerBeApp.demandDataFile.fileName">File Name</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('fileName')} />
                  </th>
                  <th className="hand" onClick={sort('uploadedBy')}>
                    <Translate contentKey="farmerBeApp.demandDataFile.uploadedBy">Uploaded By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('uploadedBy')} />
                  </th>
                  <th className="hand" onClick={sort('status')}>
                    <Translate contentKey="farmerBeApp.demandDataFile.status">Status</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="farmerBeApp.demandDataFile.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('createddBy')}>
                    <Translate contentKey="farmerBeApp.demandDataFile.createddBy">Createdd By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createddBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdTime')}>
                    <Translate contentKey="farmerBeApp.demandDataFile.createdTime">Created Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdTime')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="farmerBeApp.demandDataFile.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedTime')}>
                    <Translate contentKey="farmerBeApp.demandDataFile.updatedTime">Updated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedTime')} />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {demandDataFileList.map((demandDataFile, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/demand-data-file/${demandDataFile.id}`} color="link" size="sm">
                        {demandDataFile.id}
                      </Button>
                    </td>
                    <td>
                      {demandDataFile.uploadedDate ? (
                        <TextFormat type="date" value={demandDataFile.uploadedDate} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {demandDataFile.uploadedTime ? (
                        <TextFormat type="date" value={demandDataFile.uploadedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{demandDataFile.fileName}</td>
                    <td>{demandDataFile.uploadedBy}</td>
                    <td>
                      <Translate contentKey={`farmerBeApp.UploadStatus.${demandDataFile.status}`} />
                    </td>
                    <td>{demandDataFile.isActive ? 'true' : 'false'}</td>
                    <td>{demandDataFile.createddBy}</td>
                    <td>
                      {demandDataFile.createdTime ? (
                        <TextFormat type="date" value={demandDataFile.createdTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{demandDataFile.updatedBy}</td>
                    <td>
                      {demandDataFile.updatedTime ? (
                        <TextFormat type="date" value={demandDataFile.updatedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/demand-data-file/${demandDataFile.id}`}
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
                          to={`/demand-data-file/${demandDataFile.id}/edit`}
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
                          onClick={() => (window.location.href = `/demand-data-file/${demandDataFile.id}/delete`)}
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
                <Translate contentKey="farmerBeApp.demandDataFile.home.notFound">No Demand Data Files found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default DemandDataFile;
