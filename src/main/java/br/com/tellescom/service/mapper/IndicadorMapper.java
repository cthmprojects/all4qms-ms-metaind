package br.com.tellescom.service.mapper;

import br.com.tellescom.domain.Indicador;
import br.com.tellescom.service.dto.IndicadorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Indicador} and its DTO {@link IndicadorDTO}.
 */
@Mapper(componentModel = "spring")
public interface IndicadorMapper extends EntityMapper<IndicadorDTO, Indicador> {}
