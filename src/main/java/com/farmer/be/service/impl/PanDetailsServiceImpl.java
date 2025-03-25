package com.farmer.be.service.impl;

import com.farmer.be.domain.PanDetails;
import com.farmer.be.repository.PanDetailsRepository;
import com.farmer.be.service.PanDetailsService;
import com.farmer.be.service.dto.PanDetailsDTO;
import com.farmer.be.service.mapper.PanDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.PanDetails}.
 */
@Service
@Transactional
public class PanDetailsServiceImpl implements PanDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(PanDetailsServiceImpl.class);

    private final PanDetailsRepository panDetailsRepository;

    private final PanDetailsMapper panDetailsMapper;

    public PanDetailsServiceImpl(PanDetailsRepository panDetailsRepository, PanDetailsMapper panDetailsMapper) {
        this.panDetailsRepository = panDetailsRepository;
        this.panDetailsMapper = panDetailsMapper;
    }

    @Override
    public PanDetailsDTO save(PanDetailsDTO panDetailsDTO) {
        LOG.debug("Request to save PanDetails : {}", panDetailsDTO);
        PanDetails panDetails = panDetailsMapper.toEntity(panDetailsDTO);
        panDetails = panDetailsRepository.save(panDetails);
        return panDetailsMapper.toDto(panDetails);
    }

    @Override
    public PanDetailsDTO update(PanDetailsDTO panDetailsDTO) {
        LOG.debug("Request to update PanDetails : {}", panDetailsDTO);
        PanDetails panDetails = panDetailsMapper.toEntity(panDetailsDTO);
        panDetails = panDetailsRepository.save(panDetails);
        return panDetailsMapper.toDto(panDetails);
    }

    @Override
    public Optional<PanDetailsDTO> partialUpdate(PanDetailsDTO panDetailsDTO) {
        LOG.debug("Request to partially update PanDetails : {}", panDetailsDTO);

        return panDetailsRepository
            .findById(panDetailsDTO.getId())
            .map(existingPanDetails -> {
                panDetailsMapper.partialUpdate(existingPanDetails, panDetailsDTO);

                return existingPanDetails;
            })
            .map(panDetailsRepository::save)
            .map(panDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PanDetailsDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all PanDetails");
        return panDetailsRepository.findAll(pageable).map(panDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PanDetailsDTO> findOne(Long id) {
        LOG.debug("Request to get PanDetails : {}", id);
        return panDetailsRepository.findById(id).map(panDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete PanDetails : {}", id);
        panDetailsRepository.deleteById(id);
    }
}
