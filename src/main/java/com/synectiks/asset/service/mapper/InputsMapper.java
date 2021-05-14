package com.synectiks.asset.service.mapper;


import com.synectiks.asset.domain.*;
import com.synectiks.asset.service.dto.InputsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Inputs} and its DTO {@link InputsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InputsMapper extends EntityMapper<InputsDTO, Inputs> {



    default Inputs fromId(Long id) {
        if (id == null) {
            return null;
        }
        Inputs inputs = new Inputs();
        inputs.setId(id);
        return inputs;
    }
}
