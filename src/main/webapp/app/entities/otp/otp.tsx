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

import { getEntities, reset } from './otp.reducer';

export const Otp = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const otpList = useAppSelector(state => state.otp.entities);
  const loading = useAppSelector(state => state.otp.loading);
  const links = useAppSelector(state => state.otp.links);
  const updateSuccess = useAppSelector(state => state.otp.updateSuccess);

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
      <h2 id="otp-heading" data-cy="OtpHeading">
        <Translate contentKey="farmerBeApp.otp.home.title">Otps</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="farmerBeApp.otp.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/otp/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="farmerBeApp.otp.home.createLabel">Create new Otp</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={otpList ? otpList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {otpList && otpList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="farmerBeApp.otp.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('email')}>
                    <Translate contentKey="farmerBeApp.otp.email">Email</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('email')} />
                  </th>
                  <th className="hand" onClick={sort('emailOtp')}>
                    <Translate contentKey="farmerBeApp.otp.emailOtp">Email Otp</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('emailOtp')} />
                  </th>
                  <th className="hand" onClick={sort('phone')}>
                    <Translate contentKey="farmerBeApp.otp.phone">Phone</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('phone')} />
                  </th>
                  <th className="hand" onClick={sort('phoneOtp')}>
                    <Translate contentKey="farmerBeApp.otp.phoneOtp">Phone Otp</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('phoneOtp')} />
                  </th>
                  <th className="hand" onClick={sort('isValidated')}>
                    <Translate contentKey="farmerBeApp.otp.isValidated">Is Validated</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isValidated')} />
                  </th>
                  <th className="hand" onClick={sort('expiryTime')}>
                    <Translate contentKey="farmerBeApp.otp.expiryTime">Expiry Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('expiryTime')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="farmerBeApp.otp.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('createddBy')}>
                    <Translate contentKey="farmerBeApp.otp.createddBy">Createdd By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createddBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdTime')}>
                    <Translate contentKey="farmerBeApp.otp.createdTime">Created Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdTime')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="farmerBeApp.otp.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedTime')}>
                    <Translate contentKey="farmerBeApp.otp.updatedTime">Updated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedTime')} />
                  </th>
                  <th>
                    <Translate contentKey="farmerBeApp.otp.farmer">Farmer</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {otpList.map((otp, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/otp/${otp.id}`} color="link" size="sm">
                        {otp.id}
                      </Button>
                    </td>
                    <td>{otp.email}</td>
                    <td>{otp.emailOtp}</td>
                    <td>{otp.phone}</td>
                    <td>{otp.phoneOtp}</td>
                    <td>{otp.isValidated ? 'true' : 'false'}</td>
                    <td>{otp.expiryTime ? <TextFormat type="date" value={otp.expiryTime} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{otp.isActive ? 'true' : 'false'}</td>
                    <td>{otp.createddBy}</td>
                    <td>{otp.createdTime ? <TextFormat type="date" value={otp.createdTime} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{otp.updatedBy}</td>
                    <td>{otp.updatedTime ? <TextFormat type="date" value={otp.updatedTime} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{otp.farmer ? <Link to={`/farmer/${otp.farmer.id}`}>{otp.farmer.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/otp/${otp.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/otp/${otp.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (window.location.href = `/otp/${otp.id}/delete`)}
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
                <Translate contentKey="farmerBeApp.otp.home.notFound">No Otps found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Otp;
