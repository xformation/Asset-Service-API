package com.synectiks.asset.service.mapper;


import com.synectiks.asset.domain.*;
import com.synectiks.asset.service.dto.InputConfigDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link InputConfig} and its DTO {@link InputConfigDTO}.
 */
@Mapper(componentModel = "spring", uses = {AccountsMapper.class})
public interface InputConfigMapper extends EntityMapper<InputConfigDTO, InputConfig> {

    @Mapping(source = "accounts.id", target = "accountsId")
    InputConfigDTO toDto(InputConfig inputConfig);

    @Mapping(source = "accountsId", target = "accounts")
    InputConfig toEntity(InputConfigDTO inputConfigDTO);

    default InputConfig fromId(Long id) {
        if (id == null) {
            return null;
        }
        InputConfig inputConfig = new InputConfig();
        inputConfig.setId(id);
        return inputConfig;
    }
}
