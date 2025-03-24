package com.farmer.be.service.mapper;

import com.farmer.be.domain.SessionContext;
import com.farmer.be.service.dto.SessionContextDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SessionContext} and its DTO {@link SessionContextDTO}.
 */
@Mapper(componentModel = "spring")
public interface SessionContextMapper extends EntityMapper<SessionContextDTO, SessionContext> {}
