package io.github.devcaioalves.projetodacbackifmarket.docs;

import io.github.devcaioalves.projetodacbackifmarket.dto.fotoItem.FotoAlterDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.fotoItem.FotoCreateDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.fotoItem.FotoResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.repositories.projection.FotoProjection;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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
    ResponseEntity<FotoResponseDTO> criarFoto(@RequestBody @Valid FotoCreateDTO fotoCreateDTO);

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
            summary = "Listar todas as fotos",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de fotos retornada.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FotoProjection.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Fotos não encontradas.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
            }
    )
    ResponseEntity<Page<FotoResponseDTO>> buscarTodasFotos(@Parameter(hidden = true) Pageable pageable);

    @Operation(
            summary = "Atualizar foto",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Foto atualizada com sucesso.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FotoResponseDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Dados incorretos para atualização.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
            }
    )
    ResponseEntity<FotoResponseDTO> atualizarFoto(@PathVariable Long id, @RequestBody @Valid FotoAlterDTO dto);

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
    ResponseEntity<FotoResponseDTO> deletarFoto(@PathVariable Long id);

}
