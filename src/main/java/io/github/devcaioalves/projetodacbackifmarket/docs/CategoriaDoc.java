package io.github.devcaioalves.projetodacbackifmarket.docs;

import io.github.devcaioalves.projetodacbackifmarket.dto.categoria.CategoriaAlterDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.categoria.CategoriaCreateDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.categoria.CategoriaResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.repositories.projection.CategoriaProjection;
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

public interface CategoriaDoc {
    @Operation(
            summary = "Cria uma nova categoria",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoriaResponseDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Dados incorretos.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)

                    ))
            }
    )
    ResponseEntity<CategoriaResponseDTO> criarCategoria(@RequestBody @Valid CategoriaCreateDTO categoriaCreateDTO);

    @Operation(
            summary = "Buscar categoria pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Categoria encontrada.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoriaResponseDTO.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Categoria não encontrada.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
            }
    )
    ResponseEntity<CategoriaResponseDTO> buscarCategoria(@PathVariable Long id);

    @Operation(
            summary = "Listar todas as categorias",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de categorias retornada.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoriaProjection.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Categorias não encontradas.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
            }
    )
    ResponseEntity<Page<CategoriaResponseDTO>> buscarTodasCategorias(@Parameter(hidden = true) Pageable pageable);

    @Operation(
            summary = "Atualizar categoria",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoriaResponseDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Dados incorretos para atualização.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
            }
    )
    ResponseEntity<CategoriaResponseDTO> atualizarCategoria(@PathVariable Long id, @RequestBody @Valid CategoriaAlterDTO dto);

    @Operation(
            summary = "Deletar uma categoria",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Categoria deletada com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Categoria não encontrada.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
            }
    )
    ResponseEntity<CategoriaResponseDTO> deletarCategoria(@PathVariable Long id);

}
