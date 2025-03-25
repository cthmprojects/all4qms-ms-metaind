package br.com.tellescom.service.mapper;

import br.com.tellescom.domain.AcaoCritica;
import br.com.tellescom.domain.IndicadorCritica;
import br.com.tellescom.service.dto.AcaoCriticaDTO;
import br.com.tellescom.service.dto.IndicadorCriticaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AcaoCritica} and its DTO {@link AcaoCriticaDTO}.
 */
@Mapper(componentModel = "spring")
public interface AcaoCriticaMapper extends EntityMapper<AcaoCriticaDTO, AcaoCritica> {
    @Mapping(target = "indicadorCritica", source = "indicadorCritica", qualifiedByName = "indicadorCriticaId")
    AcaoCriticaDTO toDto(AcaoCritica s);

    @Named("indicadorCriticaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    IndicadorCriticaDTO toDtoIndicadorCriticaId(IndicadorCritica indicadorCritica);
}
