package io.github.devcaioalves.projetodacbackifmarket.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.devcaioalves.projetodacbackifmarket.docs.ItemDoc;
import io.github.devcaioalves.projetodacbackifmarket.dto.item.ItemAlterDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.item.ItemCreateDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.item.ItemResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.repositories.projection.ItemProjection;
import io.github.devcaioalves.projetodacbackifmarket.services.ItemService;
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
@RequestMapping("/br/com/ifmarket/itemmanagent/v1")
public class ItemController implements ItemDoc {

    private final ItemService itemService;
    private final ObjectMapper objectMapper;

    @PostMapping("/criar-item")
    public ResponseEntity<ItemResponseDTO> criarItem(@RequestBody @Valid ItemCreateDTO itemCreateDTO) {
        ItemResponseDTO item = itemService.criarItem(itemCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @GetMapping("/buscar-item/{id}")
    public ResponseEntity<ItemResponseDTO> buscarItem(@PathVariable Long id) {
        ItemResponseDTO item = itemService.buscarItem(id);
        return ResponseEntity.status(HttpStatus.OK).body(item);
    }

    @GetMapping("/buscar-todos-itens")
    public ResponseEntity<Page<ItemResponseDTO>> buscarTodosItens(@Parameter(hidden = true) Pageable pageable) {
        Page<ItemProjection> itens = itemService.listarTodosOsItens(pageable);
        return ResponseEntity.ok(convertToDTOPage(itens, pageable));
    }

    @PutMapping("/atualizar-item/{id}")
    public ResponseEntity<ItemResponseDTO> atualizarItem(@PathVariable Long id, @RequestBody @Valid ItemAlterDTO dto) {
        ItemResponseDTO item = itemService.atualizarItem(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(item);
    }

    @DeleteMapping("/deleta-item/{id}")
    public ResponseEntity<ItemResponseDTO> deletarItem(@PathVariable Long id) {
        itemService.deletarItem(id);
        return ResponseEntity.noContent().build();
    }

    private Page<ItemResponseDTO> convertToDTOPage(Page<ItemProjection> itens, Pageable pageable) {
        List<ItemResponseDTO> content = itens.getContent().stream()
                .map(post -> objectMapper.convertValue(post, ItemResponseDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageable, itens.getTotalElements());
    }

}
