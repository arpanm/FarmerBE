package com.farmer.be.service.impl;

import com.farmer.be.domain.CarouselContent;
import com.farmer.be.repository.CarouselContentRepository;
import com.farmer.be.service.CarouselContentService;
import com.farmer.be.service.dto.CarouselContentDTO;
import com.farmer.be.service.mapper.CarouselContentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.CarouselContent}.
 */
@Service
@Transactional
public class CarouselContentServiceImpl implements CarouselContentService {

    private static final Logger LOG = LoggerFactory.getLogger(CarouselContentServiceImpl.class);

    private final CarouselContentRepository carouselContentRepository;

    private final CarouselContentMapper carouselContentMapper;

    public CarouselContentServiceImpl(CarouselContentRepository carouselContentRepository, CarouselContentMapper carouselContentMapper) {
        this.carouselContentRepository = carouselContentRepository;
        this.carouselContentMapper = carouselContentMapper;
    }

    @Override
    public CarouselContentDTO save(CarouselContentDTO carouselContentDTO) {
        LOG.debug("Request to save CarouselContent : {}", carouselContentDTO);
        CarouselContent carouselContent = carouselContentMapper.toEntity(carouselContentDTO);
        carouselContent = carouselContentRepository.save(carouselContent);
        return carouselContentMapper.toDto(carouselContent);
    }

    @Override
    public CarouselContentDTO update(CarouselContentDTO carouselContentDTO) {
        LOG.debug("Request to update CarouselContent : {}", carouselContentDTO);
        CarouselContent carouselContent = carouselContentMapper.toEntity(carouselContentDTO);
        carouselContent = carouselContentRepository.save(carouselContent);
        return carouselContentMapper.toDto(carouselContent);
    }

    @Override
    public Optional<CarouselContentDTO> partialUpdate(CarouselContentDTO carouselContentDTO) {
        LOG.debug("Request to partially update CarouselContent : {}", carouselContentDTO);

        return carouselContentRepository
            .findById(carouselContentDTO.getId())
            .map(existingCarouselContent -> {
                carouselContentMapper.partialUpdate(existingCarouselContent, carouselContentDTO);

                return existingCarouselContent;
            })
            .map(carouselContentRepository::save)
            .map(carouselContentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CarouselContentDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all CarouselContents");
        return carouselContentRepository.findAll(pageable).map(carouselContentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CarouselContentDTO> findOne(Long id) {
        LOG.debug("Request to get CarouselContent : {}", id);
        return carouselContentRepository.findById(id).map(carouselContentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete CarouselContent : {}", id);
        carouselContentRepository.deleteById(id);
    }
}
