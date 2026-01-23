package io.github.devcaioalves.projetodacbackifmarket.controllers;

import io.github.devcaioalves.projetodacbackifmarket.dto.usuario.login.LoginDTO;
import io.github.devcaioalves.projetodacbackifmarket.entities.Usuario;
import io.github.devcaioalves.projetodacbackifmarket.exceptions.EntityNotFoundException;
import io.github.devcaioalves.projetodacbackifmarket.jwt.JwtToken;
import io.github.devcaioalves.projetodacbackifmarket.jwt.JwtUserDetailsServices;
import io.github.devcaioalves.projetodacbackifmarket.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/br/com/ifmarket/auth")
public class AuthController {
	
	private final JwtUserDetailsServices detailsServices;
	private final UsuarioService userService;


	@PostMapping
	public ResponseEntity<?> auth(@RequestBody @Valid LoginDTO usuarioDTO) {
		log.info("Processo de autenticação por identificador {}", usuarioDTO.getIdentificador());
		try {
			// Busca usuário por email ou matrícula
			Usuario usuario = userService.buscarPorIdentificador(usuarioDTO.getIdentificador());

			// Gera token com email e role
			JwtToken token = detailsServices.getTokenAuthenticated(usuario);

			return ResponseEntity.ok(token);
		} catch (EntityNotFoundException e) {
			log.warn("Tentativa de gerar token para um identificador inexistente '{}'", usuarioDTO.getIdentificador());
			return ResponseEntity
					.status(HttpStatus.UNAUTHORIZED)
					.body(new ErrorMessage("Usuário não encontrado."));
		}
	}

}
