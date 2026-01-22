package io.github.devcaioalves.projetodacbackifmarket.repositories;

import io.github.devcaioalves.projetodacbackifmarket.entities.SenhaResetToken;
import io.github.devcaioalves.projetodacbackifmarket.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SenhaResetTokenRepository extends JpaRepository<SenhaResetToken,Long> {
    Optional<SenhaResetToken> findByToken(String token);
    Optional<SenhaResetToken> findByUsuario(Usuario usuario);

}
