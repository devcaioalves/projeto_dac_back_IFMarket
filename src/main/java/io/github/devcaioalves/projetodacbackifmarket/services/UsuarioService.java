package io.github.devcaioalves.projetodacbackifmarket.services;

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

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioResponseDTO criarUsuario(UsuarioCreateDTO dto) {

        validarEmailAcademico(dto.getEmail());

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setTelefone(dto.getTelefone());
        usuario.setRole(dto.getRole());
        usuario.setMatricula(dto.getMatricula());

        Usuario savedUsuario = usuarioRepository.save(usuario);

        return toResponseDTO(savedUsuario);
    }

    public UsuarioResponseDTO buscarUsuario(Long id) {
        Usuario usuario = findUsuarioById(id);
        return toResponseDTO(usuario);
    }

    public Page<UsuarioProjection> listarTodosOsUsuarios(Pageable pageable) {
        Page<UsuarioProjection> page = usuarioRepository.findAllBy(pageable);
        if (page.isEmpty()) {
            throw new EntityNotFoundException("Nenhum usuário encontrado.");
        }

        return page;
    }

    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioAlterDTO dto) {
        Usuario usuario = findUsuarioById(id);

        if (dto.getNovoNome() != null && !dto.getNovoNome().isBlank()) {
            usuario.setNome(dto.getNovoNome());
        }

        if (dto.getNovoEmail() != null && !dto.getNovoEmail().isBlank()) {
            validarEmailAcademico(dto.getNovoEmail());
            usuario.setEmail(dto.getNovoEmail());
        }

        if (dto.getNovaSenha() != null && !dto.getNovaSenha().isBlank()) {
            usuario.setSenha(dto.getNovaSenha());
        }

        if (dto.getNovoTelefone() != null && !dto.getNovoTelefone().isBlank()) {
            usuario.setTelefone(dto.getNovoTelefone());
        }

        if (dto.getNovoRole() != null) {
            usuario.setRole(dto.getNovoRole());
        }

        Usuario usuarioAtualizado = usuarioRepository.save(usuario);

        return toResponseDTO(usuarioAtualizado);
    }

    public void deletarUsuario(Long id) {
        Usuario usuario = findUsuarioById(id);
        usuarioRepository.delete(usuario);
    }

    private Usuario findUsuarioById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Usuário com id '" + id + "' não encontrado."));
    }

    private static final List<String> DOMINIOS_ACADEMICOS = List.of(
            "@academico.ifpb.edu.br",
            "@ifpb.edu.br"
    );

    private void validarEmailAcademico(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email não pode ser vazio.");
        }

        boolean valido = DOMINIOS_ACADEMICOS.stream()
                .anyMatch(email::endsWith);

        if (!valido) {
            throw new IllegalArgumentException(
                    "Email inválido! Utilize apenas emails acadêmicos: " + DOMINIOS_ACADEMICOS
            );
        }
    }

    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();

        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setTelefone(usuario.getTelefone());
        dto.setRole(usuario.getRole());
        dto.setMatricula(usuario.getMatricula());

        return dto;
    }

}
