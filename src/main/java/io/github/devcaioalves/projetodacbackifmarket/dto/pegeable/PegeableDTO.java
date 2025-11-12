package io.github.devcaioalves.projetodacbackifmarket.dto.pegeable;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.devcaioalves.projetodacbackifmarket.dto.usuario.UsuarioResponseDTO;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class PegeableDTO {
    private List<UsuarioResponseDTO> content = new ArrayList<>();
    private boolean first;
    private boolean last;
    @JsonProperty("page")
    private int number;
    private int size;
    @JsonProperty("pageElements")
    private int numberOfPages;
    private int totalPages;
    private int totalElements;
}
