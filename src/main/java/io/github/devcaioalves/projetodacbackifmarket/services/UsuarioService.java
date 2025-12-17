package io.github.devcaioalves.projetodacbackifmarket.services;

import io.github.devcaioalves.projetodacbackifmarket.dto.usuario.UsuarioAlterDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.usuario.UsuarioAlterPassDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.usuario.UsuarioCreateDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.usuario.UsuarioResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.usuario.login.LoginDTO;
import io.github.devcaioalves.projetodacbackifmarket.entities.SenhaResetToken;
import io.github.devcaioalves.projetodacbackifmarket.entities.Usuario;
import io.github.devcaioalves.projetodacbackifmarket.entities.enums.Role;
import io.github.devcaioalves.projetodacbackifmarket.repositories.SenhaResetTokenRepository;
import io.github.devcaioalves.projetodacbackifmarket.repositories.UsuarioRepository;
import io.github.devcaioalves.projetodacbackifmarket.repositories.projection.UsuarioProjection;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final SenhaResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    private static final List<String> DOMINIOS_ACADEMICOS = List.of(
            "@academico.ifpb.edu.br",
            "@ifpb.edu.br"
    );

    public UsuarioResponseDTO criarUsuario(UsuarioCreateDTO dto) {

        validarEmailAcademico(dto.getEmail());
        validarSenha(dto.getSenha(), dto.getConfirmarSenha());

        if (dto.getRole() == Role.ALUNO && !campoValido(dto.getMatricula())) {
            throw new IllegalArgumentException("A matrícula é obrigatória para alunos.");
        }

        Usuario usuario = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(passwordEncoder.encode(dto.getSenha()))
                .telefone(dto.getTelefone())
                .role(dto.getRole())
                .matricula(dto.getMatricula())
                .build();

        return toResponseDTO(usuarioRepository.save(usuario));
    }

    public UsuarioResponseDTO buscarUsuario(Long id) {
        return toResponseDTO(findUsuarioById(id));
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

        if (campoValido(dto.getNovoNome())) usuario.setNome(dto.getNovoNome());
        if (campoValido(dto.getNovoEmail())) {
            validarEmailAcademico(dto.getNovoEmail());
            usuario.setEmail(dto.getNovoEmail());
        }
        if (campoValido(dto.getNovoTelefone())) usuario.setTelefone(dto.getNovoTelefone());

        return toResponseDTO(usuarioRepository.save(usuario));
    }

    public void alterarSenha(Long id, UsuarioAlterPassDTO dto) {
        Usuario usuario = findUsuarioById(id);

        if (!passwordEncoder.matches(dto.getSenhaAtual(), usuario.getSenha())) {
            throw new IllegalArgumentException("Senha atual incorreta.");
        }

        validarSenha(dto.getNovaSenha(), dto.getConfirmarNovaSenha());

        usuario.setSenha(passwordEncoder.encode(dto.getNovaSenha()));
        usuarioRepository.save(usuario);
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

    public UsuarioResponseDTO autenticar(LoginDTO dto) {
        if (!campoValido(dto.getIdentificador()) || !campoValido(dto.getSenha())) {
            throw new IllegalArgumentException("Identificador e senha são obrigatórios.");
        }

        // tenta encontrar por email ou matrícula (passa o mesmo valor para ambos)
        Usuario usuario = usuarioRepository
                .findByEmailOrMatricula(dto.getIdentificador(), dto.getIdentificador())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));

        // compara com bcrypt
        if (!passwordEncoder.matches(dto.getSenha(), usuario.getSenha())) {
            throw new IllegalArgumentException("Credenciais inválidas.");
        }

        // retorna DTO sem senha
        return toResponseDTO(usuario);
    }

    private void validarEmailAcademico(String email) {
        if (!campoValido(email)) {
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

    private void validarSenha(String senha, String confirmarSenha) {
        if (!campoValido(senha)) {
            throw new IllegalArgumentException("Senha não pode ser vazia.");
        }

        if (!senha.equals(confirmarSenha)) {
            throw new IllegalArgumentException("As senhas não coincidem.");
        }
    }

    private boolean campoValido(String valor) {
        return valor != null && !valor.isBlank();
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

    // 1) Solicitar redefinição
    public void enviarEmailRedefinicao(String email) {

        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("Usuário não encontrado.")
        );

        tokenRepository.findByUsuario(usuario).ifPresent(tokenRepository::delete);

        String token = UUID.randomUUID().toString();

        SenhaResetToken reset = new SenhaResetToken();
        reset.setToken(token);
        reset.setUsuario(usuario);
        reset.setExpiracao(LocalDateTime.now().plusMinutes(15));

        tokenRepository.save(reset);

        String link = "http://localhost:5173/receive-code?token=" + token;

        emailService.enviar(
                usuario.getEmail(),
                "Redefinição de Senha - IFMarket",
                "\nToken: " + token +
                "\nClique no link para redefinir sua senha:\n\n" + link
        );
    }


    // 2) Validar token (para tela do front)
    public void validarToken(String token) {
        SenhaResetToken reset = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token inválido"));

        if (reset.getExpiracao().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token expirado");
        }
    }

    // 3) Redefinir senha
    public void redefinirSenha(String token, String novaSenha) {

        SenhaResetToken reset = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token inválido"));

        if (reset.getExpiracao().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token expirado");
        }

        Usuario usuario = reset.getUsuario();
        usuario.setSenha(passwordEncoder.encode(novaSenha));

        usuarioRepository.save(usuario);

        tokenRepository.delete(reset);
    }
}
