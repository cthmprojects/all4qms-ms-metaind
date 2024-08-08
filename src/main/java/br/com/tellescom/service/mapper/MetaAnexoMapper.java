package br.com.tellescom.service.mapper;

import br.com.tellescom.domain.MetaAnexo;
import br.com.tellescom.domain.MetaResultado;
import br.com.tellescom.service.dto.MetaAnexoDTO;
import br.com.tellescom.service.dto.MetaResultadoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MetaAnexo} and its DTO {@link MetaAnexoDTO}.
 */
@Mapper(componentModel = "spring")
public interface MetaAnexoMapper extends EntityMapper<MetaAnexoDTO, MetaAnexo> {
    @Mapping(target = "metaResultado", source = "metaResultado", qualifiedByName = "metaResultadoId")
    MetaAnexoDTO toDto(MetaAnexo s);

    @Named("metaResultadoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MetaResultadoDTO toDtoMetaResultadoId(MetaResultado metaResultado);
}
