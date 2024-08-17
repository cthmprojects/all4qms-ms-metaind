package br.com.tellescom.domain.response;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnumAllResponse {

    List<EnumItemResponse> temporal = new ArrayList<>();
    List<EnumItemResponse> tendencia = new ArrayList<>();
    List<EnumItemResponse> unidade = new ArrayList<>();
}
