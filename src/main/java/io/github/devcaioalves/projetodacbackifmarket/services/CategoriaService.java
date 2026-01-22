package io.github.devcaioalves.projetodacbackifmarket.services;

import io.github.devcaioalves.projetodacbackifmarket.dto.categoria.CategoriaAlterDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.categoria.CategoriaCreateDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.categoria.CategoriaResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.entities.Categoria;
import io.github.devcaioalves.projetodacbackifmarket.repositories.CategoriaRepository;
import io.github.devcaioalves.projetodacbackifmarket.repositories.projection.CategoriaProjection;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaResponseDTO criarCategoria(CategoriaCreateDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        return toResponseDTO(categoriaRepository.save(categoria));
    }

    public CategoriaResponseDTO buscarCategoria(Long id) {
        return toResponseDTO(findCategoriaById(id));
    }

    public Page<CategoriaProjection> listarTodasAsCategorias(Pageable pageable) {
        Page<CategoriaProjection> page = categoriaRepository.findAllBy(pageable);
        if (page.isEmpty()) throw new EntityNotFoundException("Sem categorias.");
        return page;
    }

    public CategoriaResponseDTO atualizarCategoria(Long id, CategoriaAlterDTO dto) {
        Categoria categoria = findCategoriaById(id);

        if (dto.getNovoNome() != null && !dto.getNovoNome().isBlank()) {
            categoria.setNome(dto.getNovoNome());
        }

        return toResponseDTO(categoriaRepository.save(categoria));
    }

    public void deletarCategoria(Long id) {
        categoriaRepository.delete(findCategoriaById(id));
    }

    private Categoria findCategoriaById(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
    }

    private CategoriaResponseDTO toResponseDTO(Categoria categoria) {
        return new CategoriaResponseDTO(
                categoria.getIdCategoria(),
                categoria.getNome()
        );
    }
}
