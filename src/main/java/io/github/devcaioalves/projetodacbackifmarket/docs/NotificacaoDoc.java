package io.github.devcaioalves.projetodacbackifmarket.docs;

import io.github.devcaioalves.projetodacbackifmarket.dto.categoria.CategoriaAlterDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.categoria.CategoriaCreateDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.categoria.CategoriaResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.notificacao.NotificacaoAlterDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.notificacao.NotificacaoCreateDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.notificacao.NotificacaoResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.repositories.projection.CategoriaProjection;
import io.github.devcaioalves.projetodacbackifmarket.repositories.projection.NotificacaoProjection;
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

public interface NotificacaoDoc {

    @Operation(
            summary = "Cria uma nova notificacao",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Notificacao criada com sucesso.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NotificacaoResponseDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Dados incorretos.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)

                    ))
            }
    )
    ResponseEntity<NotificacaoResponseDTO> criarNotificacao(@RequestBody @Valid NotificacaoCreateDTO notificacaoCreateDTO);

    @Operation(
            summary = "Buscar notificacao pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Notificacao encontrada.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NotificacaoResponseDTO.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Notificacao não encontrada.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
            }
    )
    ResponseEntity<NotificacaoResponseDTO> buscarNotificacao(@PathVariable Long id);

    @Operation(
            summary = "Listar todas as notificacoes",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de notificacoes retornada.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NotificacaoProjection.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Notificacoes não encontradas.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
            }
    )
    ResponseEntity<Page<NotificacaoResponseDTO>> buscarTodasNotificacoes(@Parameter(hidden = true) Pageable pageable);

    @Operation(
            summary = "Atualizar notificacao",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Notificacao atualizada com sucesso.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoriaResponseDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Dados incorretos para atualização.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
            }
    )
    ResponseEntity<NotificacaoResponseDTO> atualizarNotificacao(@PathVariable Long id, @RequestBody @Valid NotificacaoAlterDTO dto);

    @Operation(
            summary = "Deletar uma notificacao",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Notificacao deletada com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Notificacao não encontrada.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
            }
    )
    ResponseEntity<NotificacaoResponseDTO> deletarNotificacao(@PathVariable Long id);

}
