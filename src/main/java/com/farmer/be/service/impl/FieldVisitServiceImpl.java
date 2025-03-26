package com.farmer.be.service.impl;

import com.farmer.be.domain.FieldVisit;
import com.farmer.be.repository.FieldVisitRepository;
import com.farmer.be.service.FieldVisitService;
import com.farmer.be.service.dto.FieldVisitDTO;
import com.farmer.be.service.mapper.FieldVisitMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.FieldVisit}.
 */
@Service
@Transactional
public class FieldVisitServiceImpl implements FieldVisitService {

    private static final Logger LOG = LoggerFactory.getLogger(FieldVisitServiceImpl.class);

    private final FieldVisitRepository fieldVisitRepository;

    private final FieldVisitMapper fieldVisitMapper;

    public FieldVisitServiceImpl(FieldVisitRepository fieldVisitRepository, FieldVisitMapper fieldVisitMapper) {
        this.fieldVisitRepository = fieldVisitRepository;
        this.fieldVisitMapper = fieldVisitMapper;
    }

    @Override
    public FieldVisitDTO save(FieldVisitDTO fieldVisitDTO) {
        LOG.debug("Request to save FieldVisit : {}", fieldVisitDTO);
        FieldVisit fieldVisit = fieldVisitMapper.toEntity(fieldVisitDTO);
        fieldVisit = fieldVisitRepository.save(fieldVisit);
        return fieldVisitMapper.toDto(fieldVisit);
    }

    @Override
    public FieldVisitDTO update(FieldVisitDTO fieldVisitDTO) {
        LOG.debug("Request to update FieldVisit : {}", fieldVisitDTO);
        FieldVisit fieldVisit = fieldVisitMapper.toEntity(fieldVisitDTO);
        fieldVisit = fieldVisitRepository.save(fieldVisit);
        return fieldVisitMapper.toDto(fieldVisit);
    }

    @Override
    public Optional<FieldVisitDTO> partialUpdate(FieldVisitDTO fieldVisitDTO) {
        LOG.debug("Request to partially update FieldVisit : {}", fieldVisitDTO);

        return fieldVisitRepository
            .findById(fieldVisitDTO.getId())
            .map(existingFieldVisit -> {
                fieldVisitMapper.partialUpdate(existingFieldVisit, fieldVisitDTO);

                return existingFieldVisit;
            })
            .map(fieldVisitRepository::save)
            .map(fieldVisitMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FieldVisitDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all FieldVisits");
        return fieldVisitRepository.findAll(pageable).map(fieldVisitMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FieldVisitDTO> findOne(Long id) {
        LOG.debug("Request to get FieldVisit : {}", id);
        return fieldVisitRepository.findById(id).map(fieldVisitMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete FieldVisit : {}", id);
        fieldVisitRepository.deleteById(id);
    }
}
