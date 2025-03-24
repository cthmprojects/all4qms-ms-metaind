package br.com.tellescom.service;

import br.com.tellescom.config.ApiUrlsConfig;
import br.com.tellescom.domain.response.UsuarioResponse;
import br.com.tellescom.service.dto.UsuarioDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestTemplateService {

    private static final Logger log = LoggerFactory.getLogger(RestTemplateService.class);
    private final RestTemplate restTemplate;
    private final ApiUrlsConfig apiUrlsConfig;

    public RestTemplateService(ApiUrlsConfig apiUrlsConfig) {
        this.restTemplate = new RestTemplate();
        this.apiUrlsConfig = apiUrlsConfig;
    }

    public List<UsuarioResponse> getAllSgqUsers() {
        log.debug("Buscando Lista de Usuários SGQ do Gateway");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            HttpEntity<UsuarioResponse> request = new HttpEntity<>(headers);
            ResponseEntity<List<UsuarioResponse>> response = restTemplate.exchange(
                apiUrlsConfig.getGatewayUsuario() + "/sqg-users",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {}
            );
            return response.getBody();
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar buscar os usuarios no Gateway");
            return new ArrayList<>();
        }
    }

    public UsuarioDTO getUsuarioById(Long id) {
        log.debug("Buscando Usuários do Gateway");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            HttpEntity<UsuarioDTO> request = new HttpEntity<>(headers);
            ResponseEntity<UsuarioDTO> response = restTemplate.exchange(
                apiUrlsConfig.getGatewayUsuario() + "/" + id,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {}
            );
            return response.getBody();
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar buscar o usuario no Gateway");
            return null;
        }
    }
}
