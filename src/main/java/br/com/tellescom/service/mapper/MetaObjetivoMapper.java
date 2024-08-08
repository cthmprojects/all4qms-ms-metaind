package br.com.tellescom.service.mapper;

import br.com.tellescom.domain.MetaObjetivo;
import br.com.tellescom.service.dto.MetaObjetivoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MetaObjetivo} and its DTO {@link MetaObjetivoDTO}.
 */
@Mapper(componentModel = "spring")
public interface MetaObjetivoMapper extends EntityMapper<MetaObjetivoDTO, MetaObjetivo> {}
