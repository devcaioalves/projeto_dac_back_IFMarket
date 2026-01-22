package io.github.devcaioalves.projetodacbackifmarket.docs;

import io.github.devcaioalves.projetodacbackifmarket.dto.fotoItem.FotoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface FotoDoc {

    @Operation(
            summary = "Cria uma nova foto",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Foto criada com sucesso.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FotoResponseDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Dados incorretos.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)

                    ))
            }
    )
    ResponseEntity<FotoResponseDTO> criarFoto(
            @RequestParam("arquivo") MultipartFile arquivo,
            @RequestParam("itemId") Long itemId
    );

    @Operation(
            summary = "Buscar foto pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Foto encontrada.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FotoResponseDTO.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Foto não encontrada.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
            }
    )
    ResponseEntity<FotoResponseDTO> buscarFoto(@PathVariable Long id);

    @Operation(
            summary = "Deletar uma foto",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Foto deletada com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Foto não encontrada.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
            }
    )
    ResponseEntity<Void> deletarFoto(@PathVariable Long id);

}
