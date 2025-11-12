package io.github.devcaioalves.projetodacbackifmarket.docs;

import io.github.devcaioalves.projetodacbackifmarket.dto.item.ItemAlterDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.item.ItemCreateDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.item.ItemResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.repositories.projection.ItemProjection;
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

public interface ItemDoc {

    @Operation(
            summary = "Cria um novo item",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Item criado com sucesso.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ItemResponseDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Dados incorretos.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)

                    ))
            }
    )
    ResponseEntity<ItemResponseDTO> criarItem(@RequestBody @Valid ItemCreateDTO itemCreateDTO);

    @Operation(
            summary = "Buscar item pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Item encontrado.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ItemResponseDTO.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Item não encontrado.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
            }
    )
    ResponseEntity<ItemResponseDTO> buscarItem(@PathVariable Long id);

    @Operation(
            summary = "Listar todos os itens",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de itens retornada.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ItemProjection.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Itens não encontrados.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
            }
    )
    ResponseEntity<Page<ItemResponseDTO>> buscarTodosItens(@Parameter(hidden = true) Pageable pageable);

    @Operation(
            summary = "Atualizar Item",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ItemResponseDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Dados incorretos para atualização.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
            }
    )
    ResponseEntity<ItemResponseDTO> atualizarItem(@PathVariable Long id, @RequestBody @Valid ItemAlterDTO dto);

    @Operation(
            summary = "Deletar um Item",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Item deletado com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Item não encontrado.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
            }
    )
    ResponseEntity<ItemResponseDTO> deletarItem(@PathVariable Long id);

}
