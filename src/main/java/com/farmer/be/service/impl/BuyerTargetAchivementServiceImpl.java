package com.farmer.be.service.impl;

import com.farmer.be.domain.BuyerTargetAchivement;
import com.farmer.be.repository.BuyerTargetAchivementRepository;
import com.farmer.be.service.BuyerTargetAchivementService;
import com.farmer.be.service.dto.BuyerTargetAchivementDTO;
import com.farmer.be.service.mapper.BuyerTargetAchivementMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.BuyerTargetAchivement}.
 */
@Service
@Transactional
public class BuyerTargetAchivementServiceImpl implements BuyerTargetAchivementService {

    private static final Logger LOG = LoggerFactory.getLogger(BuyerTargetAchivementServiceImpl.class);

    private final BuyerTargetAchivementRepository buyerTargetAchivementRepository;

    private final BuyerTargetAchivementMapper buyerTargetAchivementMapper;

    public BuyerTargetAchivementServiceImpl(
        BuyerTargetAchivementRepository buyerTargetAchivementRepository,
        BuyerTargetAchivementMapper buyerTargetAchivementMapper
    ) {
        this.buyerTargetAchivementRepository = buyerTargetAchivementRepository;
        this.buyerTargetAchivementMapper = buyerTargetAchivementMapper;
    }

    @Override
    public BuyerTargetAchivementDTO save(BuyerTargetAchivementDTO buyerTargetAchivementDTO) {
        LOG.debug("Request to save BuyerTargetAchivement : {}", buyerTargetAchivementDTO);
        BuyerTargetAchivement buyerTargetAchivement = buyerTargetAchivementMapper.toEntity(buyerTargetAchivementDTO);
        buyerTargetAchivement = buyerTargetAchivementRepository.save(buyerTargetAchivement);
        return buyerTargetAchivementMapper.toDto(buyerTargetAchivement);
    }

    @Override
    public BuyerTargetAchivementDTO update(BuyerTargetAchivementDTO buyerTargetAchivementDTO) {
        LOG.debug("Request to update BuyerTargetAchivement : {}", buyerTargetAchivementDTO);
        BuyerTargetAchivement buyerTargetAchivement = buyerTargetAchivementMapper.toEntity(buyerTargetAchivementDTO);
        buyerTargetAchivement = buyerTargetAchivementRepository.save(buyerTargetAchivement);
        return buyerTargetAchivementMapper.toDto(buyerTargetAchivement);
    }

    @Override
    public Optional<BuyerTargetAchivementDTO> partialUpdate(BuyerTargetAchivementDTO buyerTargetAchivementDTO) {
        LOG.debug("Request to partially update BuyerTargetAchivement : {}", buyerTargetAchivementDTO);

        return buyerTargetAchivementRepository
            .findById(buyerTargetAchivementDTO.getId())
            .map(existingBuyerTargetAchivement -> {
                buyerTargetAchivementMapper.partialUpdate(existingBuyerTargetAchivement, buyerTargetAchivementDTO);

                return existingBuyerTargetAchivement;
            })
            .map(buyerTargetAchivementRepository::save)
            .map(buyerTargetAchivementMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BuyerTargetAchivementDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all BuyerTargetAchivements");
        return buyerTargetAchivementRepository.findAll(pageable).map(buyerTargetAchivementMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BuyerTargetAchivementDTO> findOne(Long id) {
        LOG.debug("Request to get BuyerTargetAchivement : {}", id);
        return buyerTargetAchivementRepository.findById(id).map(buyerTargetAchivementMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete BuyerTargetAchivement : {}", id);
        buyerTargetAchivementRepository.deleteById(id);
    }
}
