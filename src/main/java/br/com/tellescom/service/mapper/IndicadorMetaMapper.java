package br.com.tellescom.service.mapper;

import br.com.tellescom.domain.Indicador;
import br.com.tellescom.domain.IndicadorMeta;
import br.com.tellescom.service.dto.IndicadorDTO;
import br.com.tellescom.service.dto.IndicadorMetaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link IndicadorMeta} and its DTO {@link IndicadorMetaDTO}.
 */
@Mapper(componentModel = "spring")
public interface IndicadorMetaMapper extends EntityMapper<IndicadorMetaDTO, IndicadorMeta> {
    @Mapping(target = "indicador", source = "indicador", qualifiedByName = "indicadorId")
    IndicadorMetaDTO toDto(IndicadorMeta s);

    @Named("indicadorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    IndicadorDTO toDtoIndicadorId(Indicador indicador);
}
