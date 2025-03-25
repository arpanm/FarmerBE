package com.farmer.be.service.impl;

import com.farmer.be.domain.Accessories;
import com.farmer.be.repository.AccessoriesRepository;
import com.farmer.be.service.AccessoriesService;
import com.farmer.be.service.dto.AccessoriesDTO;
import com.farmer.be.service.mapper.AccessoriesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.Accessories}.
 */
@Service
@Transactional
public class AccessoriesServiceImpl implements AccessoriesService {

    private static final Logger LOG = LoggerFactory.getLogger(AccessoriesServiceImpl.class);

    private final AccessoriesRepository accessoriesRepository;

    private final AccessoriesMapper accessoriesMapper;

    public AccessoriesServiceImpl(AccessoriesRepository accessoriesRepository, AccessoriesMapper accessoriesMapper) {
        this.accessoriesRepository = accessoriesRepository;
        this.accessoriesMapper = accessoriesMapper;
    }

    @Override
    public AccessoriesDTO save(AccessoriesDTO accessoriesDTO) {
        LOG.debug("Request to save Accessories : {}", accessoriesDTO);
        Accessories accessories = accessoriesMapper.toEntity(accessoriesDTO);
        accessories = accessoriesRepository.save(accessories);
        return accessoriesMapper.toDto(accessories);
    }

    @Override
    public AccessoriesDTO update(AccessoriesDTO accessoriesDTO) {
        LOG.debug("Request to update Accessories : {}", accessoriesDTO);
        Accessories accessories = accessoriesMapper.toEntity(accessoriesDTO);
        accessories = accessoriesRepository.save(accessories);
        return accessoriesMapper.toDto(accessories);
    }

    @Override
    public Optional<AccessoriesDTO> partialUpdate(AccessoriesDTO accessoriesDTO) {
        LOG.debug("Request to partially update Accessories : {}", accessoriesDTO);

        return accessoriesRepository
            .findById(accessoriesDTO.getId())
            .map(existingAccessories -> {
                accessoriesMapper.partialUpdate(existingAccessories, accessoriesDTO);

                return existingAccessories;
            })
            .map(accessoriesRepository::save)
            .map(accessoriesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AccessoriesDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Accessories");
        return accessoriesRepository.findAll(pageable).map(accessoriesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AccessoriesDTO> findOne(Long id) {
        LOG.debug("Request to get Accessories : {}", id);
        return accessoriesRepository.findById(id).map(accessoriesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Accessories : {}", id);
        accessoriesRepository.deleteById(id);
    }
}
