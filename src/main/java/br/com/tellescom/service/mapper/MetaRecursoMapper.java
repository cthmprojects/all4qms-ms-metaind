package br.com.tellescom.service.mapper;

import br.com.tellescom.domain.MetaRecurso;
import br.com.tellescom.service.dto.MetaRecursoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MetaRecurso} and its DTO {@link MetaRecursoDTO}.
 */
@Mapper(componentModel = "spring")
public interface MetaRecursoMapper extends EntityMapper<MetaRecursoDTO, MetaRecurso> {}
