package com.farmer.be.service.impl;

import com.farmer.be.domain.HervestPlanRule;
import com.farmer.be.repository.HervestPlanRuleRepository;
import com.farmer.be.service.HervestPlanRuleService;
import com.farmer.be.service.dto.HervestPlanRuleDTO;
import com.farmer.be.service.mapper.HervestPlanRuleMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.HervestPlanRule}.
 */
@Service
@Transactional
public class HervestPlanRuleServiceImpl implements HervestPlanRuleService {

    private static final Logger LOG = LoggerFactory.getLogger(HervestPlanRuleServiceImpl.class);

    private final HervestPlanRuleRepository hervestPlanRuleRepository;

    private final HervestPlanRuleMapper hervestPlanRuleMapper;

    public HervestPlanRuleServiceImpl(HervestPlanRuleRepository hervestPlanRuleRepository, HervestPlanRuleMapper hervestPlanRuleMapper) {
        this.hervestPlanRuleRepository = hervestPlanRuleRepository;
        this.hervestPlanRuleMapper = hervestPlanRuleMapper;
    }

    @Override
    public HervestPlanRuleDTO save(HervestPlanRuleDTO hervestPlanRuleDTO) {
        LOG.debug("Request to save HervestPlanRule : {}", hervestPlanRuleDTO);
        HervestPlanRule hervestPlanRule = hervestPlanRuleMapper.toEntity(hervestPlanRuleDTO);
        hervestPlanRule = hervestPlanRuleRepository.save(hervestPlanRule);
        return hervestPlanRuleMapper.toDto(hervestPlanRule);
    }

    @Override
    public HervestPlanRuleDTO update(HervestPlanRuleDTO hervestPlanRuleDTO) {
        LOG.debug("Request to update HervestPlanRule : {}", hervestPlanRuleDTO);
        HervestPlanRule hervestPlanRule = hervestPlanRuleMapper.toEntity(hervestPlanRuleDTO);
        hervestPlanRule = hervestPlanRuleRepository.save(hervestPlanRule);
        return hervestPlanRuleMapper.toDto(hervestPlanRule);
    }

    @Override
    public Optional<HervestPlanRuleDTO> partialUpdate(HervestPlanRuleDTO hervestPlanRuleDTO) {
        LOG.debug("Request to partially update HervestPlanRule : {}", hervestPlanRuleDTO);

        return hervestPlanRuleRepository
            .findById(hervestPlanRuleDTO.getId())
            .map(existingHervestPlanRule -> {
                hervestPlanRuleMapper.partialUpdate(existingHervestPlanRule, hervestPlanRuleDTO);

                return existingHervestPlanRule;
            })
            .map(hervestPlanRuleRepository::save)
            .map(hervestPlanRuleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HervestPlanRuleDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all HervestPlanRules");
        return hervestPlanRuleRepository.findAll(pageable).map(hervestPlanRuleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HervestPlanRuleDTO> findOne(Long id) {
        LOG.debug("Request to get HervestPlanRule : {}", id);
        return hervestPlanRuleRepository.findById(id).map(hervestPlanRuleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete HervestPlanRule : {}", id);
        hervestPlanRuleRepository.deleteById(id);
    }
}
