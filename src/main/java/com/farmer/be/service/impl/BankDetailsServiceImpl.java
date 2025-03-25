package com.farmer.be.service.impl;

import com.farmer.be.domain.BankDetails;
import com.farmer.be.repository.BankDetailsRepository;
import com.farmer.be.service.BankDetailsService;
import com.farmer.be.service.dto.BankDetailsDTO;
import com.farmer.be.service.mapper.BankDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.BankDetails}.
 */
@Service
@Transactional
public class BankDetailsServiceImpl implements BankDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(BankDetailsServiceImpl.class);

    private final BankDetailsRepository bankDetailsRepository;

    private final BankDetailsMapper bankDetailsMapper;

    public BankDetailsServiceImpl(BankDetailsRepository bankDetailsRepository, BankDetailsMapper bankDetailsMapper) {
        this.bankDetailsRepository = bankDetailsRepository;
        this.bankDetailsMapper = bankDetailsMapper;
    }

    @Override
    public BankDetailsDTO save(BankDetailsDTO bankDetailsDTO) {
        LOG.debug("Request to save BankDetails : {}", bankDetailsDTO);
        BankDetails bankDetails = bankDetailsMapper.toEntity(bankDetailsDTO);
        bankDetails = bankDetailsRepository.save(bankDetails);
        return bankDetailsMapper.toDto(bankDetails);
    }

    @Override
    public BankDetailsDTO update(BankDetailsDTO bankDetailsDTO) {
        LOG.debug("Request to update BankDetails : {}", bankDetailsDTO);
        BankDetails bankDetails = bankDetailsMapper.toEntity(bankDetailsDTO);
        bankDetails = bankDetailsRepository.save(bankDetails);
        return bankDetailsMapper.toDto(bankDetails);
    }

    @Override
    public Optional<BankDetailsDTO> partialUpdate(BankDetailsDTO bankDetailsDTO) {
        LOG.debug("Request to partially update BankDetails : {}", bankDetailsDTO);

        return bankDetailsRepository
            .findById(bankDetailsDTO.getId())
            .map(existingBankDetails -> {
                bankDetailsMapper.partialUpdate(existingBankDetails, bankDetailsDTO);

                return existingBankDetails;
            })
            .map(bankDetailsRepository::save)
            .map(bankDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BankDetailsDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all BankDetails");
        return bankDetailsRepository.findAll(pageable).map(bankDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BankDetailsDTO> findOne(Long id) {
        LOG.debug("Request to get BankDetails : {}", id);
        return bankDetailsRepository.findById(id).map(bankDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete BankDetails : {}", id);
        bankDetailsRepository.deleteById(id);
    }
}
