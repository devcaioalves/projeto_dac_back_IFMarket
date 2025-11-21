package io.github.devcaioalves.projetodacbackifmarket.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.devcaioalves.projetodacbackifmarket.docs.FotoDoc;
import io.github.devcaioalves.projetodacbackifmarket.dto.categoria.CategoriaCreateDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.categoria.CategoriaResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.fotoitem.FotoAlterDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.fotoitem.FotoCreateDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.fotoitem.FotoResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.repositories.projection.FotoProjection;
import io.github.devcaioalves.projetodacbackifmarket.services.FotoService;
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
@RequestMapping("/br/com/ifmarket/fotomanagent/v1")
public class FotoController implements FotoDoc {
    private final FotoService fotoService;
    private final ObjectMapper objectMapper;

    @PostMapping("/criar-foto")
    public ResponseEntity<FotoResponseDTO> criarFoto(@RequestBody @Valid FotoCreateDTO fotoCreateDTO) {
        FotoResponseDTO foto = fotoService.criarFoto(fotoCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(foto);
    }

    @Override
    public ResponseEntity<CategoriaResponseDTO> criarCategoria(CategoriaCreateDTO categoriaCreateDTO) {
        return null;
    }

    @GetMapping("/buscar-Foto/{id}")
    public ResponseEntity<FotoResponseDTO> buscarFoto(@PathVariable Long id) {
        FotoResponseDTO foto = fotoService.buscarFoto(id);
        return ResponseEntity.status(HttpStatus.OK).body(foto);
    }

    @GetMapping("/buscar-todas-fotos")
    public ResponseEntity<Page<FotoResponseDTO>> buscarTodasFotos(@Parameter(hidden = true) Pageable pageable) {
        Page<FotoProjection> fotos = fotoService.listarTodasAsFotos(pageable);
        return ResponseEntity.ok(convertToDTOPage(fotos, pageable));
    }

    @PutMapping("/atualizar-foto/{id}")
    public ResponseEntity<FotoResponseDTO> atualizarFoto(@PathVariable Long id, @RequestBody @Valid FotoAlterDTO dto) {
        FotoResponseDTO foto = fotoService.atualizarFoto(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(foto);
    }

    @DeleteMapping("/deleta-foto/{id}")
    public ResponseEntity<FotoResponseDTO> deletarFoto(@PathVariable Long id) {
        fotoService.deletarFoto(id);
        return ResponseEntity.noContent().build();
    }

    private Page<FotoResponseDTO> convertToDTOPage(Page<FotoProjection> fotos, Pageable pageable) {
        List<FotoResponseDTO> content = fotos.getContent().stream()
                .map(post -> objectMapper.convertValue(post, FotoResponseDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageable, fotos.getTotalElements());
    }

}
