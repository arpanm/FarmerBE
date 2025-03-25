package com.farmer.be.service.impl;

import com.farmer.be.domain.PickupGradation;
import com.farmer.be.repository.PickupGradationRepository;
import com.farmer.be.service.PickupGradationService;
import com.farmer.be.service.dto.PickupGradationDTO;
import com.farmer.be.service.mapper.PickupGradationMapper;
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
 * Service Implementation for managing {@link com.farmer.be.domain.PickupGradation}.
 */
@Service
@Transactional
public class PickupGradationServiceImpl implements PickupGradationService {

    private static final Logger LOG = LoggerFactory.getLogger(PickupGradationServiceImpl.class);

    private final PickupGradationRepository pickupGradationRepository;

    private final PickupGradationMapper pickupGradationMapper;

    public PickupGradationServiceImpl(PickupGradationRepository pickupGradationRepository, PickupGradationMapper pickupGradationMapper) {
        this.pickupGradationRepository = pickupGradationRepository;
        this.pickupGradationMapper = pickupGradationMapper;
    }

    @Override
    public PickupGradationDTO save(PickupGradationDTO pickupGradationDTO) {
        LOG.debug("Request to save PickupGradation : {}", pickupGradationDTO);
        PickupGradation pickupGradation = pickupGradationMapper.toEntity(pickupGradationDTO);
        pickupGradation = pickupGradationRepository.save(pickupGradation);
        return pickupGradationMapper.toDto(pickupGradation);
    }

    @Override
    public PickupGradationDTO update(PickupGradationDTO pickupGradationDTO) {
        LOG.debug("Request to update PickupGradation : {}", pickupGradationDTO);
        PickupGradation pickupGradation = pickupGradationMapper.toEntity(pickupGradationDTO);
        pickupGradation = pickupGradationRepository.save(pickupGradation);
        return pickupGradationMapper.toDto(pickupGradation);
    }

    @Override
    public Optional<PickupGradationDTO> partialUpdate(PickupGradationDTO pickupGradationDTO) {
        LOG.debug("Request to partially update PickupGradation : {}", pickupGradationDTO);

        return pickupGradationRepository
            .findById(pickupGradationDTO.getId())
            .map(existingPickupGradation -> {
                pickupGradationMapper.partialUpdate(existingPickupGradation, pickupGradationDTO);

                return existingPickupGradation;
            })
            .map(pickupGradationRepository::save)
            .map(pickupGradationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PickupGradationDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all PickupGradations");
        return pickupGradationRepository.findAll(pageable).map(pickupGradationMapper::toDto);
    }

    /**
     *  Get all the pickupGradations where PickupItem is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PickupGradationDTO> findAllWherePickupItemIsNull() {
        LOG.debug("Request to get all pickupGradations where PickupItem is null");
        return StreamSupport.stream(pickupGradationRepository.findAll().spliterator(), false)
            .filter(pickupGradation -> pickupGradation.getPickupItem() == null)
            .map(pickupGradationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PickupGradationDTO> findOne(Long id) {
        LOG.debug("Request to get PickupGradation : {}", id);
        return pickupGradationRepository.findById(id).map(pickupGradationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete PickupGradation : {}", id);
        pickupGradationRepository.deleteById(id);
    }
}
