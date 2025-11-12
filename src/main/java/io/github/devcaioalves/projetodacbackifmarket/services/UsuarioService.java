package io.github.devcaioalves.projetodacbackifmarket.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.devcaioalves.projetodacbackifmarket.dto.usuario.UsuarioAlterDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.usuario.UsuarioCreateDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.usuario.UsuarioResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.entities.Usuario;
import io.github.devcaioalves.projetodacbackifmarket.repositories.UsuarioRepository;
import io.github.devcaioalves.projetodacbackifmarket.repositories.projection.UsuarioProjection;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;

    public UsuarioResponseDTO criarUsuario(UsuarioCreateDTO dto) {

        Usuario usuario = objectMapper.convertValue(dto, Usuario.class);

        Usuario savedUsuario = usuarioRepository.save(usuario);
        return convertToDTO(savedUsuario);
    }

    public UsuarioResponseDTO buscarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com id '" + id + "' não encontrado."));
        return convertToDTO(usuario);
    }

    public Page<UsuarioProjection> listarTodosOsUsuarios(Pageable pageable) {
        Page<UsuarioProjection> page = usuarioRepository.findAllBy(pageable);
        if (page.isEmpty()) {
            throw new EntityNotFoundException("Sem usuários.");
        }
        return page;
    }

    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioAlterDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com id '" + id + "' não encontrado."));

        usuario.setNome(dto.getNovoNome());
        usuario.setEmail(dto.getNovoEmail());
        usuario.setSenha(dto.getNovaSenha());
        usuario.setTelefone(dto.getNovoTelefone());
        usuario.setRole(dto.getNovoRole());

        Usuario usuarioAtualizado = usuarioRepository.save(usuario);
        return convertToDTO(usuarioAtualizado);
    }

    public void deletarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario com id '" + id + "' não encontrado."));

        usuarioRepository.delete(usuario);
    }

    private UsuarioResponseDTO convertToDTO(Usuario usuario) {
        return objectMapper.convertValue(usuario, UsuarioResponseDTO.class);
    }

}
