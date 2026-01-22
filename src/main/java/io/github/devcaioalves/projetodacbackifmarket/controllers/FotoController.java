package io.github.devcaioalves.projetodacbackifmarket.controllers;

import io.github.devcaioalves.projetodacbackifmarket.docs.FotoDoc;
import io.github.devcaioalves.projetodacbackifmarket.dto.fotoItem.FotoResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.services.FotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/br/com/ifmarket/fotomanagent/v1")
public class FotoController implements FotoDoc {

    private final FotoService fotoService;

    @PostMapping(
            value = "/criar-foto",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<FotoResponseDTO> criarFoto(
            @RequestParam("arquivo") MultipartFile arquivo,
            @RequestParam("itemId") Long itemId
    ) {
        FotoResponseDTO foto = fotoService.criarFoto(arquivo, itemId);
        return ResponseEntity.status(HttpStatus.CREATED).body(foto);
    }

    @GetMapping("/buscar-foto/{id}")
    public ResponseEntity<FotoResponseDTO> buscarFoto(@PathVariable Long id) {
        return ResponseEntity.ok(fotoService.buscarFoto(id));
    }

    @DeleteMapping("/deleta-foto/{id}")
    public ResponseEntity<Void> deletarFoto(@PathVariable Long id) {
        fotoService.deletarFoto(id);
        return ResponseEntity.noContent().build();
    }
}

