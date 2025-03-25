package br.com.tellescom.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "api.urls")
@Getter
@Setter
public class ApiUrlsConfig {

    private String gatewayUsuario;
}
