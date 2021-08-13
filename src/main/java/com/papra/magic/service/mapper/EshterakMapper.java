package com.papra.magic.service.mapper;

import com.papra.magic.domain.*;
import com.papra.magic.service.dto.EshterakDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Eshterak} and its DTO {@link EshterakDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EshterakMapper extends EntityMapper<EshterakDTO, Eshterak> {}
