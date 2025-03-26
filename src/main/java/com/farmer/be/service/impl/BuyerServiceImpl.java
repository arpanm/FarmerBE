package com.farmer.be.service.impl;

import com.farmer.be.domain.Buyer;
import com.farmer.be.repository.BuyerRepository;
import com.farmer.be.service.BuyerService;
import com.farmer.be.service.dto.BuyerDTO;
import com.farmer.be.service.mapper.BuyerMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.Buyer}.
 */
@Service
@Transactional
public class BuyerServiceImpl implements BuyerService {

    private static final Logger LOG = LoggerFactory.getLogger(BuyerServiceImpl.class);

    private final BuyerRepository buyerRepository;

    private final BuyerMapper buyerMapper;

    public BuyerServiceImpl(BuyerRepository buyerRepository, BuyerMapper buyerMapper) {
        this.buyerRepository = buyerRepository;
        this.buyerMapper = buyerMapper;
    }

    @Override
    public BuyerDTO save(BuyerDTO buyerDTO) {
        LOG.debug("Request to save Buyer : {}", buyerDTO);
        Buyer buyer = buyerMapper.toEntity(buyerDTO);
        buyer = buyerRepository.save(buyer);
        return buyerMapper.toDto(buyer);
    }

    @Override
    public BuyerDTO update(BuyerDTO buyerDTO) {
        LOG.debug("Request to update Buyer : {}", buyerDTO);
        Buyer buyer = buyerMapper.toEntity(buyerDTO);
        buyer = buyerRepository.save(buyer);
        return buyerMapper.toDto(buyer);
    }

    @Override
    public Optional<BuyerDTO> partialUpdate(BuyerDTO buyerDTO) {
        LOG.debug("Request to partially update Buyer : {}", buyerDTO);

        return buyerRepository
            .findById(buyerDTO.getId())
            .map(existingBuyer -> {
                buyerMapper.partialUpdate(existingBuyer, buyerDTO);

                return existingBuyer;
            })
            .map(buyerRepository::save)
            .map(buyerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BuyerDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Buyers");
        return buyerRepository.findAll(pageable).map(buyerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BuyerDTO> findOne(Long id) {
        LOG.debug("Request to get Buyer : {}", id);
        return buyerRepository.findById(id).map(buyerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Buyer : {}", id);
        buyerRepository.deleteById(id);
    }
}
