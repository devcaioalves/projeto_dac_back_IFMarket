package io.github.devcaioalves.projetodacbackifmarket.repositories;

import io.github.devcaioalves.projetodacbackifmarket.entities.Usuario;
import io.github.devcaioalves.projetodacbackifmarket.repositories.projection.UsuarioProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    Page<UsuarioProjection> findAllBy(Pageable pageable);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByMatricula(String matricula);
    Optional<Usuario> findByEmailOrMatricula(String email, String matricula);
}
