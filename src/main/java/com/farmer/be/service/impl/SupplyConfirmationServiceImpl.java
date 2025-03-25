package com.farmer.be.service.impl;

import com.farmer.be.domain.SupplyConfirmation;
import com.farmer.be.repository.SupplyConfirmationRepository;
import com.farmer.be.service.SupplyConfirmationService;
import com.farmer.be.service.dto.SupplyConfirmationDTO;
import com.farmer.be.service.mapper.SupplyConfirmationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.SupplyConfirmation}.
 */
@Service
@Transactional
public class SupplyConfirmationServiceImpl implements SupplyConfirmationService {

    private static final Logger LOG = LoggerFactory.getLogger(SupplyConfirmationServiceImpl.class);

    private final SupplyConfirmationRepository supplyConfirmationRepository;

    private final SupplyConfirmationMapper supplyConfirmationMapper;

    public SupplyConfirmationServiceImpl(
        SupplyConfirmationRepository supplyConfirmationRepository,
        SupplyConfirmationMapper supplyConfirmationMapper
    ) {
        this.supplyConfirmationRepository = supplyConfirmationRepository;
        this.supplyConfirmationMapper = supplyConfirmationMapper;
    }

    @Override
    public SupplyConfirmationDTO save(SupplyConfirmationDTO supplyConfirmationDTO) {
        LOG.debug("Request to save SupplyConfirmation : {}", supplyConfirmationDTO);
        SupplyConfirmation supplyConfirmation = supplyConfirmationMapper.toEntity(supplyConfirmationDTO);
        supplyConfirmation = supplyConfirmationRepository.save(supplyConfirmation);
        return supplyConfirmationMapper.toDto(supplyConfirmation);
    }

    @Override
    public SupplyConfirmationDTO update(SupplyConfirmationDTO supplyConfirmationDTO) {
        LOG.debug("Request to update SupplyConfirmation : {}", supplyConfirmationDTO);
        SupplyConfirmation supplyConfirmation = supplyConfirmationMapper.toEntity(supplyConfirmationDTO);
        supplyConfirmation = supplyConfirmationRepository.save(supplyConfirmation);
        return supplyConfirmationMapper.toDto(supplyConfirmation);
    }

    @Override
    public Optional<SupplyConfirmationDTO> partialUpdate(SupplyConfirmationDTO supplyConfirmationDTO) {
        LOG.debug("Request to partially update SupplyConfirmation : {}", supplyConfirmationDTO);

        return supplyConfirmationRepository
            .findById(supplyConfirmationDTO.getId())
            .map(existingSupplyConfirmation -> {
                supplyConfirmationMapper.partialUpdate(existingSupplyConfirmation, supplyConfirmationDTO);

                return existingSupplyConfirmation;
            })
            .map(supplyConfirmationRepository::save)
            .map(supplyConfirmationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SupplyConfirmationDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all SupplyConfirmations");
        return supplyConfirmationRepository.findAll(pageable).map(supplyConfirmationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SupplyConfirmationDTO> findOne(Long id) {
        LOG.debug("Request to get SupplyConfirmation : {}", id);
        return supplyConfirmationRepository.findById(id).map(supplyConfirmationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete SupplyConfirmation : {}", id);
        supplyConfirmationRepository.deleteById(id);
    }
}
