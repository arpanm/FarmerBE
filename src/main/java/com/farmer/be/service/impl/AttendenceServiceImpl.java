package com.farmer.be.service.impl;

import com.farmer.be.domain.Attendence;
import com.farmer.be.repository.AttendenceRepository;
import com.farmer.be.service.AttendenceService;
import com.farmer.be.service.dto.AttendenceDTO;
import com.farmer.be.service.mapper.AttendenceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.Attendence}.
 */
@Service
@Transactional
public class AttendenceServiceImpl implements AttendenceService {

    private static final Logger LOG = LoggerFactory.getLogger(AttendenceServiceImpl.class);

    private final AttendenceRepository attendenceRepository;

    private final AttendenceMapper attendenceMapper;

    public AttendenceServiceImpl(AttendenceRepository attendenceRepository, AttendenceMapper attendenceMapper) {
        this.attendenceRepository = attendenceRepository;
        this.attendenceMapper = attendenceMapper;
    }

    @Override
    public AttendenceDTO save(AttendenceDTO attendenceDTO) {
        LOG.debug("Request to save Attendence : {}", attendenceDTO);
        Attendence attendence = attendenceMapper.toEntity(attendenceDTO);
        attendence = attendenceRepository.save(attendence);
        return attendenceMapper.toDto(attendence);
    }

    @Override
    public AttendenceDTO update(AttendenceDTO attendenceDTO) {
        LOG.debug("Request to update Attendence : {}", attendenceDTO);
        Attendence attendence = attendenceMapper.toEntity(attendenceDTO);
        attendence = attendenceRepository.save(attendence);
        return attendenceMapper.toDto(attendence);
    }

    @Override
    public Optional<AttendenceDTO> partialUpdate(AttendenceDTO attendenceDTO) {
        LOG.debug("Request to partially update Attendence : {}", attendenceDTO);

        return attendenceRepository
            .findById(attendenceDTO.getId())
            .map(existingAttendence -> {
                attendenceMapper.partialUpdate(existingAttendence, attendenceDTO);

                return existingAttendence;
            })
            .map(attendenceRepository::save)
            .map(attendenceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttendenceDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Attendences");
        return attendenceRepository.findAll(pageable).map(attendenceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AttendenceDTO> findOne(Long id) {
        LOG.debug("Request to get Attendence : {}", id);
        return attendenceRepository.findById(id).map(attendenceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Attendence : {}", id);
        attendenceRepository.deleteById(id);
    }
}
