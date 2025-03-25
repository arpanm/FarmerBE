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

import { getEntities, reset } from './hervest-plan.reducer';

export const HervestPlan = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const hervestPlanList = useAppSelector(state => state.hervestPlan.entities);
  const loading = useAppSelector(state => state.hervestPlan.loading);
  const links = useAppSelector(state => state.hervestPlan.links);
  const updateSuccess = useAppSelector(state => state.hervestPlan.updateSuccess);

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
      <h2 id="hervest-plan-heading" data-cy="HervestPlanHeading">
        <Translate contentKey="farmerBeApp.hervestPlan.home.title">Hervest Plans</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="farmerBeApp.hervestPlan.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/hervest-plan/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="farmerBeApp.hervestPlan.home.createLabel">Create new Hervest Plan</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={hervestPlanList ? hervestPlanList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {hervestPlanList && hervestPlanList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="farmerBeApp.hervestPlan.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('hervestPlanDate')}>
                    <Translate contentKey="farmerBeApp.hervestPlan.hervestPlanDate">Hervest Plan Date</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('hervestPlanDate')} />
                  </th>
                  <th className="hand" onClick={sort('hervestPlanValue')}>
                    <Translate contentKey="farmerBeApp.hervestPlan.hervestPlanValue">Hervest Plan Value</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('hervestPlanValue')} />
                  </th>
                  <th className="hand" onClick={sort('hervestPlanValueMin')}>
                    <Translate contentKey="farmerBeApp.hervestPlan.hervestPlanValueMin">Hervest Plan Value Min</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('hervestPlanValueMin')} />
                  </th>
                  <th className="hand" onClick={sort('hervestPlanValueMax')}>
                    <Translate contentKey="farmerBeApp.hervestPlan.hervestPlanValueMax">Hervest Plan Value Max</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('hervestPlanValueMax')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="farmerBeApp.hervestPlan.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('createddBy')}>
                    <Translate contentKey="farmerBeApp.hervestPlan.createddBy">Createdd By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createddBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdTime')}>
                    <Translate contentKey="farmerBeApp.hervestPlan.createdTime">Created Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdTime')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="farmerBeApp.hervestPlan.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedTime')}>
                    <Translate contentKey="farmerBeApp.hervestPlan.updatedTime">Updated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedTime')} />
                  </th>
                  <th>
                    <Translate contentKey="farmerBeApp.hervestPlan.farm">Farm</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="farmerBeApp.hervestPlan.crop">Crop</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {hervestPlanList.map((hervestPlan, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/hervest-plan/${hervestPlan.id}`} color="link" size="sm">
                        {hervestPlan.id}
                      </Button>
                    </td>
                    <td>
                      {hervestPlan.hervestPlanDate ? (
                        <TextFormat type="date" value={hervestPlan.hervestPlanDate} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{hervestPlan.hervestPlanValue}</td>
                    <td>{hervestPlan.hervestPlanValueMin}</td>
                    <td>{hervestPlan.hervestPlanValueMax}</td>
                    <td>{hervestPlan.isActive ? 'true' : 'false'}</td>
                    <td>{hervestPlan.createddBy}</td>
                    <td>
                      {hervestPlan.createdTime ? <TextFormat type="date" value={hervestPlan.createdTime} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>{hervestPlan.updatedBy}</td>
                    <td>
                      {hervestPlan.updatedTime ? <TextFormat type="date" value={hervestPlan.updatedTime} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>{hervestPlan.farm ? <Link to={`/farm/${hervestPlan.farm.id}`}>{hervestPlan.farm.id}</Link> : ''}</td>
                    <td>{hervestPlan.crop ? <Link to={`/crop/${hervestPlan.crop.id}`}>{hervestPlan.crop.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/hervest-plan/${hervestPlan.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/hervest-plan/${hervestPlan.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (window.location.href = `/hervest-plan/${hervestPlan.id}/delete`)}
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
                <Translate contentKey="farmerBeApp.hervestPlan.home.notFound">No Hervest Plans found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default HervestPlan;
