package com.farmer.be.service.impl;

import com.farmer.be.domain.TermsAndCondition;
import com.farmer.be.repository.TermsAndConditionRepository;
import com.farmer.be.service.TermsAndConditionService;
import com.farmer.be.service.dto.TermsAndConditionDTO;
import com.farmer.be.service.mapper.TermsAndConditionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.TermsAndCondition}.
 */
@Service
@Transactional
public class TermsAndConditionServiceImpl implements TermsAndConditionService {

    private static final Logger LOG = LoggerFactory.getLogger(TermsAndConditionServiceImpl.class);

    private final TermsAndConditionRepository termsAndConditionRepository;

    private final TermsAndConditionMapper termsAndConditionMapper;

    public TermsAndConditionServiceImpl(
        TermsAndConditionRepository termsAndConditionRepository,
        TermsAndConditionMapper termsAndConditionMapper
    ) {
        this.termsAndConditionRepository = termsAndConditionRepository;
        this.termsAndConditionMapper = termsAndConditionMapper;
    }

    @Override
    public TermsAndConditionDTO save(TermsAndConditionDTO termsAndConditionDTO) {
        LOG.debug("Request to save TermsAndCondition : {}", termsAndConditionDTO);
        TermsAndCondition termsAndCondition = termsAndConditionMapper.toEntity(termsAndConditionDTO);
        termsAndCondition = termsAndConditionRepository.save(termsAndCondition);
        return termsAndConditionMapper.toDto(termsAndCondition);
    }

    @Override
    public TermsAndConditionDTO update(TermsAndConditionDTO termsAndConditionDTO) {
        LOG.debug("Request to update TermsAndCondition : {}", termsAndConditionDTO);
        TermsAndCondition termsAndCondition = termsAndConditionMapper.toEntity(termsAndConditionDTO);
        termsAndCondition = termsAndConditionRepository.save(termsAndCondition);
        return termsAndConditionMapper.toDto(termsAndCondition);
    }

    @Override
    public Optional<TermsAndConditionDTO> partialUpdate(TermsAndConditionDTO termsAndConditionDTO) {
        LOG.debug("Request to partially update TermsAndCondition : {}", termsAndConditionDTO);

        return termsAndConditionRepository
            .findById(termsAndConditionDTO.getId())
            .map(existingTermsAndCondition -> {
                termsAndConditionMapper.partialUpdate(existingTermsAndCondition, termsAndConditionDTO);

                return existingTermsAndCondition;
            })
            .map(termsAndConditionRepository::save)
            .map(termsAndConditionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TermsAndConditionDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all TermsAndConditions");
        return termsAndConditionRepository.findAll(pageable).map(termsAndConditionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TermsAndConditionDTO> findOne(Long id) {
        LOG.debug("Request to get TermsAndCondition : {}", id);
        return termsAndConditionRepository.findById(id).map(termsAndConditionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete TermsAndCondition : {}", id);
        termsAndConditionRepository.deleteById(id);
    }
}
