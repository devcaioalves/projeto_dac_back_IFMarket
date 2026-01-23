package io.github.devcaioalves.projetodacbackifmarket.jwt;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serial;
import java.util.Collections;

public class JwtUserDetails extends User {

    @Serial
    private static final long serialVersionUID = 1L;
    private final io.github.devcaioalves.projetodacbackifmarket.entities.Usuario usuario;

    public JwtUserDetails(io.github.devcaioalves.projetodacbackifmarket.entities.Usuario usuario) {
        super(usuario.getEmail(), "", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        this.usuario = usuario;
    }

    public String getId() {
        return String.valueOf(this.usuario.getIdUsuario());
    }



}