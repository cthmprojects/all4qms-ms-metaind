package br.com.tellescom.domain.request;

import org.springframework.web.multipart.MultipartFile;

public record MetaAnexoRequest(MultipartFile arquivo, Long idMetaResultado) {}
