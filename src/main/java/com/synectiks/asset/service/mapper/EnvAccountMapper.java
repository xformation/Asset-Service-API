package com.synectiks.asset.service.mapper;


import com.synectiks.asset.domain.*;
import com.synectiks.asset.service.dto.EnvAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EnvAccount} and its DTO {@link EnvAccountDTO}.
 */
@Mapper(componentModel = "spring", uses = {EnvironmentMapper.class})
public interface EnvAccountMapper extends EntityMapper<EnvAccountDTO, EnvAccount> {

    @Mapping(source = "environment.id", target = "environmentId")
    EnvAccountDTO toDto(EnvAccount envAccount);

    @Mapping(source = "environmentId", target = "environment")
    EnvAccount toEntity(EnvAccountDTO envAccountDTO);

    default EnvAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        EnvAccount envAccount = new EnvAccount();
        envAccount.setId(id);
        return envAccount;
    }
}
