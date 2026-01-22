package io.github.devcaioalves.projetodacbackifmarket.repositories;

import io.github.devcaioalves.projetodacbackifmarket.entities.PropostaTroca;
import io.github.devcaioalves.projetodacbackifmarket.repositories.projection.PropostaProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropostaRepository extends JpaRepository<PropostaTroca,Long> {
    Page<PropostaProjection> findAllBy(Pageable pageable);
}
