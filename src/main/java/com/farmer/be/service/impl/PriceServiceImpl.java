package com.farmer.be.service.impl;

import com.farmer.be.domain.Price;
import com.farmer.be.repository.PriceRepository;
import com.farmer.be.service.PriceService;
import com.farmer.be.service.dto.PriceDTO;
import com.farmer.be.service.mapper.PriceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.Price}.
 */
@Service
@Transactional
public class PriceServiceImpl implements PriceService {

    private static final Logger LOG = LoggerFactory.getLogger(PriceServiceImpl.class);

    private final PriceRepository priceRepository;

    private final PriceMapper priceMapper;

    public PriceServiceImpl(PriceRepository priceRepository, PriceMapper priceMapper) {
        this.priceRepository = priceRepository;
        this.priceMapper = priceMapper;
    }

    @Override
    public PriceDTO save(PriceDTO priceDTO) {
        LOG.debug("Request to save Price : {}", priceDTO);
        Price price = priceMapper.toEntity(priceDTO);
        price = priceRepository.save(price);
        return priceMapper.toDto(price);
    }

    @Override
    public PriceDTO update(PriceDTO priceDTO) {
        LOG.debug("Request to update Price : {}", priceDTO);
        Price price = priceMapper.toEntity(priceDTO);
        price = priceRepository.save(price);
        return priceMapper.toDto(price);
    }

    @Override
    public Optional<PriceDTO> partialUpdate(PriceDTO priceDTO) {
        LOG.debug("Request to partially update Price : {}", priceDTO);

        return priceRepository
            .findById(priceDTO.getId())
            .map(existingPrice -> {
                priceMapper.partialUpdate(existingPrice, priceDTO);

                return existingPrice;
            })
            .map(priceRepository::save)
            .map(priceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PriceDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Prices");
        return priceRepository.findAll(pageable).map(priceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PriceDTO> findOne(Long id) {
        LOG.debug("Request to get Price : {}", id);
        return priceRepository.findById(id).map(priceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Price : {}", id);
        priceRepository.deleteById(id);
    }
}
