package br.com.tellescom.service.mapper;

import br.com.tellescom.domain.IndicadorCritica;
import br.com.tellescom.service.dto.IndicadorCriticaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link IndicadorCritica} and its DTO {@link IndicadorCriticaDTO}.
 */
@Mapper(componentModel = "spring")
public interface IndicadorCriticaMapper extends EntityMapper<IndicadorCriticaDTO, IndicadorCritica> {}
