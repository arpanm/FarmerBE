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

import { getEntities, reset } from './banner-content.reducer';

export const BannerContent = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const bannerContentList = useAppSelector(state => state.bannerContent.entities);
  const loading = useAppSelector(state => state.bannerContent.loading);
  const links = useAppSelector(state => state.bannerContent.links);
  const updateSuccess = useAppSelector(state => state.bannerContent.updateSuccess);

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
      <h2 id="banner-content-heading" data-cy="BannerContentHeading">
        <Translate contentKey="farmerBeApp.bannerContent.home.title">Banner Contents</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="farmerBeApp.bannerContent.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/banner-content/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="farmerBeApp.bannerContent.home.createLabel">Create new Banner Content</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={bannerContentList ? bannerContentList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {bannerContentList && bannerContentList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="farmerBeApp.bannerContent.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('bannerTag')}>
                    <Translate contentKey="farmerBeApp.bannerContent.bannerTag">Banner Tag</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('bannerTag')} />
                  </th>
                  <th className="hand" onClick={sort('logoPath')}>
                    <Translate contentKey="farmerBeApp.bannerContent.logoPath">Logo Path</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('logoPath')} />
                  </th>
                  <th className="hand" onClick={sort('imagePath')}>
                    <Translate contentKey="farmerBeApp.bannerContent.imagePath">Image Path</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('imagePath')} />
                  </th>
                  <th className="hand" onClick={sort('heading')}>
                    <Translate contentKey="farmerBeApp.bannerContent.heading">Heading</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('heading')} />
                  </th>
                  <th className="hand" onClick={sort('subHeading')}>
                    <Translate contentKey="farmerBeApp.bannerContent.subHeading">Sub Heading</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('subHeading')} />
                  </th>
                  <th className="hand" onClick={sort('description')}>
                    <Translate contentKey="farmerBeApp.bannerContent.description">Description</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                  </th>
                  <th className="hand" onClick={sort('landingLink')}>
                    <Translate contentKey="farmerBeApp.bannerContent.landingLink">Landing Link</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('landingLink')} />
                  </th>
                  <th className="hand" onClick={sort('landingUtm')}>
                    <Translate contentKey="farmerBeApp.bannerContent.landingUtm">Landing Utm</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('landingUtm')} />
                  </th>
                  <th className="hand" onClick={sort('pixelLink')}>
                    <Translate contentKey="farmerBeApp.bannerContent.pixelLink">Pixel Link</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('pixelLink')} />
                  </th>
                  <th className="hand" onClick={sort('startTime')}>
                    <Translate contentKey="farmerBeApp.bannerContent.startTime">Start Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('startTime')} />
                  </th>
                  <th className="hand" onClick={sort('endTime')}>
                    <Translate contentKey="farmerBeApp.bannerContent.endTime">End Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('endTime')} />
                  </th>
                  <th className="hand" onClick={sort('isActive')}>
                    <Translate contentKey="farmerBeApp.bannerContent.isActive">Is Active</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isActive')} />
                  </th>
                  <th className="hand" onClick={sort('createddBy')}>
                    <Translate contentKey="farmerBeApp.bannerContent.createddBy">Createdd By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createddBy')} />
                  </th>
                  <th className="hand" onClick={sort('createdTime')}>
                    <Translate contentKey="farmerBeApp.bannerContent.createdTime">Created Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdTime')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="farmerBeApp.bannerContent.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedTime')}>
                    <Translate contentKey="farmerBeApp.bannerContent.updatedTime">Updated Time</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedTime')} />
                  </th>
                  <th>
                    <Translate contentKey="farmerBeApp.bannerContent.holdingCarousel">Holding Carousel</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {bannerContentList.map((bannerContent, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/banner-content/${bannerContent.id}`} color="link" size="sm">
                        {bannerContent.id}
                      </Button>
                    </td>
                    <td>{bannerContent.bannerTag}</td>
                    <td>{bannerContent.logoPath}</td>
                    <td>{bannerContent.imagePath}</td>
                    <td>{bannerContent.heading}</td>
                    <td>{bannerContent.subHeading}</td>
                    <td>{bannerContent.description}</td>
                    <td>{bannerContent.landingLink}</td>
                    <td>{bannerContent.landingUtm}</td>
                    <td>{bannerContent.pixelLink}</td>
                    <td>
                      {bannerContent.startTime ? <TextFormat type="date" value={bannerContent.startTime} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>
                      {bannerContent.endTime ? <TextFormat type="date" value={bannerContent.endTime} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>{bannerContent.isActive ? 'true' : 'false'}</td>
                    <td>{bannerContent.createddBy}</td>
                    <td>
                      {bannerContent.createdTime ? (
                        <TextFormat type="date" value={bannerContent.createdTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{bannerContent.updatedBy}</td>
                    <td>
                      {bannerContent.updatedTime ? (
                        <TextFormat type="date" value={bannerContent.updatedTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {bannerContent.holdingCarousel ? (
                        <Link to={`/carousel-content/${bannerContent.holdingCarousel.id}`}>{bannerContent.holdingCarousel.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/banner-content/${bannerContent.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/banner-content/${bannerContent.id}/edit`}
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
                          onClick={() => (window.location.href = `/banner-content/${bannerContent.id}/delete`)}
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
                <Translate contentKey="farmerBeApp.bannerContent.home.notFound">No Banner Contents found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default BannerContent;
