package br.com.tellescom.service;

import br.com.tellescom.domain.MetaAnexo;
import br.com.tellescom.domain.request.MetaAnexoRequest;
import br.com.tellescom.repository.MetaAnexoRepository;
import br.com.tellescom.service.dto.MetaAnexoDTO;
import br.com.tellescom.service.dto.MetaResultadoDTO;
import br.com.tellescom.service.mapper.MetaAnexoMapper;
import br.com.tellescom.web.rest.errors.BadRequestAlertException;
import br.com.tellescom.web.rest.errors.NotFoundAlertException;
import br.com.tellescom.web.rest.errors.ServerErrorAlertException;
import com.google.common.io.Files;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Implementation for managing {@link br.com.tellescom.domain.MetaAnexo}.
 */
@Service
@Transactional
public class MetaAnexoService {

    private final Logger log = LoggerFactory.getLogger(MetaAnexoService.class);

    private final MetaAnexoRepository metaAnexoRepository;

    private final MetaAnexoMapper metaAnexoMapper;

    public MetaAnexoService(MetaAnexoRepository metaAnexoRepository, MetaAnexoMapper metaAnexoMapper) {
        this.metaAnexoRepository = metaAnexoRepository;
        this.metaAnexoMapper = metaAnexoMapper;
    }

    /**
     * Save a metaAnexo.
     *
     * @param metaAnexoDTO the entity to save.
     * @return the persisted entity.
     */
    public MetaAnexoDTO save(MetaAnexoRequest metaAnexoDTO) {
        log.debug("Request to save MetaAnexo : {}", metaAnexoDTO);
        return metaAnexoMapper.toDto(saveFile(metaAnexoDTO.arquivo(), metaAnexoDTO.idMetaResultado()));
    }

    /**
     * Update a metaAnexo.
     *
     * @param metaAnexoDTO the entity to save.
     * @return the persisted entity.
     */
    public MetaAnexoDTO update(MetaAnexoDTO metaAnexoDTO) {
        log.debug("Request to update MetaAnexo : {}", metaAnexoDTO);
        MetaAnexo metaAnexo = metaAnexoMapper.toEntity(metaAnexoDTO);
        metaAnexo = metaAnexoRepository.save(metaAnexo);
        return metaAnexoMapper.toDto(metaAnexo);
    }

    /**
     * Partially update a metaAnexo.
     *
     * @param metaAnexoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MetaAnexoDTO> partialUpdate(MetaAnexoDTO metaAnexoDTO) {
        log.debug("Request to partially update MetaAnexo : {}", metaAnexoDTO);

        return metaAnexoRepository
            .findById(metaAnexoDTO.getId())
            .map(existingMetaAnexo -> {
                metaAnexoMapper.partialUpdate(existingMetaAnexo, metaAnexoDTO);

                return existingMetaAnexo;
            })
            .map(metaAnexoRepository::save)
            .map(metaAnexoMapper::toDto);
    }

    /**
     * Get all the metaAnexos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MetaAnexoDTO> findAll() {
        log.debug("Request to get all MetaAnexos");
        return metaAnexoRepository.findAll().stream().map(metaAnexoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one metaAnexo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MetaAnexoDTO> findOne(Long id) {
        log.debug("Request to get MetaAnexo : {}", id);
        return metaAnexoRepository.findById(id).map(metaAnexoMapper::toDto);
    }

    /**
     * Delete the metaAnexo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MetaAnexo : {}", id);
        Optional<MetaAnexoDTO> optional = findOne(id);
        if (optional.isPresent()) {
            try {
                MetaAnexoDTO result = optional.orElseThrow();
                File file = new File(result.getCaminho());

                // Apagar o arquivo físico
                if (file.exists() && !file.delete()) {
                    throw new BadRequestAlertException(
                        "Não foi possível apagar o arquivo físico: " + file.getAbsolutePath(),
                        "all4QmsMsInfodocDocumentacaoAnexo",
                        "anexoarquivodeletefail"
                    );
                }

                // Apagar o registro no banco de dados
                metaAnexoRepository.deleteById(id);
            } catch (NoSuchElementException exception) {
                throw new NotFoundAlertException(exception.getMessage(), "all4QmsMsInfodocDocumentacaoAnexo", "anexoarquivonotexists");
            } catch (Exception exception) {
                throw new BadRequestAlertException(exception.getMessage(), "all4QmsMsInfodocDocumentacaoAnexo", "anexoarquivonotexists");
            }
        }
    }

    public MetaAnexo saveFile(MultipartFile arquivo, Long idMetaResultado) {
        Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>^*()%!]");

        if (regex.matcher(Objects.requireNonNull(arquivo.getOriginalFilename())).find()) {
            throw new BadRequestAlertException(
                "O nome do arquivo não deve conter caracteres especiais",
                "all4QmsMsInfodocDocumentacaoAnexo",
                "specialchar"
            );
        }

        if (arquivo.getSize() < (5 * 1024)) {
            throw new BadRequestAlertException(
                "O arquivo ter tamanho superior a 5 bytes.",
                "all4QmsMsInfodocDocumentacaoAnexo",
                "toosmall"
            );
        }

        MetaAnexoDTO anexoDTO = new MetaAnexoDTO();
        anexoDTO.setMetaResultado(new MetaResultadoDTO());
        anexoDTO.getMetaResultado().setId(idMetaResultado);
        anexoDTO.setDataCriacao(Instant.now());
        anexoDTO.setNomeOriginal(arquivo.getOriginalFilename());

        SimpleDateFormat dateTagFormat = new SimpleDateFormat("ddMMyy'_'HHmm'_'", Locale.getDefault());

        String newFileName =
            "Anexo_META_" +
            idMetaResultado +
            "_" +
            dateTagFormat.format(Date.from(anexoDTO.getDataCriacao())) +
            anexoDTO.getNomeOriginal().replaceAll(" ", "_");

        anexoDTO.setNomeFisico(newFileName);
        File serverPath = new File(caminhoBase());
        File anexoFile = new File(serverPath, newFileName);

        try {
            if (!serverPath.exists()) {
                serverPath.mkdirs();
            }
            arquivo.transferTo(anexoFile);
        } catch (IOException ioException) {
            throw new BadRequestAlertException(
                "Não foi possível salvar o arquivo: " + ioException.getMessage(),
                "all4QmsMsInfodocDocumentacaoAnexo",
                "failtosave"
            );
        }

        anexoDTO.setExtensao(Files.getFileExtension(anexoFile.getAbsolutePath()));
        anexoDTO.setCaminho(anexoFile.getAbsolutePath());

        return metaAnexoRepository.save(metaAnexoMapper.toEntity(anexoDTO));
    }

    public String caminhoBase() {
        // Obtém o separador de caminho apropriado para o sistema operacional atual
        String fileSeparator = System.getProperty("file.separator");

        String serverFilePath;
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            serverFilePath = "C:" + fileSeparator + "QMS" + fileSeparator + "Anexos" + fileSeparator + "META";
        } else {
            serverFilePath = fileSeparator + "tmp" + fileSeparator + "anexos" + fileSeparator + "meta"; // Para sistemas Unix-like
        }

        return serverFilePath;
    }

    public List<File> findFilesByIds(Long id) throws NotFoundAlertException, BadRequestAlertException {
        List<File> files = new ArrayList<>();
        List<MetaAnexo> anexos = metaAnexoRepository.findAllByMetaResultadoId(id);
        for (MetaAnexo anexo : anexos) {
            try {
                File file = new File(anexo.getCaminho());
                files.add(file);
            } catch (NoSuchElementException exception) {
                throw new NotFoundAlertException(exception.getMessage(), "all4QmsMsInfodocDocumentacaoAnexo", "anexoarquivonotexists");
            } catch (Exception exception) {
                throw new BadRequestAlertException(exception.getMessage(), "all4QmsMsInfodocDocumentacaoAnexo", "anexoarquivonotexists");
            }
        }
        return files;
    }

    public ByteArrayResource buscaZipAnexos(Long id) {
        List<File> filesToDownload = findFilesByIds(id);

        try (
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)
        ) {
            for (File file : filesToDownload) {
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zipOutputStream.putNextEntry(zipEntry);

                Files.copy(file.toPath().toFile(), zipOutputStream);

                zipOutputStream.closeEntry();
            }

            zipOutputStream.finish();

            return new ByteArrayResource(byteArrayOutputStream.toByteArray());
        } catch (BadRequestAlertException | NotFoundAlertException e) {
            throw e;
        } catch (Exception e) {
            throw new ServerErrorAlertException(
                "O servidor não conseguiu persistir os arquivos",
                "all4QmsMsMetaIndMetaAnexo",
                "docnotexists"
            );
        }
    }

    public List<MetaAnexo> findAllIdAnexosByIdMetaResultado(Long id) {
        return metaAnexoRepository.findAllByMetaResultadoId(id);
    }
}
