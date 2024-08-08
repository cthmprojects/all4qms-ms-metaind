package br.com.tellescom.service.mapper;

import br.com.tellescom.domain.Meta;
import br.com.tellescom.domain.MetaRecurso;
import br.com.tellescom.service.dto.MetaDTO;
import br.com.tellescom.service.dto.MetaRecursoDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MetaRecurso} and its DTO {@link MetaRecursoDTO}.
 */
@Mapper(componentModel = "spring")
public interface MetaRecursoMapper extends EntityMapper<MetaRecursoDTO, MetaRecurso> {
    @Mapping(target = "metas", source = "metas", qualifiedByName = "metaIdSet")
    MetaRecursoDTO toDto(MetaRecurso s);

    @Mapping(target = "metas", ignore = true)
    @Mapping(target = "removeMetas", ignore = true)
    MetaRecurso toEntity(MetaRecursoDTO metaRecursoDTO);

    @Named("metaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MetaDTO toDtoMetaId(Meta meta);

    @Named("metaIdSet")
    default Set<MetaDTO> toDtoMetaIdSet(Set<Meta> meta) {
        return meta.stream().map(this::toDtoMetaId).collect(Collectors.toSet());
    }
}
