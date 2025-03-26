package com.farmer.be.service.impl;

import com.farmer.be.domain.DemandDataFile;
import com.farmer.be.repository.DemandDataFileRepository;
import com.farmer.be.service.DemandDataFileService;
import com.farmer.be.service.dto.DemandDataFileDTO;
import com.farmer.be.service.mapper.DemandDataFileMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.DemandDataFile}.
 */
@Service
@Transactional
public class DemandDataFileServiceImpl implements DemandDataFileService {

    private static final Logger LOG = LoggerFactory.getLogger(DemandDataFileServiceImpl.class);

    private final DemandDataFileRepository demandDataFileRepository;

    private final DemandDataFileMapper demandDataFileMapper;

    public DemandDataFileServiceImpl(DemandDataFileRepository demandDataFileRepository, DemandDataFileMapper demandDataFileMapper) {
        this.demandDataFileRepository = demandDataFileRepository;
        this.demandDataFileMapper = demandDataFileMapper;
    }

    @Override
    public DemandDataFileDTO save(DemandDataFileDTO demandDataFileDTO) {
        LOG.debug("Request to save DemandDataFile : {}", demandDataFileDTO);
        DemandDataFile demandDataFile = demandDataFileMapper.toEntity(demandDataFileDTO);
        demandDataFile = demandDataFileRepository.save(demandDataFile);
        return demandDataFileMapper.toDto(demandDataFile);
    }

    @Override
    public DemandDataFileDTO update(DemandDataFileDTO demandDataFileDTO) {
        LOG.debug("Request to update DemandDataFile : {}", demandDataFileDTO);
        DemandDataFile demandDataFile = demandDataFileMapper.toEntity(demandDataFileDTO);
        demandDataFile = demandDataFileRepository.save(demandDataFile);
        return demandDataFileMapper.toDto(demandDataFile);
    }

    @Override
    public Optional<DemandDataFileDTO> partialUpdate(DemandDataFileDTO demandDataFileDTO) {
        LOG.debug("Request to partially update DemandDataFile : {}", demandDataFileDTO);

        return demandDataFileRepository
            .findById(demandDataFileDTO.getId())
            .map(existingDemandDataFile -> {
                demandDataFileMapper.partialUpdate(existingDemandDataFile, demandDataFileDTO);

                return existingDemandDataFile;
            })
            .map(demandDataFileRepository::save)
            .map(demandDataFileMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DemandDataFileDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all DemandDataFiles");
        return demandDataFileRepository.findAll(pageable).map(demandDataFileMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DemandDataFileDTO> findOne(Long id) {
        LOG.debug("Request to get DemandDataFile : {}", id);
        return demandDataFileRepository.findById(id).map(demandDataFileMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete DemandDataFile : {}", id);
        demandDataFileRepository.deleteById(id);
    }
}
