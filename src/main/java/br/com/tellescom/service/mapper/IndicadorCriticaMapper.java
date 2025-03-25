package br.com.tellescom.service.mapper;

import br.com.tellescom.domain.IndicadorCritica;
import br.com.tellescom.service.AcaoCriticaService;
import br.com.tellescom.service.dto.AcaoCriticaDTO;
import br.com.tellescom.service.dto.IndicadorCriticaDTO;
import org.mapstruct.*;

import java.util.Set;

/**
 * Mapper for the entity {@link IndicadorCritica} and its DTO {@link IndicadorCriticaDTO}.
 */
@Mapper(componentModel = "spring")
public interface IndicadorCriticaMapper extends EntityMapper<IndicadorCriticaDTO, IndicadorCritica> {

    @Mapping(target = "acaoCriticas", source = "id", qualifiedByName = "findAcaoCriticas")
    IndicadorCriticaDTO toDto(IndicadorCritica indicadorCritica, @Context AcaoCriticaService acaoCriticaService);

    @Named("findAcaoCriticas")
    default Set<AcaoCriticaDTO> findAcaoCriticas(Long indicadorCriticaId, @Context AcaoCriticaService acaoCriticaService) {
        return acaoCriticaService.findAllByIndicadorCritica(indicadorCriticaId);
    }
}

