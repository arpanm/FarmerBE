package com.farmer.be.service.impl;

import com.farmer.be.domain.BannerContent;
import com.farmer.be.repository.BannerContentRepository;
import com.farmer.be.service.BannerContentService;
import com.farmer.be.service.dto.BannerContentDTO;
import com.farmer.be.service.mapper.BannerContentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.BannerContent}.
 */
@Service
@Transactional
public class BannerContentServiceImpl implements BannerContentService {

    private static final Logger LOG = LoggerFactory.getLogger(BannerContentServiceImpl.class);

    private final BannerContentRepository bannerContentRepository;

    private final BannerContentMapper bannerContentMapper;

    public BannerContentServiceImpl(BannerContentRepository bannerContentRepository, BannerContentMapper bannerContentMapper) {
        this.bannerContentRepository = bannerContentRepository;
        this.bannerContentMapper = bannerContentMapper;
    }

    @Override
    public BannerContentDTO save(BannerContentDTO bannerContentDTO) {
        LOG.debug("Request to save BannerContent : {}", bannerContentDTO);
        BannerContent bannerContent = bannerContentMapper.toEntity(bannerContentDTO);
        bannerContent = bannerContentRepository.save(bannerContent);
        return bannerContentMapper.toDto(bannerContent);
    }

    @Override
    public BannerContentDTO update(BannerContentDTO bannerContentDTO) {
        LOG.debug("Request to update BannerContent : {}", bannerContentDTO);
        BannerContent bannerContent = bannerContentMapper.toEntity(bannerContentDTO);
        bannerContent = bannerContentRepository.save(bannerContent);
        return bannerContentMapper.toDto(bannerContent);
    }

    @Override
    public Optional<BannerContentDTO> partialUpdate(BannerContentDTO bannerContentDTO) {
        LOG.debug("Request to partially update BannerContent : {}", bannerContentDTO);

        return bannerContentRepository
            .findById(bannerContentDTO.getId())
            .map(existingBannerContent -> {
                bannerContentMapper.partialUpdate(existingBannerContent, bannerContentDTO);

                return existingBannerContent;
            })
            .map(bannerContentRepository::save)
            .map(bannerContentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BannerContentDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all BannerContents");
        return bannerContentRepository.findAll(pageable).map(bannerContentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BannerContentDTO> findOne(Long id) {
        LOG.debug("Request to get BannerContent : {}", id);
        return bannerContentRepository.findById(id).map(bannerContentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete BannerContent : {}", id);
        bannerContentRepository.deleteById(id);
    }
}
