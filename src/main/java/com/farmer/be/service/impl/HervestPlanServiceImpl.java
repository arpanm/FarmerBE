package com.farmer.be.service.impl;

import com.farmer.be.domain.HervestPlan;
import com.farmer.be.repository.HervestPlanRepository;
import com.farmer.be.service.HervestPlanService;
import com.farmer.be.service.dto.HervestPlanDTO;
import com.farmer.be.service.mapper.HervestPlanMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.HervestPlan}.
 */
@Service
@Transactional
public class HervestPlanServiceImpl implements HervestPlanService {

    private static final Logger LOG = LoggerFactory.getLogger(HervestPlanServiceImpl.class);

    private final HervestPlanRepository hervestPlanRepository;

    private final HervestPlanMapper hervestPlanMapper;

    public HervestPlanServiceImpl(HervestPlanRepository hervestPlanRepository, HervestPlanMapper hervestPlanMapper) {
        this.hervestPlanRepository = hervestPlanRepository;
        this.hervestPlanMapper = hervestPlanMapper;
    }

    @Override
    public HervestPlanDTO save(HervestPlanDTO hervestPlanDTO) {
        LOG.debug("Request to save HervestPlan : {}", hervestPlanDTO);
        HervestPlan hervestPlan = hervestPlanMapper.toEntity(hervestPlanDTO);
        hervestPlan = hervestPlanRepository.save(hervestPlan);
        return hervestPlanMapper.toDto(hervestPlan);
    }

    @Override
    public HervestPlanDTO update(HervestPlanDTO hervestPlanDTO) {
        LOG.debug("Request to update HervestPlan : {}", hervestPlanDTO);
        HervestPlan hervestPlan = hervestPlanMapper.toEntity(hervestPlanDTO);
        hervestPlan = hervestPlanRepository.save(hervestPlan);
        return hervestPlanMapper.toDto(hervestPlan);
    }

    @Override
    public Optional<HervestPlanDTO> partialUpdate(HervestPlanDTO hervestPlanDTO) {
        LOG.debug("Request to partially update HervestPlan : {}", hervestPlanDTO);

        return hervestPlanRepository
            .findById(hervestPlanDTO.getId())
            .map(existingHervestPlan -> {
                hervestPlanMapper.partialUpdate(existingHervestPlan, hervestPlanDTO);

                return existingHervestPlan;
            })
            .map(hervestPlanRepository::save)
            .map(hervestPlanMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HervestPlanDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all HervestPlans");
        return hervestPlanRepository.findAll(pageable).map(hervestPlanMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HervestPlanDTO> findOne(Long id) {
        LOG.debug("Request to get HervestPlan : {}", id);
        return hervestPlanRepository.findById(id).map(hervestPlanMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete HervestPlan : {}", id);
        hervestPlanRepository.deleteById(id);
    }
}
