package com.farmer.be.service.impl;

import com.farmer.be.domain.SessionContext;
import com.farmer.be.repository.SessionContextRepository;
import com.farmer.be.service.SessionContextService;
import com.farmer.be.service.dto.SessionContextDTO;
import com.farmer.be.service.mapper.SessionContextMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.farmer.be.domain.SessionContext}.
 */
@Service
@Transactional
public class SessionContextServiceImpl implements SessionContextService {

    private static final Logger LOG = LoggerFactory.getLogger(SessionContextServiceImpl.class);

    private final SessionContextRepository sessionContextRepository;

    private final SessionContextMapper sessionContextMapper;

    public SessionContextServiceImpl(SessionContextRepository sessionContextRepository, SessionContextMapper sessionContextMapper) {
        this.sessionContextRepository = sessionContextRepository;
        this.sessionContextMapper = sessionContextMapper;
    }

    @Override
    public SessionContextDTO save(SessionContextDTO sessionContextDTO) {
        LOG.debug("Request to save SessionContext : {}", sessionContextDTO);
        SessionContext sessionContext = sessionContextMapper.toEntity(sessionContextDTO);
        sessionContext = sessionContextRepository.save(sessionContext);
        return sessionContextMapper.toDto(sessionContext);
    }

    @Override
    public SessionContextDTO update(SessionContextDTO sessionContextDTO) {
        LOG.debug("Request to update SessionContext : {}", sessionContextDTO);
        SessionContext sessionContext = sessionContextMapper.toEntity(sessionContextDTO);
        sessionContext = sessionContextRepository.save(sessionContext);
        return sessionContextMapper.toDto(sessionContext);
    }

    @Override
    public Optional<SessionContextDTO> partialUpdate(SessionContextDTO sessionContextDTO) {
        LOG.debug("Request to partially update SessionContext : {}", sessionContextDTO);

        return sessionContextRepository
            .findById(sessionContextDTO.getId())
            .map(existingSessionContext -> {
                sessionContextMapper.partialUpdate(existingSessionContext, sessionContextDTO);

                return existingSessionContext;
            })
            .map(sessionContextRepository::save)
            .map(sessionContextMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SessionContextDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all SessionContexts");
        return sessionContextRepository.findAll(pageable).map(sessionContextMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SessionContextDTO> findOne(Long id) {
        LOG.debug("Request to get SessionContext : {}", id);
        return sessionContextRepository.findById(id).map(sessionContextMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete SessionContext : {}", id);
        sessionContextRepository.deleteById(id);
    }
}
