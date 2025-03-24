package br.com.tellescom.service;


import br.com.tellescom.domain.enumeration.EnumTipoCritica;
import br.com.tellescom.service.dto.AcaoCriticaDTO;
import br.com.tellescom.service.dto.UsuarioDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class NotificacaoUsuarioService {

    private static final Logger LOG = LoggerFactory.getLogger(NotificacaoUsuarioService.class);

    private final JavaMailSender emailSender;

    private final RestTemplateService restTemplateService;

    @Value("${mail-service.port}")
    private String mail;

    public NotificacaoUsuarioService(JavaMailSender emailSender, RestTemplateService restTemplateService) {
        this.emailSender = emailSender;
        this.restTemplateService = restTemplateService;
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        try {
            emailSender.send(message);
            LOG.debug("Email enviado com sucesso para {}", to);
        } catch (Exception e) {
            LOG.error("Ocorreu um erro ao enviar email para {}", to);
        }
    }

    public void enviarEmailAcaoCritica(EnumTipoCritica tipoEmail, AcaoCriticaDTO acaoCriticaDTO) {
        if (tipoEmail == null || acaoCriticaDTO == null) {
            throw new IllegalArgumentException("Parâmetros tipoEmail e acaoCriticaDTO não podem ser nulos.");
        }

        String assunto = gerarAssuntoEmail(tipoEmail, acaoCriticaDTO.getDataAcao());
        String tipoAcao = gerarTipoAcao(tipoEmail);

        String textoEmail = construirCorpoEmail(acaoCriticaDTO, tipoAcao);

        String mailResponsavel = retornaEmailUsuarioResponsavel(acaoCriticaDTO.getIdResponsavel());
        if (mailResponsavel != null) {
            sendSimpleMessage(mailResponsavel, assunto, textoEmail);
        }
    }

    private String gerarAssuntoEmail(EnumTipoCritica tipoEmail, LocalDate dataAcao) {
        return switch (tipoEmail) {
            case ATUALIZAR -> "Atualização Ação de Análise Crítica para Data: " + dataAcao;
            case CANCELAR -> "Cancelada Ação de Análise Crítica para Data: " + dataAcao;
            default -> "Nova ação de Análise Crítica para Data: " + dataAcao;
        };
    }

    private String gerarTipoAcao(EnumTipoCritica tipoEmail) {
        return switch (tipoEmail) {
            case ATUALIZAR -> "Uma ação foi Atualizada, seguem as instruções abaixo:\n\n";
            case CANCELAR -> "Uma ação foi Cancelada, seguem as instruções abaixo:\n\n";
            default -> "Há uma ação para ser executada, seguem as instruções abaixo:\n\n";
        };
    }

    private String construirCorpoEmail(AcaoCriticaDTO acaoCriticaDTO, String tipoAcao) {
        return String.format("""
                Prezado(a) %s,

                %s
                Quando: %s
                O que deve ser feito: %s
                Como: %s

                Por favor entre em contato com o responsável do SGQ para qualquer dúvida ou necessidade de mudança.

                Agradecemos sua colaboração.

                Atenciosamente,
                Equipe de Gestão de Qualidade
                """,
            acaoCriticaDTO.getNomeResponsavel(),
            tipoAcao,
            acaoCriticaDTO.getAcaoCritica(),
            acaoCriticaDTO.getDataAcao(),
            acaoCriticaDTO.getInstrucaoAcao()
        );
    }

    private String retornaEmailUsuarioResponsavel(Long idUsuario) {
        UsuarioDTO usuario = restTemplateService.getUsuarioById(idUsuario);
        if (null != usuario) {
            return usuario.getEmail();
        }
        return null;
    }
}
