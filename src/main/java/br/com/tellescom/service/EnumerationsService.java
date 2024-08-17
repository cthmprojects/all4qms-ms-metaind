package br.com.tellescom.service;

import br.com.tellescom.domain.enumeration.EnumTemporal;
import br.com.tellescom.domain.enumeration.EnumTendencia;
import br.com.tellescom.domain.enumeration.EnumUnidadeMedida;
import br.com.tellescom.domain.response.EnumItemResponse;
import br.com.tellescom.web.rest.errors.BadRequestAlertException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EnumerationsService {

    private final Logger log = LoggerFactory.getLogger(EnumerationsService.class);

    private static final String ENTITY_NAME = "all4QmsMsInfoDocEnumerations";

    public EnumerationsService() {}

    public List<EnumItemResponse> listEnum(String tipo) {
        log.debug("Request to get list of enum " + tipo);
        if (tipo.equalsIgnoreCase("temporal")) {
            return EnumTemporal.toEnumItemResponseList();
        } else if (tipo.equalsIgnoreCase(("tendencia"))) {
            return EnumTendencia.toEnumItemResponseList();
        } else if (tipo.equalsIgnoreCase(("unidade"))) {
            return EnumUnidadeMedida.toEnumItemResponseList();
        } else {
            throw new BadRequestAlertException("Opção não encontrada.", ENTITY_NAME, "typenotexists");
        }
    }
}
