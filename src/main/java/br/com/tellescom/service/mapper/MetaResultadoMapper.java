package br.com.tellescom.service.mapper;

import br.com.tellescom.domain.Meta;
import br.com.tellescom.domain.MetaResultado;
import br.com.tellescom.service.dto.MetaDTO;
import br.com.tellescom.service.dto.MetaResultadoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MetaResultado} and its DTO {@link MetaResultadoDTO}.
 */
@Mapper(componentModel = "spring")
public interface MetaResultadoMapper extends EntityMapper<MetaResultadoDTO, MetaResultado> {
    @Mapping(target = "meta", source = "meta", qualifiedByName = "metaId")
    MetaResultadoDTO toDto(MetaResultado s);

    @Named("metaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MetaDTO toDtoMetaId(Meta meta);
}
