package com.synectiks.asset.service.mapper;

import com.synectiks.asset.domain.*;
import com.synectiks.asset.service.dto.InputDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Input} and its DTO {@link InputDTO}.
 */
@Mapper(componentModel = "spring", uses = { EnvAccountMapper.class })
public interface InputMapper extends EntityMapper<InputDTO, Input> {
  @Mapping(target = "envAccount", source = "envAccount", qualifiedByName = "id")
  InputDTO toDto(Input s);
}
