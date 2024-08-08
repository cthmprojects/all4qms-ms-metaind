package br.com.tellescom.service.mapper;

import br.com.tellescom.domain.Meta;
import br.com.tellescom.domain.MetaObjetivo;
import br.com.tellescom.domain.MetaRecurso;
import br.com.tellescom.service.dto.MetaDTO;
import br.com.tellescom.service.dto.MetaObjetivoDTO;
import br.com.tellescom.service.dto.MetaRecursoDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Meta} and its DTO {@link MetaDTO}.
 */
@Mapper(componentModel = "spring")
public interface MetaMapper extends EntityMapper<MetaDTO, Meta> {
    @Mapping(target = "recursos", source = "recursos", qualifiedByName = "metaRecursoIdSet")
    @Mapping(target = "metaObjetivo", source = "metaObjetivo", qualifiedByName = "metaObjetivoId")
    MetaDTO toDto(Meta s);

    @Mapping(target = "removeRecursos", ignore = true)
    Meta toEntity(MetaDTO metaDTO);

    @Named("metaRecursoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MetaRecursoDTO toDtoMetaRecursoId(MetaRecurso metaRecurso);

    @Named("metaRecursoIdSet")
    default Set<MetaRecursoDTO> toDtoMetaRecursoIdSet(Set<MetaRecurso> metaRecurso) {
        return metaRecurso.stream().map(this::toDtoMetaRecursoId).collect(Collectors.toSet());
    }

    @Named("metaObjetivoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MetaObjetivoDTO toDtoMetaObjetivoId(MetaObjetivo metaObjetivo);
}
