package io.github.devcaioalves.projetodacbackifmarket.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.devcaioalves.projetodacbackifmarket.docs.UsuarioDoc;
import io.github.devcaioalves.projetodacbackifmarket.dto.usuario.UsuarioAlterDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.usuario.UsuarioAlterPassDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.usuario.UsuarioCreateDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.usuario.UsuarioResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.usuario.login.LoginDTO;
import io.github.devcaioalves.projetodacbackifmarket.exceptions.EntityNotFoundException;
import io.github.devcaioalves.projetodacbackifmarket.repositories.projection.UsuarioProjection;
import io.github.devcaioalves.projetodacbackifmarket.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/br/com/ifmarket/usermanagent/v1")
public class UsuarioController implements UsuarioDoc {

    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;

    @PostMapping("/criar-usuario")
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO) {
        try {
            UsuarioResponseDTO usuario = usuarioService.criarUsuario(usuarioCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
        } catch (IllegalArgumentException ex) {
            // Retorna um UsuarioResponseDTO vazio com a mensagem de erro
            UsuarioResponseDTO erro = new UsuarioResponseDTO();
            erro.setMensagemErro(ex.getMessage()); // adicione esse campo no DTO
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
        }
    }

    @GetMapping("/buscar-usuario/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuario(@PathVariable Long id) {
        UsuarioResponseDTO usuario = usuarioService.buscarUsuario(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/buscar-todos-usuarios")
    public ResponseEntity<Page<UsuarioResponseDTO>> buscarTodosUsuarios(@Parameter(hidden = true) Pageable pageable) {
        Page<UsuarioProjection> usuarios = usuarioService.listarTodosOsUsuarios(pageable);
        return ResponseEntity.ok(convertToDTOPage(usuarios, pageable));
    }

    @PutMapping("/atualizar-usuario/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable Long id, @RequestBody @Valid UsuarioAlterDTO dto) {
        UsuarioResponseDTO usuario = usuarioService.atualizarUsuario(id, dto);
        return ResponseEntity.ok(usuario);
    }

    @PatchMapping("/alterar-senha-usuario/{id}")
    public ResponseEntity<UsuarioResponseDTO> alterarSenha(@PathVariable Long id, @RequestBody @Valid UsuarioAlterPassDTO dto) {
        try {
            usuarioService.alterarSenha(id, dto);
            UsuarioResponseDTO resposta = new UsuarioResponseDTO();
            resposta.setMensagemErro("Senha alterada com sucesso!");
            return ResponseEntity.ok(resposta);
        } catch (IllegalArgumentException ex) {
            UsuarioResponseDTO erro = new UsuarioResponseDTO();
            erro.setMensagemErro(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
        }
    }

    @DeleteMapping("/deleta-usuario/{id}")
    public ResponseEntity<UsuarioResponseDTO> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        UsuarioResponseDTO resposta = new UsuarioResponseDTO();
        resposta.setMensagemErro("Usuário deletado com sucesso!");
        return ResponseEntity.ok(resposta);
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioResponseDTO> login(@RequestBody @Valid LoginDTO dto) {
        try {
            UsuarioResponseDTO usuario = usuarioService.autenticar(dto);
            return ResponseEntity.ok(usuario);
        } catch (EntityNotFoundException | IllegalArgumentException ex) {
            UsuarioResponseDTO erro = new UsuarioResponseDTO();
            erro.setMensagemErro(ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
        }
    }

    private Page<UsuarioResponseDTO> convertToDTOPage(Page<UsuarioProjection> usuarios, Pageable pageable) {
        List<UsuarioResponseDTO> content = usuarios.getContent().stream()
                .map(post -> objectMapper.convertValue(post, UsuarioResponseDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageable, usuarios.getTotalElements());
    }
}
