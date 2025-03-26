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

import { getEntities, reset } from './field-visit.reducer';

export const FieldVisit = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const fieldVisitList = useAppSelector(state => state.fieldVisit.entities);
  const loading = useAppSelector(state => state.fieldVisit.loading);
  const links = useAppSelector(state => state.fieldVisit.links);
  const updateSuccess = useAppSelector(state => state.fieldVisit.updateSuccess);

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
      <h2 id="field-visit-heading" data-cy="FieldVisitHeading">
        <Translate contentKey="farmerBeApp.fieldVisit.home.title">Field Visits</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="farmerBeApp.fieldVisit.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/field-visit/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="farmerBeApp.fieldVisit.home.createLabel">Create new Field Visit</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={fieldVisitList ? fieldVisitList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {fieldVisitList && fieldVisitList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="farmerBeApp.fieldVisit.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('fieldVisitDate')}>
                    <Translate contentKey="farmerBeApp.fieldVisit.fieldVisitDate">Field Visit Date</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('fieldVisitDate')} />
                  </th>
                  <th className="hand" onClick={sort('fieldVisitTime')}>
                    <Translate contentKey="farmerBeApp.fieldVisit.fieldVisitTime">Field Visit Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('fieldVisitTime')} />
                  </th>
                  <th className="hand" onClick={sort('lat')}>
                    <Translate contentKey="farmerBeApp.fieldVisit.lat">Lat</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('lat')} />
                  </th>
                  <th className="hand" onClick={sort('lon')}>
                    <Translate contentKey="farmerBeApp.fieldVisit.lon">Lon</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('lon')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="farmerBeApp.fieldVisit.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('createddBy')}>
                    <Translate contentKey="farmerBeApp.fieldVisit.createddBy">Createdd By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createddBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdTime')}>
                    <Translate contentKey="farmerBeApp.fieldVisit.createdTime">Created Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdTime')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="farmerBeApp.fieldVisit.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedTime')}>
                    <Translate contentKey="farmerBeApp.fieldVisit.updatedTime">Updated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedTime')} />
                  </th>
                  <th>
                    <Translate contentKey="farmerBeApp.fieldVisit.buyer">Buyer</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="farmerBeApp.fieldVisit.farm">Farm</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {fieldVisitList.map((fieldVisit, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/field-visit/${fieldVisit.id}`} color="link" size="sm">
                        {fieldVisit.id}
                      </Button>
                    </td>
                    <td>
                      {fieldVisit.fieldVisitDate ? (
                        <TextFormat type="date" value={fieldVisit.fieldVisitDate} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {fieldVisit.fieldVisitTime ? (
                        <TextFormat type="date" value={fieldVisit.fieldVisitTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{fieldVisit.lat}</td>
                    <td>{fieldVisit.lon}</td>
                    <td>{fieldVisit.isActive ? 'true' : 'false'}</td>
                    <td>{fieldVisit.createddBy}</td>
                    <td>
                      {fieldVisit.createdTime ? <TextFormat type="date" value={fieldVisit.createdTime} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>{fieldVisit.updatedBy}</td>
                    <td>
                      {fieldVisit.updatedTime ? <TextFormat type="date" value={fieldVisit.updatedTime} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>{fieldVisit.buyer ? <Link to={`/buyer/${fieldVisit.buyer.id}`}>{fieldVisit.buyer.id}</Link> : ''}</td>
                    <td>{fieldVisit.farm ? <Link to={`/farm/${fieldVisit.farm.id}`}>{fieldVisit.farm.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/field-visit/${fieldVisit.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/field-visit/${fieldVisit.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (window.location.href = `/field-visit/${fieldVisit.id}/delete`)}
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
                <Translate contentKey="farmerBeApp.fieldVisit.home.notFound">No Field Visits found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default FieldVisit;
