package com.farmer.be.service.impl;

import com.farmer.be.domain.DemandData;
import com.farmer.be.repository.DemandDataRepository;
import com.farmer.be.service.DemandDataService;
import com.farmer.be.service.dto.DemandDataDTO;
import com.farmer.be.service.mapper.DemandDataMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.DemandData}.
 */
@Service
@Transactional
public class DemandDataServiceImpl implements DemandDataService {

    private static final Logger LOG = LoggerFactory.getLogger(DemandDataServiceImpl.class);

    private final DemandDataRepository demandDataRepository;

    private final DemandDataMapper demandDataMapper;

    public DemandDataServiceImpl(DemandDataRepository demandDataRepository, DemandDataMapper demandDataMapper) {
        this.demandDataRepository = demandDataRepository;
        this.demandDataMapper = demandDataMapper;
    }

    @Override
    public DemandDataDTO save(DemandDataDTO demandDataDTO) {
        LOG.debug("Request to save DemandData : {}", demandDataDTO);
        DemandData demandData = demandDataMapper.toEntity(demandDataDTO);
        demandData = demandDataRepository.save(demandData);
        return demandDataMapper.toDto(demandData);
    }

    @Override
    public DemandDataDTO update(DemandDataDTO demandDataDTO) {
        LOG.debug("Request to update DemandData : {}", demandDataDTO);
        DemandData demandData = demandDataMapper.toEntity(demandDataDTO);
        demandData = demandDataRepository.save(demandData);
        return demandDataMapper.toDto(demandData);
    }

    @Override
    public Optional<DemandDataDTO> partialUpdate(DemandDataDTO demandDataDTO) {
        LOG.debug("Request to partially update DemandData : {}", demandDataDTO);

        return demandDataRepository
            .findById(demandDataDTO.getId())
            .map(existingDemandData -> {
                demandDataMapper.partialUpdate(existingDemandData, demandDataDTO);

                return existingDemandData;
            })
            .map(demandDataRepository::save)
            .map(demandDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DemandDataDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all DemandData");
        return demandDataRepository.findAll(pageable).map(demandDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DemandDataDTO> findOne(Long id) {
        LOG.debug("Request to get DemandData : {}", id);
        return demandDataRepository.findById(id).map(demandDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete DemandData : {}", id);
        demandDataRepository.deleteById(id);
    }
}
