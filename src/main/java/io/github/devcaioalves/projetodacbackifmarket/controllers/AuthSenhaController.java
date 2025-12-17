package io.github.devcaioalves.projetodacbackifmarket.controllers;

import io.github.devcaioalves.projetodacbackifmarket.dto.alterarsenha.EsqueciSenhaDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.alterarsenha.RedefinirSenhaDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.alterarsenha.ValidarTokenDTO;
import io.github.devcaioalves.projetodacbackifmarket.exceptions.EntityNotFoundException;
import io.github.devcaioalves.projetodacbackifmarket.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/br/com/ifmarket/recuperarsenha/v1")
public class AuthSenhaController {

    private final UsuarioService usuarioService;

    @PostMapping("/esqueci-senha")
    public ResponseEntity<String> esqueciSenha(@RequestBody @Valid EsqueciSenhaDTO dto) {
        try {
            usuarioService.enviarEmailRedefinicao(dto.getEmail());
            return ResponseEntity.ok("Um link foi enviado para seu e-mail.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/validar-token")
    public ResponseEntity<String> validarToken(@RequestBody @Valid ValidarTokenDTO dto) {
        try {
            usuarioService.validarToken(dto.getToken());
            return ResponseEntity.ok("Token válido.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<String> redefinirSenha(@RequestBody @Valid RedefinirSenhaDTO dto) {
        usuarioService.redefinirSenha(dto.getToken(), dto.getNovaSenha());
        return ResponseEntity.ok("Senha redefinida com sucesso!");
    }
}
