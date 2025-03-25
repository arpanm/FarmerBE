package com.farmer.be.service.impl;

import com.farmer.be.domain.PickupPayment;
import com.farmer.be.repository.PickupPaymentRepository;
import com.farmer.be.service.PickupPaymentService;
import com.farmer.be.service.dto.PickupPaymentDTO;
import com.farmer.be.service.mapper.PickupPaymentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.PickupPayment}.
 */
@Service
@Transactional
public class PickupPaymentServiceImpl implements PickupPaymentService {

    private static final Logger LOG = LoggerFactory.getLogger(PickupPaymentServiceImpl.class);

    private final PickupPaymentRepository pickupPaymentRepository;

    private final PickupPaymentMapper pickupPaymentMapper;

    public PickupPaymentServiceImpl(PickupPaymentRepository pickupPaymentRepository, PickupPaymentMapper pickupPaymentMapper) {
        this.pickupPaymentRepository = pickupPaymentRepository;
        this.pickupPaymentMapper = pickupPaymentMapper;
    }

    @Override
    public PickupPaymentDTO save(PickupPaymentDTO pickupPaymentDTO) {
        LOG.debug("Request to save PickupPayment : {}", pickupPaymentDTO);
        PickupPayment pickupPayment = pickupPaymentMapper.toEntity(pickupPaymentDTO);
        pickupPayment = pickupPaymentRepository.save(pickupPayment);
        return pickupPaymentMapper.toDto(pickupPayment);
    }

    @Override
    public PickupPaymentDTO update(PickupPaymentDTO pickupPaymentDTO) {
        LOG.debug("Request to update PickupPayment : {}", pickupPaymentDTO);
        PickupPayment pickupPayment = pickupPaymentMapper.toEntity(pickupPaymentDTO);
        pickupPayment = pickupPaymentRepository.save(pickupPayment);
        return pickupPaymentMapper.toDto(pickupPayment);
    }

    @Override
    public Optional<PickupPaymentDTO> partialUpdate(PickupPaymentDTO pickupPaymentDTO) {
        LOG.debug("Request to partially update PickupPayment : {}", pickupPaymentDTO);

        return pickupPaymentRepository
            .findById(pickupPaymentDTO.getId())
            .map(existingPickupPayment -> {
                pickupPaymentMapper.partialUpdate(existingPickupPayment, pickupPaymentDTO);

                return existingPickupPayment;
            })
            .map(pickupPaymentRepository::save)
            .map(pickupPaymentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PickupPaymentDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all PickupPayments");
        return pickupPaymentRepository.findAll(pageable).map(pickupPaymentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PickupPaymentDTO> findOne(Long id) {
        LOG.debug("Request to get PickupPayment : {}", id);
        return pickupPaymentRepository.findById(id).map(pickupPaymentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete PickupPayment : {}", id);
        pickupPaymentRepository.deleteById(id);
    }
}
