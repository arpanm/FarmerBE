package com.farmer.be.service.impl;

import com.farmer.be.domain.Crop;
import com.farmer.be.repository.CropRepository;
import com.farmer.be.service.CropService;
import com.farmer.be.service.dto.CropDTO;
import com.farmer.be.service.mapper.CropMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.Crop}.
 */
@Service
@Transactional
public class CropServiceImpl implements CropService {

    private static final Logger LOG = LoggerFactory.getLogger(CropServiceImpl.class);

    private final CropRepository cropRepository;

    private final CropMapper cropMapper;

    public CropServiceImpl(CropRepository cropRepository, CropMapper cropMapper) {
        this.cropRepository = cropRepository;
        this.cropMapper = cropMapper;
    }

    @Override
    public CropDTO save(CropDTO cropDTO) {
        LOG.debug("Request to save Crop : {}", cropDTO);
        Crop crop = cropMapper.toEntity(cropDTO);
        crop = cropRepository.save(crop);
        return cropMapper.toDto(crop);
    }

    @Override
    public CropDTO update(CropDTO cropDTO) {
        LOG.debug("Request to update Crop : {}", cropDTO);
        Crop crop = cropMapper.toEntity(cropDTO);
        crop = cropRepository.save(crop);
        return cropMapper.toDto(crop);
    }

    @Override
    public Optional<CropDTO> partialUpdate(CropDTO cropDTO) {
        LOG.debug("Request to partially update Crop : {}", cropDTO);

        return cropRepository
            .findById(cropDTO.getId())
            .map(existingCrop -> {
                cropMapper.partialUpdate(existingCrop, cropDTO);

                return existingCrop;
            })
            .map(cropRepository::save)
            .map(cropMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CropDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Crops");
        return cropRepository.findAll(pageable).map(cropMapper::toDto);
    }

    /**
     *  Get all the crops where DemandData is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CropDTO> findAllWhereDemandDataIsNull() {
        LOG.debug("Request to get all crops where DemandData is null");
        return StreamSupport.stream(cropRepository.findAll().spliterator(), false)
            .filter(crop -> crop.getDemandData() == null)
            .map(cropMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CropDTO> findOne(Long id) {
        LOG.debug("Request to get Crop : {}", id);
        return cropRepository.findById(id).map(cropMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Crop : {}", id);
        cropRepository.deleteById(id);
    }
}
