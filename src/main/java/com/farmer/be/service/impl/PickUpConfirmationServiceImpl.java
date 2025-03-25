package com.farmer.be.service.impl;

import com.farmer.be.domain.PickUpConfirmation;
import com.farmer.be.repository.PickUpConfirmationRepository;
import com.farmer.be.service.PickUpConfirmationService;
import com.farmer.be.service.dto.PickUpConfirmationDTO;
import com.farmer.be.service.mapper.PickUpConfirmationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.PickUpConfirmation}.
 */
@Service
@Transactional
public class PickUpConfirmationServiceImpl implements PickUpConfirmationService {

    private static final Logger LOG = LoggerFactory.getLogger(PickUpConfirmationServiceImpl.class);

    private final PickUpConfirmationRepository pickUpConfirmationRepository;

    private final PickUpConfirmationMapper pickUpConfirmationMapper;

    public PickUpConfirmationServiceImpl(
        PickUpConfirmationRepository pickUpConfirmationRepository,
        PickUpConfirmationMapper pickUpConfirmationMapper
    ) {
        this.pickUpConfirmationRepository = pickUpConfirmationRepository;
        this.pickUpConfirmationMapper = pickUpConfirmationMapper;
    }

    @Override
    public PickUpConfirmationDTO save(PickUpConfirmationDTO pickUpConfirmationDTO) {
        LOG.debug("Request to save PickUpConfirmation : {}", pickUpConfirmationDTO);
        PickUpConfirmation pickUpConfirmation = pickUpConfirmationMapper.toEntity(pickUpConfirmationDTO);
        pickUpConfirmation = pickUpConfirmationRepository.save(pickUpConfirmation);
        return pickUpConfirmationMapper.toDto(pickUpConfirmation);
    }

    @Override
    public PickUpConfirmationDTO update(PickUpConfirmationDTO pickUpConfirmationDTO) {
        LOG.debug("Request to update PickUpConfirmation : {}", pickUpConfirmationDTO);
        PickUpConfirmation pickUpConfirmation = pickUpConfirmationMapper.toEntity(pickUpConfirmationDTO);
        pickUpConfirmation = pickUpConfirmationRepository.save(pickUpConfirmation);
        return pickUpConfirmationMapper.toDto(pickUpConfirmation);
    }

    @Override
    public Optional<PickUpConfirmationDTO> partialUpdate(PickUpConfirmationDTO pickUpConfirmationDTO) {
        LOG.debug("Request to partially update PickUpConfirmation : {}", pickUpConfirmationDTO);

        return pickUpConfirmationRepository
            .findById(pickUpConfirmationDTO.getId())
            .map(existingPickUpConfirmation -> {
                pickUpConfirmationMapper.partialUpdate(existingPickUpConfirmation, pickUpConfirmationDTO);

                return existingPickUpConfirmation;
            })
            .map(pickUpConfirmationRepository::save)
            .map(pickUpConfirmationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PickUpConfirmationDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all PickUpConfirmations");
        return pickUpConfirmationRepository.findAll(pageable).map(pickUpConfirmationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PickUpConfirmationDTO> findOne(Long id) {
        LOG.debug("Request to get PickUpConfirmation : {}", id);
        return pickUpConfirmationRepository.findById(id).map(pickUpConfirmationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete PickUpConfirmation : {}", id);
        pickUpConfirmationRepository.deleteById(id);
    }
}
