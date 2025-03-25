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

import { getEntities, reset } from './pickup-payment.reducer';

export const PickupPayment = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const pickupPaymentList = useAppSelector(state => state.pickupPayment.entities);
  const loading = useAppSelector(state => state.pickupPayment.loading);
  const links = useAppSelector(state => state.pickupPayment.links);
  const updateSuccess = useAppSelector(state => state.pickupPayment.updateSuccess);

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
      <h2 id="pickup-payment-heading" data-cy="PickupPaymentHeading">
        <Translate contentKey="farmerBeApp.pickupPayment.home.title">Pickup Payments</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="farmerBeApp.pickupPayment.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/pickup-payment/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="farmerBeApp.pickupPayment.home.createLabel">Create new Pickup Payment</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={pickupPaymentList ? pickupPaymentList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {pickupPaymentList && pickupPaymentList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="farmerBeApp.pickupPayment.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('paymentStatus')}>
                    <Translate contentKey="farmerBeApp.pickupPayment.paymentStatus">Payment Status</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('paymentStatus')} />
                  </th>
                  <th className="hand" onClick={sort('transactionId')}>
                    <Translate contentKey="farmerBeApp.pickupPayment.transactionId">Transaction Id</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('transactionId')} />
                  </th>
                  <th className="hand" onClick={sort('paymentDate')}>
                    <Translate contentKey="farmerBeApp.pickupPayment.paymentDate">Payment Date</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('paymentDate')} />
                  </th>
                  <th className="hand" onClick={sort('details')}>
                    <Translate contentKey="farmerBeApp.pickupPayment.details">Details</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('details')} />
                  </th>
                  <th className="hand" onClick={sort('paymentUpdatedBy')}>
                    <Translate contentKey="farmerBeApp.pickupPayment.paymentUpdatedBy">Payment Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('paymentUpdatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('paymentUpdatedTime')}>
                    <Translate contentKey="farmerBeApp.pickupPayment.paymentUpdatedTime">Payment Updated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('paymentUpdatedTime')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="farmerBeApp.pickupPayment.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('createddBy')}>
                    <Translate contentKey="farmerBeApp.pickupPayment.createddBy">Createdd By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createddBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdTime')}>
                    <Translate contentKey="farmerBeApp.pickupPayment.createdTime">Created Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdTime')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="farmerBeApp.pickupPayment.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedTime')}>
                    <Translate contentKey="farmerBeApp.pickupPayment.updatedTime">Updated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedTime')} />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {pickupPaymentList.map((pickupPayment, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/pickup-payment/${pickupPayment.id}`} color="link" size="sm">
                        {pickupPayment.id}
                      </Button>
                    </td>
                    <td>
                      <Translate contentKey={`farmerBeApp.PaymentStatus.${pickupPayment.paymentStatus}`} />
                    </td>
                    <td>{pickupPayment.transactionId}</td>
                    <td>
                      {pickupPayment.paymentDate ? (
                        <TextFormat type="date" value={pickupPayment.paymentDate} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{pickupPayment.details}</td>
                    <td>{pickupPayment.paymentUpdatedBy}</td>
                    <td>
                      {pickupPayment.paymentUpdatedTime ? (
                        <TextFormat type="date" value={pickupPayment.paymentUpdatedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{pickupPayment.isActive ? 'true' : 'false'}</td>
                    <td>{pickupPayment.createddBy}</td>
                    <td>
                      {pickupPayment.createdTime ? (
                        <TextFormat type="date" value={pickupPayment.createdTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{pickupPayment.updatedBy}</td>
                    <td>
                      {pickupPayment.updatedTime ? (
                        <TextFormat type="date" value={pickupPayment.updatedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/pickup-payment/${pickupPayment.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/pickup-payment/${pickupPayment.id}/edit`}
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
                          onClick={() => (window.location.href = `/pickup-payment/${pickupPayment.id}/delete`)}
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
                <Translate contentKey="farmerBeApp.pickupPayment.home.notFound">No Pickup Payments found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default PickupPayment;
