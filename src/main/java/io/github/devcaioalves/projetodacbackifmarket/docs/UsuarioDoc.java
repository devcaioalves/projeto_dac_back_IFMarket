package io.github.devcaioalves.projetodacbackifmarket.docs;

import io.github.devcaioalves.projetodacbackifmarket.dto.usuario.UsuarioAlterDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.usuario.UsuarioCreateDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.usuario.UsuarioResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.repositories.projection.UsuarioProjection;
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

public interface UsuarioDoc {

    @Operation(
            summary = "Cria um novo usuário",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioResponseDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Dados incorretos.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)

                    ))
            }
    )
    ResponseEntity<UsuarioResponseDTO> criarUsuario(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO);

    @Operation(
            summary = "Buscar usuário pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário encontrado.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioResponseDTO.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
            }
    )
    ResponseEntity<UsuarioResponseDTO> buscarUsuario(@PathVariable Long id);

    @Operation(
            summary = "Listar todos os usuários",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioProjection.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Usuários não encontrados.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
            }
    )
    ResponseEntity<Page<UsuarioResponseDTO>> buscarTodosUsuarios(@Parameter(hidden = true) Pageable pageable);

    @Operation(
            summary = "Atualizar Usuário",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioResponseDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Dados incorretos para atualização.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
            }
    )
    ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable Long id, @RequestBody @Valid UsuarioAlterDTO dto);

    @Operation(
            summary = "Deletar um usuário",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
            }
    )
    ResponseEntity<UsuarioResponseDTO> deletarUsuario(@PathVariable Long id);
}
