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

import { getEntities, reset } from './bank-details.reducer';

export const BankDetails = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const bankDetailsList = useAppSelector(state => state.bankDetails.entities);
  const loading = useAppSelector(state => state.bankDetails.loading);
  const links = useAppSelector(state => state.bankDetails.links);
  const updateSuccess = useAppSelector(state => state.bankDetails.updateSuccess);

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
      <h2 id="bank-details-heading" data-cy="BankDetailsHeading">
        <Translate contentKey="farmerBeApp.bankDetails.home.title">Bank Details</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="farmerBeApp.bankDetails.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/bank-details/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="farmerBeApp.bankDetails.home.createLabel">Create new Bank Details</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={bankDetailsList ? bankDetailsList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {bankDetailsList && bankDetailsList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="farmerBeApp.bankDetails.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('name')}>
                    <Translate contentKey="farmerBeApp.bankDetails.name">Name</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                  </th>
                  <th className="hand" onClick={sort('accountNumber')}>
                    <Translate contentKey="farmerBeApp.bankDetails.accountNumber">Account Number</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('accountNumber')} />
                  </th>
                  <th className="hand" onClick={sort('ifsC')}>
                    <Translate contentKey="farmerBeApp.bankDetails.ifsC">Ifs C</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('ifsC')} />
                  </th>
                  <th className="hand" onClick={sort('bankName')}>
                    <Translate contentKey="farmerBeApp.bankDetails.bankName">Bank Name</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('bankName')} />
                  </th>
                  <th className="hand" onClick={sort('branchName')}>
                    <Translate contentKey="farmerBeApp.bankDetails.branchName">Branch Name</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('branchName')} />
                  </th>
                  <th className="hand" onClick={sort('isVerified')}>
                    <Translate contentKey="farmerBeApp.bankDetails.isVerified">Is Verified</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isVerified')} />
                  </th>
                  <th className="hand" onClick={sort('verificationTime')}>
                    <Translate contentKey="farmerBeApp.bankDetails.verificationTime">Verification Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('verificationTime')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="farmerBeApp.bankDetails.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('createddBy')}>
                    <Translate contentKey="farmerBeApp.bankDetails.createddBy">Createdd By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createddBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdTime')}>
                    <Translate contentKey="farmerBeApp.bankDetails.createdTime">Created Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdTime')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="farmerBeApp.bankDetails.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedTime')}>
                    <Translate contentKey="farmerBeApp.bankDetails.updatedTime">Updated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedTime')} />
                  </th>
                  <th>
                    <Translate contentKey="farmerBeApp.bankDetails.farmer">Farmer</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {bankDetailsList.map((bankDetails, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/bank-details/${bankDetails.id}`} color="link" size="sm">
                        {bankDetails.id}
                      </Button>
                    </td>
                    <td>{bankDetails.name}</td>
                    <td>{bankDetails.accountNumber}</td>
                    <td>{bankDetails.ifsC}</td>
                    <td>{bankDetails.bankName}</td>
                    <td>{bankDetails.branchName}</td>
                    <td>{bankDetails.isVerified ? 'true' : 'false'}</td>
                    <td>
                      {bankDetails.verificationTime ? (
                        <TextFormat type="date" value={bankDetails.verificationTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{bankDetails.isActive ? 'true' : 'false'}</td>
                    <td>{bankDetails.createddBy}</td>
                    <td>
                      {bankDetails.createdTime ? <TextFormat type="date" value={bankDetails.createdTime} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>{bankDetails.updatedBy}</td>
                    <td>
                      {bankDetails.updatedTime ? <TextFormat type="date" value={bankDetails.updatedTime} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>{bankDetails.farmer ? <Link to={`/farmer/${bankDetails.farmer.id}`}>{bankDetails.farmer.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/bank-details/${bankDetails.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/bank-details/${bankDetails.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (window.location.href = `/bank-details/${bankDetails.id}/delete`)}
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
                <Translate contentKey="farmerBeApp.bankDetails.home.notFound">No Bank Details found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default BankDetails;
