package io.github.devcaioalves.projetodacbackifmarket.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.devcaioalves.projetodacbackifmarket.docs.CategoriaDoc;
import io.github.devcaioalves.projetodacbackifmarket.dto.categoria.CategoriaAlterDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.categoria.CategoriaCreateDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.categoria.CategoriaResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.repositories.projection.CategoriaProjection;
import io.github.devcaioalves.projetodacbackifmarket.services.CategoriaService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/br/com/ifmarket/categoriamanagent/v1")
public class CategoriaController implements CategoriaDoc {

    private final CategoriaService categoriaService;
    private final ObjectMapper objectMapper;

    @PostMapping("/criar-categoria")
    public ResponseEntity<CategoriaResponseDTO> criarCategoria(@RequestBody @Valid CategoriaCreateDTO categoriaCreateDTO) {
        CategoriaResponseDTO categoria = categoriaService.criarCategoria(categoriaCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }

    @GetMapping("/buscar-categoria/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscarCategoria(@PathVariable Long id) {
        CategoriaResponseDTO categoria = categoriaService.buscarCategoria(id);
        return ResponseEntity.status(HttpStatus.OK).body(categoria);
    }

    @GetMapping("/buscar-todas-categorias")
    public ResponseEntity<Page<CategoriaResponseDTO>> buscarTodasCategorias(@Parameter(hidden = true) Pageable pageable) {
        Page<CategoriaProjection> categorias = categoriaService.listarTodasAsCategorias(pageable);
        return ResponseEntity.ok(convertToDTOPage(categorias, pageable));
    }

    @PutMapping("/atualizar-categoria/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizarCategoria(@PathVariable Long id, @RequestBody @Valid CategoriaAlterDTO dto) {
        CategoriaResponseDTO categoria = categoriaService.atualizarCategoria(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(categoria);
    }

    @DeleteMapping("/deleta-categoria/{id}")
    public ResponseEntity<CategoriaResponseDTO> deletarCategoria(@PathVariable Long id) {
        categoriaService.deletarCategoria(id);
        return ResponseEntity.noContent().build();
    }

    private Page<CategoriaResponseDTO> convertToDTOPage(Page<CategoriaProjection> categorias, Pageable pageable) {
        List<CategoriaResponseDTO> content = categorias.getContent().stream()
                .map(post -> objectMapper.convertValue(post, CategoriaResponseDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageable, categorias.getTotalElements());
    }
}
