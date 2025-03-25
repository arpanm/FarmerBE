package com.farmer.be.service.impl;

import com.farmer.be.domain.Demand;
import com.farmer.be.repository.DemandRepository;
import com.farmer.be.service.DemandService;
import com.farmer.be.service.dto.DemandDTO;
import com.farmer.be.service.mapper.DemandMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.Demand}.
 */
@Service
@Transactional
public class DemandServiceImpl implements DemandService {

    private static final Logger LOG = LoggerFactory.getLogger(DemandServiceImpl.class);

    private final DemandRepository demandRepository;

    private final DemandMapper demandMapper;

    public DemandServiceImpl(DemandRepository demandRepository, DemandMapper demandMapper) {
        this.demandRepository = demandRepository;
        this.demandMapper = demandMapper;
    }

    @Override
    public DemandDTO save(DemandDTO demandDTO) {
        LOG.debug("Request to save Demand : {}", demandDTO);
        Demand demand = demandMapper.toEntity(demandDTO);
        demand = demandRepository.save(demand);
        return demandMapper.toDto(demand);
    }

    @Override
    public DemandDTO update(DemandDTO demandDTO) {
        LOG.debug("Request to update Demand : {}", demandDTO);
        Demand demand = demandMapper.toEntity(demandDTO);
        demand = demandRepository.save(demand);
        return demandMapper.toDto(demand);
    }

    @Override
    public Optional<DemandDTO> partialUpdate(DemandDTO demandDTO) {
        LOG.debug("Request to partially update Demand : {}", demandDTO);

        return demandRepository
            .findById(demandDTO.getId())
            .map(existingDemand -> {
                demandMapper.partialUpdate(existingDemand, demandDTO);

                return existingDemand;
            })
            .map(demandRepository::save)
            .map(demandMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DemandDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Demands");
        return demandRepository.findAll(pageable).map(demandMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DemandDTO> findOne(Long id) {
        LOG.debug("Request to get Demand : {}", id);
        return demandRepository.findById(id).map(demandMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Demand : {}", id);
        demandRepository.deleteById(id);
    }
}
