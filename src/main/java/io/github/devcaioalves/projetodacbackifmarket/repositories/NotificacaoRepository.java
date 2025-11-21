package io.github.devcaioalves.projetodacbackifmarket.repositories;

import io.github.devcaioalves.projetodacbackifmarket.entities.Notificacao;
import io.github.devcaioalves.projetodacbackifmarket.repositories.projection.NotificacaoProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
    Page<NotificacaoProjection> findAllBy(Pageable pageable);
}
