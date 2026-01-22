package io.github.devcaioalves.projetodacbackifmarket.repositories;

import io.github.devcaioalves.projetodacbackifmarket.entities.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findAllBy(Pageable pageable);
    List<Item> findAllByTituloContainingIgnoreCase(String titulo);
    Page<Item> findAllByCategoriaIdCategoria(Long idCategoria, Pageable pageable);
    Page<Item> findAllByCategoriaIdCategoriaAndUsuarioIdUsuario(Long categoriaId, Long usuarioId, Pageable pageable);
    Page<Item> findAllByUsuarioIdUsuario(Long usuarioId, Pageable pageable);
}
