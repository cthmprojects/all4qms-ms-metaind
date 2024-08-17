package br.com.tellescom.domain.request;

import br.com.tellescom.service.dto.MetaResultadoDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetaResultadoRequest {

    private MetaResultadoDTO metaResultadoDTO;
    private List<MultipartFile> anexos;
}
