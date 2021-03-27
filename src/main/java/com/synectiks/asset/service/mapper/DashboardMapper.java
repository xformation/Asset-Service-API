package com.synectiks.asset.service.mapper;


import com.synectiks.asset.domain.*;
import com.synectiks.asset.service.dto.DashboardDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Dashboard} and its DTO {@link DashboardDTO}.
 */
@Mapper(componentModel = "spring", uses = {InputMapper.class})
public interface DashboardMapper extends EntityMapper<DashboardDTO, Dashboard> {

    @Mapping(source = "input.id", target = "inputId")
    DashboardDTO toDto(Dashboard dashboard);

    @Mapping(source = "inputId", target = "input")
    Dashboard toEntity(DashboardDTO dashboardDTO);

    default Dashboard fromId(Long id) {
        if (id == null) {
            return null;
        }
        Dashboard dashboard = new Dashboard();
        dashboard.setId(id);
        return dashboard;
    }
}
