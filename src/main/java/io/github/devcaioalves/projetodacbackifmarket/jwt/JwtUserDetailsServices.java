package io.github.devcaioalves.projetodacbackifmarket.jwt;

import io.github.devcaioalves.projetodacbackifmarket.entities.Usuario;
import io.github.devcaioalves.projetodacbackifmarket.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsServices implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario user = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
        return new JwtUserDetails(user);
    }

    public JwtToken getTokenAuthenticated(Usuario usuario) {
        return JwtUtils.gerarToken(usuario.getIdUsuario(), usuario.getEmail(), usuario.getRole().name());
    }
}