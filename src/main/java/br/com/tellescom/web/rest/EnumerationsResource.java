package br.com.tellescom.web.rest;

import br.com.tellescom.domain.enumeration.EnumTemporal;
import br.com.tellescom.domain.enumeration.EnumTendencia;
import br.com.tellescom.domain.enumeration.EnumUnidadeMedida;
import br.com.tellescom.domain.response.EnumAllResponse;
import br.com.tellescom.domain.response.EnumItemResponse;
import br.com.tellescom.service.EnumerationsService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/metaind/enumeradores")
public class EnumerationsResource {

    private final Logger log = LoggerFactory.getLogger(EnumerationsResource.class);

    private static final String ENTITY_NAME = "all4QmsMsMetaIndIndicadorMeta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnumerationsService enumerationsService;

    public EnumerationsResource(EnumerationsService enumerationsService) {
        this.enumerationsService = enumerationsService;
    }

    @GetMapping("/{tipo}")
    public ResponseEntity<List<EnumItemResponse>> listEnum(@PathVariable("tipo") String tipo) {
        log.debug("Get listas Enum");
        return ResponseEntity.ok(enumerationsService.listEnum(tipo));
    }

    @GetMapping("/all")
    public ResponseEntity<EnumAllResponse> listAllEnums() {
        log.debug("Get all listas Enum");
        return ResponseEntity.ok(
            EnumAllResponse
                .builder()
                .temporal(EnumTemporal.toEnumItemResponseList())
                .tendencia(EnumTendencia.toEnumItemResponseList())
                .unidade(EnumUnidadeMedida.toEnumItemResponseList())
                .build()
        );
    }

    @GetMapping("/consulta-tipos")
    public ResponseEntity<List<String>> consultaTipo() {
        List<String> listaTipo = new ArrayList<>();
        listaTipo.add("temporal");
        listaTipo.add("tendencia");
        listaTipo.add("unidade");
        return ResponseEntity.ok(listaTipo);
    }
}
