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

import { getEntities, reset } from './hervest-plan-rule.reducer';

export const HervestPlanRule = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const hervestPlanRuleList = useAppSelector(state => state.hervestPlanRule.entities);
  const loading = useAppSelector(state => state.hervestPlanRule.loading);
  const links = useAppSelector(state => state.hervestPlanRule.links);
  const updateSuccess = useAppSelector(state => state.hervestPlanRule.updateSuccess);

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
      <h2 id="hervest-plan-rule-heading" data-cy="HervestPlanRuleHeading">
        <Translate contentKey="farmerBeApp.hervestPlanRule.home.title">Hervest Plan Rules</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="farmerBeApp.hervestPlanRule.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/hervest-plan-rule/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="farmerBeApp.hervestPlanRule.home.createLabel">Create new Hervest Plan Rule</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={hervestPlanRuleList ? hervestPlanRuleList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {hervestPlanRuleList && hervestPlanRuleList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="farmerBeApp.hervestPlanRule.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('frequencyType')}>
                    <Translate contentKey="farmerBeApp.hervestPlanRule.frequencyType">Frequency Type</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('frequencyType')} />
                  </th>
                  <th className="hand" onClick={sort('hervestPlanValue')}>
                    <Translate contentKey="farmerBeApp.hervestPlanRule.hervestPlanValue">Hervest Plan Value</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('hervestPlanValue')} />
                  </th>
                  <th className="hand" onClick={sort('hervestPlanValueMin')}>
                    <Translate contentKey="farmerBeApp.hervestPlanRule.hervestPlanValueMin">Hervest Plan Value Min</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('hervestPlanValueMin')} />
                  </th>
                  <th className="hand" onClick={sort('hervestPlanValueMax')}>
                    <Translate contentKey="farmerBeApp.hervestPlanRule.hervestPlanValueMax">Hervest Plan Value Max</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('hervestPlanValueMax')} />
                  </th>
                  <th className="hand" onClick={sort('daysOfWeek')}>
                    <Translate contentKey="farmerBeApp.hervestPlanRule.daysOfWeek">Days Of Week</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('daysOfWeek')} />
                  </th>
                  <th className="hand" onClick={sort('daysOfMonth')}>
                    <Translate contentKey="farmerBeApp.hervestPlanRule.daysOfMonth">Days Of Month</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('daysOfMonth')} />
                  </th>
                  <th className="hand" onClick={sort('daysOfYear')}>
                    <Translate contentKey="farmerBeApp.hervestPlanRule.daysOfYear">Days Of Year</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('daysOfYear')} />
                  </th>
                  <th className="hand" onClick={sort('alternateXdays')}>
                    <Translate contentKey="farmerBeApp.hervestPlanRule.alternateXdays">Alternate Xdays</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('alternateXdays')} />
                  </th>
                  <th className="hand" onClick={sort('startDate')}>
                    <Translate contentKey="farmerBeApp.hervestPlanRule.startDate">Start Date</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('startDate')} />
                  </th>
                  <th className="hand" onClick={sort('hasEndDate')}>
                    <Translate contentKey="farmerBeApp.hervestPlanRule.hasEndDate">Has End Date</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('hasEndDate')} />
                  </th>
                  <th className="hand" onClick={sort('endDate')}>
                    <Translate contentKey="farmerBeApp.hervestPlanRule.endDate">End Date</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('endDate')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="farmerBeApp.hervestPlanRule.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('createddBy')}>
                    <Translate contentKey="farmerBeApp.hervestPlanRule.createddBy">Createdd By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createddBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdTime')}>
                    <Translate contentKey="farmerBeApp.hervestPlanRule.createdTime">Created Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdTime')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="farmerBeApp.hervestPlanRule.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedTime')}>
                    <Translate contentKey="farmerBeApp.hervestPlanRule.updatedTime">Updated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedTime')} />
                  </th>
                  <th>
                    <Translate contentKey="farmerBeApp.hervestPlanRule.farm">Farm</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="farmerBeApp.hervestPlanRule.crop">Crop</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {hervestPlanRuleList.map((hervestPlanRule, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/hervest-plan-rule/${hervestPlanRule.id}`} color="link" size="sm">
                        {hervestPlanRule.id}
                      </Button>
                    </td>
                    <td>
                      <Translate contentKey={`farmerBeApp.FrequencyType.${hervestPlanRule.frequencyType}`} />
                    </td>
                    <td>{hervestPlanRule.hervestPlanValue}</td>
                    <td>{hervestPlanRule.hervestPlanValueMin}</td>
                    <td>{hervestPlanRule.hervestPlanValueMax}</td>
                    <td>{hervestPlanRule.daysOfWeek}</td>
                    <td>{hervestPlanRule.daysOfMonth}</td>
                    <td>{hervestPlanRule.daysOfYear}</td>
                    <td>{hervestPlanRule.alternateXdays}</td>
                    <td>
                      {hervestPlanRule.startDate ? (
                        <TextFormat type="date" value={hervestPlanRule.startDate} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{hervestPlanRule.hasEndDate ? 'true' : 'false'}</td>
                    <td>
                      {hervestPlanRule.endDate ? (
                        <TextFormat type="date" value={hervestPlanRule.endDate} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{hervestPlanRule.isActive ? 'true' : 'false'}</td>
                    <td>{hervestPlanRule.createddBy}</td>
                    <td>
                      {hervestPlanRule.createdTime ? (
                        <TextFormat type="date" value={hervestPlanRule.createdTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{hervestPlanRule.updatedBy}</td>
                    <td>
                      {hervestPlanRule.updatedTime ? (
                        <TextFormat type="date" value={hervestPlanRule.updatedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{hervestPlanRule.farm ? <Link to={`/farm/${hervestPlanRule.farm.id}`}>{hervestPlanRule.farm.id}</Link> : ''}</td>
                    <td>{hervestPlanRule.crop ? <Link to={`/crop/${hervestPlanRule.crop.id}`}>{hervestPlanRule.crop.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/hervest-plan-rule/${hervestPlanRule.id}`}
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
                          to={`/hervest-plan-rule/${hervestPlanRule.id}/edit`}
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
                          onClick={() => (window.location.href = `/hervest-plan-rule/${hervestPlanRule.id}/delete`)}
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
                <Translate contentKey="farmerBeApp.hervestPlanRule.home.notFound">No Hervest Plan Rules found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default HervestPlanRule;
