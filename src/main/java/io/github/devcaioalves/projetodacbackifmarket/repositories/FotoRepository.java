package io.github.devcaioalves.projetodacbackifmarket.repositories;

import io.github.devcaioalves.projetodacbackifmarket.entities.FotoItem;
import io.github.devcaioalves.projetodacbackifmarket.repositories.projection.FotoProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FotoRepository extends JpaRepository<FotoItem, Long> {
    Page<FotoProjection> findAllBy(Pageable pageable);
    List<FotoItem> findByItemId(Long itemId);
}
