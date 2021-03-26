package com.synectiks.asset.service.mapper;

import com.synectiks.asset.domain.*;
import com.synectiks.asset.service.dto.EnvironmentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Environment} and its DTO {@link EnvironmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EnvironmentMapper extends EntityMapper<EnvironmentDTO, Environment> {
  @Named("id")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  EnvironmentDTO toDtoId(Environment environment);
}
