package com.farmer.be.service.impl;

import com.farmer.be.domain.CollectionCenter;
import com.farmer.be.repository.CollectionCenterRepository;
import com.farmer.be.service.CollectionCenterService;
import com.farmer.be.service.dto.CollectionCenterDTO;
import com.farmer.be.service.mapper.CollectionCenterMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.CollectionCenter}.
 */
@Service
@Transactional
public class CollectionCenterServiceImpl implements CollectionCenterService {

    private static final Logger LOG = LoggerFactory.getLogger(CollectionCenterServiceImpl.class);

    private final CollectionCenterRepository collectionCenterRepository;

    private final CollectionCenterMapper collectionCenterMapper;

    public CollectionCenterServiceImpl(
        CollectionCenterRepository collectionCenterRepository,
        CollectionCenterMapper collectionCenterMapper
    ) {
        this.collectionCenterRepository = collectionCenterRepository;
        this.collectionCenterMapper = collectionCenterMapper;
    }

    @Override
    public CollectionCenterDTO save(CollectionCenterDTO collectionCenterDTO) {
        LOG.debug("Request to save CollectionCenter : {}", collectionCenterDTO);
        CollectionCenter collectionCenter = collectionCenterMapper.toEntity(collectionCenterDTO);
        collectionCenter = collectionCenterRepository.save(collectionCenter);
        return collectionCenterMapper.toDto(collectionCenter);
    }

    @Override
    public CollectionCenterDTO update(CollectionCenterDTO collectionCenterDTO) {
        LOG.debug("Request to update CollectionCenter : {}", collectionCenterDTO);
        CollectionCenter collectionCenter = collectionCenterMapper.toEntity(collectionCenterDTO);
        collectionCenter = collectionCenterRepository.save(collectionCenter);
        return collectionCenterMapper.toDto(collectionCenter);
    }

    @Override
    public Optional<CollectionCenterDTO> partialUpdate(CollectionCenterDTO collectionCenterDTO) {
        LOG.debug("Request to partially update CollectionCenter : {}", collectionCenterDTO);

        return collectionCenterRepository
            .findById(collectionCenterDTO.getId())
            .map(existingCollectionCenter -> {
                collectionCenterMapper.partialUpdate(existingCollectionCenter, collectionCenterDTO);

                return existingCollectionCenter;
            })
            .map(collectionCenterRepository::save)
            .map(collectionCenterMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CollectionCenterDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all CollectionCenters");
        return collectionCenterRepository.findAll(pageable).map(collectionCenterMapper::toDto);
    }

    public Page<CollectionCenterDTO> findAllWithEagerRelationships(Pageable pageable) {
        return collectionCenterRepository.findAllWithEagerRelationships(pageable).map(collectionCenterMapper::toDto);
    }

    /**
     *  Get all the collectionCenters where DemandData is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CollectionCenterDTO> findAllWhereDemandDataIsNull() {
        LOG.debug("Request to get all collectionCenters where DemandData is null");
        return StreamSupport.stream(collectionCenterRepository.findAll().spliterator(), false)
            .filter(collectionCenter -> collectionCenter.getDemandData() == null)
            .map(collectionCenterMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CollectionCenterDTO> findOne(Long id) {
        LOG.debug("Request to get CollectionCenter : {}", id);
        return collectionCenterRepository.findOneWithEagerRelationships(id).map(collectionCenterMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete CollectionCenter : {}", id);
        collectionCenterRepository.deleteById(id);
    }
}
