package io.github.devcaioalves.projetodacbackifmarket.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.devcaioalves.projetodacbackifmarket.dto.item.ItemAlterDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.item.ItemCreateDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.item.ItemResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.entities.Item;
import io.github.devcaioalves.projetodacbackifmarket.repositories.ItemRepository;
import io.github.devcaioalves.projetodacbackifmarket.repositories.projection.ItemProjection;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ObjectMapper objectMapper;

    public ItemResponseDTO criarItem(ItemCreateDTO dto) {

        Item item = objectMapper.convertValue(dto, Item.class);

        Item salvoItem = itemRepository.save(item);
        return convertToDTO(salvoItem);
    }

    public ItemResponseDTO buscarItem(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item com id '" + id + "' não encontrado."));
        return convertToDTO(item);
    }

    public Page<ItemProjection> listarTodosOsItens(Pageable pageable) {
        Page<ItemProjection> page = itemRepository.findAllBy(pageable);
        if (page.isEmpty()) {
            throw new EntityNotFoundException("Sem Itens.");
        }
        return page;
    }

    public ItemResponseDTO atualizarItem(Long id, ItemAlterDTO dto) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item com id '" + id + "' não encontrado."));

        item.setTitulo(dto.getNovoTitulo());
        item.setDescricao(dto.getNovaDescricao());
        item.setValor(dto.getNovoValor());
        item.setDisponivelTroca(dto.getNovaDisponibilidadeTroca());
        item.setStatus(dto.getNovoStatus());
        item.setCategoria(dto.getNovaCategoria());
        item.setFotos(dto.getNovasFotos());

        Item itemAtualizado = itemRepository.save(item);
        return convertToDTO(itemAtualizado);
    }

    public void deletarItem(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item com id '" + id + "' não encontrado."));

        itemRepository.delete(item);
    }

    private ItemResponseDTO convertToDTO(Item item) {
        return objectMapper.convertValue(item, ItemResponseDTO.class);
    }

}
