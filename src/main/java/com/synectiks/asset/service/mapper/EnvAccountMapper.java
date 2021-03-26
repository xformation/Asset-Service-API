package com.synectiks.asset.service.mapper;

import com.synectiks.asset.domain.*;
import com.synectiks.asset.service.dto.EnvAccountDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EnvAccount} and its DTO {@link EnvAccountDTO}.
 */
@Mapper(componentModel = "spring", uses = { EnvironmentMapper.class })
public interface EnvAccountMapper extends EntityMapper<EnvAccountDTO, EnvAccount> {
  @Mapping(target = "environment", source = "environment", qualifiedByName = "id")
  EnvAccountDTO toDto(EnvAccount s);

  @Named("id")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  EnvAccountDTO toDtoId(EnvAccount envAccount);
}
