package com.synectiks.asset.service.mapper;


import com.synectiks.asset.domain.*;
import com.synectiks.asset.service.dto.AccountsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Accounts} and its DTO {@link AccountsDTO}.
 */
@Mapper(componentModel = "spring", uses = {OrganizationMapper.class, OrganizationalUnitMapper.class})
public interface AccountsMapper extends EntityMapper<AccountsDTO, Accounts> {

    @Mapping(source = "organization.id", target = "organizationId")
    @Mapping(source = "organizationalUnit.id", target = "organizationalUnitId")
    AccountsDTO toDto(Accounts accounts);

    @Mapping(source = "organizationId", target = "organization")
    @Mapping(source = "organizationalUnitId", target = "organizationalUnit")
    Accounts toEntity(AccountsDTO accountsDTO);

    default Accounts fromId(Long id) {
        if (id == null) {
            return null;
        }
        Accounts accounts = new Accounts();
        accounts.setId(id);
        return accounts;
    }
}
