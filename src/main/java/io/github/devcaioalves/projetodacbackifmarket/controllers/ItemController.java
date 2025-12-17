package io.github.devcaioalves.projetodacbackifmarket.controllers;

import io.github.devcaioalves.projetodacbackifmarket.docs.ItemDoc;
import io.github.devcaioalves.projetodacbackifmarket.dto.item.ItemAlterDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.item.ItemCreateDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.item.ItemResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.services.ItemService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/br/com/ifmarket/itemmanagent/v1")
public class ItemController implements ItemDoc {

    private final ItemService itemService;

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

    @GetMapping("/buscar")
    public ResponseEntity<List<ItemResponseDTO>> buscarProdutos(@RequestParam String termo) {
        List<ItemResponseDTO> encontrados = itemService.buscarPorTitulo(termo);
        return ResponseEntity.ok(encontrados);
    }

    @GetMapping("/buscar-todos-itens")
    public ResponseEntity<Page<ItemResponseDTO>> buscarTodosItens(
            @Parameter(hidden = true) Pageable pageable,
            @RequestParam(required = false) Long categoriaId) {

        Page<ItemResponseDTO> itens = itemService.listarTodosOsItens(pageable, categoriaId);
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/buscar-todos-itens-usuario/{usuarioId}")
    public ResponseEntity<Page<ItemResponseDTO>> buscarTodosItensUsuario(
            @Parameter(hidden = true) Pageable pageable,
            @RequestParam(required = false) Long categoriaId,
            @PathVariable Long usuarioId) {

        Page<ItemResponseDTO> itens = itemService.listarTodosOsItensUsuario(pageable, categoriaId, usuarioId);
        return ResponseEntity.ok(itens);
    }

    @PutMapping("/atualizar-item/{id}")
    public ResponseEntity<ItemResponseDTO> atualizarItem(@PathVariable Long id, @RequestBody @Valid ItemAlterDTO dto) {
        ItemResponseDTO item = itemService.atualizarItem(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(item);
    }

    @DeleteMapping("/deleta-item/{id}")
    public ResponseEntity<Void> deletarItem(@PathVariable Long id) {
        itemService.deletarItem(id);
        return ResponseEntity.noContent().build();
    }
}

