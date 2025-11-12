package io.github.devcaioalves.projetodacbackifmarket.docs;

import io.github.devcaioalves.projetodacbackifmarket.dto.propostatroca.PropostaAlterDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.propostatroca.PropostaCreateDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.propostatroca.PropostaResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.repositories.projection.PropostaProjection;
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

public interface PropostaDoc {

    @Operation(
            summary = "Cria uma nova proposta de troca",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Proposta de troca criado com sucesso.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PropostaResponseDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Dados incorretos.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)

                    ))
            }
    )
    ResponseEntity<PropostaResponseDTO> criarProposta(@RequestBody @Valid PropostaCreateDTO itemCreateDTO);

    @Operation(
            summary = "Buscar proposta de troca pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Proposta de troca encontrada.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PropostaResponseDTO.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Proposta de troca não encontrada.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
            }
    )
    ResponseEntity<PropostaResponseDTO> buscarProposta(@PathVariable Long id);

    @Operation(
            summary = "Listar todas as propostas de trocas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de propostas de trocas retornada.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PropostaProjection.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Propostas de trocas não encontradas.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
            }
    )
    ResponseEntity<Page<PropostaResponseDTO>> buscarTodasAsPropostas(@Parameter(hidden = true) Pageable pageable);

    @Operation(
            summary = "Atualizar proposta de troca",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Proposta de troca atualizada com sucesso.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PropostaResponseDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Dados incorretos para atualização.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
            }
    )
    ResponseEntity<PropostaResponseDTO> atualizarProposta(@PathVariable Long id, @RequestBody @Valid PropostaAlterDTO dto);

    @Operation(
            summary = "Deletar uma proposta de troca",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Proposta de troca deletada com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Proposta de troca não encontrada.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
            }
    )
    ResponseEntity<PropostaResponseDTO> deletarProposta(@PathVariable Long id);

}
