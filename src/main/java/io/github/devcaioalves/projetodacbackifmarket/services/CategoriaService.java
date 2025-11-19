package io.github.devcaioalves.projetodacbackifmarket.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.devcaioalves.projetodacbackifmarket.dto.categoria.CategoriaAlterDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.categoria.CategoriaCreateDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.categoria.CategoriaResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.item.ItemAlterDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.item.ItemCreateDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.item.ItemResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.entities.Categoria;
import io.github.devcaioalves.projetodacbackifmarket.entities.Item;
import io.github.devcaioalves.projetodacbackifmarket.repositories.CategoriaRepository;
import io.github.devcaioalves.projetodacbackifmarket.repositories.ItemRepository;
import io.github.devcaioalves.projetodacbackifmarket.repositories.projection.CategoriaProjection;
import io.github.devcaioalves.projetodacbackifmarket.repositories.projection.ItemProjection;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ObjectMapper objectMapper;

    public CategoriaResponseDTO criarCategoria(CategoriaCreateDTO dto) {

        Categoria categoria = objectMapper.convertValue(dto, Categoria.class);

        Categoria salvoCategoria = categoriaRepository.save(categoria);
        return convertToDTO(salvoCategoria);
    }

    public CategoriaResponseDTO buscarCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria com id '" + id + "' não encontrada."));
        return convertToDTO(categoria);
    }

    public Page<CategoriaProjection> listarTodasAsCategorias(Pageable pageable) {
        Page<CategoriaProjection> page = categoriaRepository.findAllBy(pageable);
        if (page.isEmpty()) {
            throw new EntityNotFoundException("Sem Categorias.");
        }
        return page;
    }

    public CategoriaResponseDTO atualizarCategoria(Long id, CategoriaAlterDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria com id '" + id + "' não encontrada."));

        categoria.setNome(dto.getNovoNome());
        categoria.setItens(dto.getNovosItens());

        Categoria categoriaAtualizada = categoriaRepository.save(categoria);
        return convertToDTO(categoriaAtualizada);
    }

    public void deletarCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria com id '" + id + "' não encontrada."));

        categoriaRepository.delete(categoria);
    }

    private CategoriaResponseDTO convertToDTO(Categoria categoria) {
        return objectMapper.convertValue(categoria, CategoriaResponseDTO.class);
    }

}
