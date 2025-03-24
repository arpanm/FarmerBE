package com.farmer.be.service.impl;

import com.farmer.be.domain.Farmer;
import com.farmer.be.repository.FarmerRepository;
import com.farmer.be.service.FarmerService;
import com.farmer.be.service.dto.FarmerDTO;
import com.farmer.be.service.mapper.FarmerMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.Farmer}.
 */
@Service
@Transactional
public class FarmerServiceImpl implements FarmerService {

    private static final Logger LOG = LoggerFactory.getLogger(FarmerServiceImpl.class);

    private final FarmerRepository farmerRepository;

    private final FarmerMapper farmerMapper;

    public FarmerServiceImpl(FarmerRepository farmerRepository, FarmerMapper farmerMapper) {
        this.farmerRepository = farmerRepository;
        this.farmerMapper = farmerMapper;
    }

    @Override
    public FarmerDTO save(FarmerDTO farmerDTO) {
        LOG.debug("Request to save Farmer : {}", farmerDTO);
        Farmer farmer = farmerMapper.toEntity(farmerDTO);
        farmer = farmerRepository.save(farmer);
        return farmerMapper.toDto(farmer);
    }

    @Override
    public FarmerDTO update(FarmerDTO farmerDTO) {
        LOG.debug("Request to update Farmer : {}", farmerDTO);
        Farmer farmer = farmerMapper.toEntity(farmerDTO);
        farmer = farmerRepository.save(farmer);
        return farmerMapper.toDto(farmer);
    }

    @Override
    public Optional<FarmerDTO> partialUpdate(FarmerDTO farmerDTO) {
        LOG.debug("Request to partially update Farmer : {}", farmerDTO);

        return farmerRepository
            .findById(farmerDTO.getId())
            .map(existingFarmer -> {
                farmerMapper.partialUpdate(existingFarmer, farmerDTO);

                return existingFarmer;
            })
            .map(farmerRepository::save)
            .map(farmerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FarmerDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Farmers");
        return farmerRepository.findAll(pageable).map(farmerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FarmerDTO> findOne(Long id) {
        LOG.debug("Request to get Farmer : {}", id);
        return farmerRepository.findById(id).map(farmerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Farmer : {}", id);
        farmerRepository.deleteById(id);
    }
}
