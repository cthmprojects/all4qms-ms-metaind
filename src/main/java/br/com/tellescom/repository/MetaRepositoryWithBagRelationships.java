package br.com.tellescom.repository;

import br.com.tellescom.domain.Meta;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface MetaRepositoryWithBagRelationships {
    Optional<Meta> fetchBagRelationships(Optional<Meta> meta);

    List<Meta> fetchBagRelationships(List<Meta> metas);

    Page<Meta> fetchBagRelationships(Page<Meta> metas);
}
