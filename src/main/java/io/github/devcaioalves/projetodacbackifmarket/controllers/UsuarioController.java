package io.github.devcaioalves.projetodacbackifmarket.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.devcaioalves.projetodacbackifmarket.entities.Usuario;
import io.github.devcaioalves.projetodacbackifmarket.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/br/com/ifmarket/usermanagent/v1")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;
}
