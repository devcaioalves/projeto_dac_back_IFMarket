package io.github.devcaioalves.projetodacbackifmarket.repositories;

import io.github.devcaioalves.projetodacbackifmarket.entities.FotoItem;
import io.github.devcaioalves.projetodacbackifmarket.repositories.projection.FotoProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FotoRepository extends JpaRepository<FotoItem, Long> {
    Page<FotoProjection> findAllBy(Pageable pageable);

}
