package com.synectiks.asset.service.mapper;


import com.synectiks.asset.domain.*;
import com.synectiks.asset.service.dto.InputDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Input} and its DTO {@link InputDTO}.
 */
@Mapper(componentModel = "spring", uses = {EnvAccountMapper.class})
public interface InputMapper extends EntityMapper<InputDTO, Input> {

    @Mapping(source = "envAccount.id", target = "envAccountId")
    InputDTO toDto(Input input);

    @Mapping(source = "envAccountId", target = "envAccount")
    Input toEntity(InputDTO inputDTO);

    default Input fromId(Long id) {
        if (id == null) {
            return null;
        }
        Input input = new Input();
        input.setId(id);
        return input;
    }
}
