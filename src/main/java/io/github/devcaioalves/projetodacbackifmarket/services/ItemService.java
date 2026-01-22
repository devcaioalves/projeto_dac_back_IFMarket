package io.github.devcaioalves.projetodacbackifmarket.services;

import io.github.devcaioalves.projetodacbackifmarket.dto.fotoItem.FotoResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.item.ItemAlterDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.item.ItemCreateDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.item.ItemResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.entities.Categoria;
import io.github.devcaioalves.projetodacbackifmarket.entities.Item;
import io.github.devcaioalves.projetodacbackifmarket.entities.Usuario;
import io.github.devcaioalves.projetodacbackifmarket.entities.enums.StatusItem;
import io.github.devcaioalves.projetodacbackifmarket.repositories.CategoriaRepository;
import io.github.devcaioalves.projetodacbackifmarket.repositories.ItemRepository;
import io.github.devcaioalves.projetodacbackifmarket.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;

    @Value("${app.base-url}")
    private String baseUrl;

    public ItemResponseDTO criarItem(ItemCreateDTO dto) {
        Usuario usuario = buscaUsuario(dto.getUsuarioId());
        Categoria categoria = buscaCategoria(dto.getCategoriaId());

        Item item = Item.builder()
                .titulo(dto.getTitulo())
                .descricao(dto.getDescricao())
                .valor(dto.getValor())
                .cidade(dto.getCidade())
                .disponivelTroca(dto.getDisponivelTroca())
                .status(StatusItem.DISPONIVEL)
                .dataAnuncio(LocalDateTime.now())
                .usuario(usuario)
                .categoria(categoria)
                .build();

        Item saved = itemRepository.save(item);
        return toResponseDTO(saved);
    }

    public ItemResponseDTO buscarItem(Long id) {
        return toResponseDTO(buscaItem(id));
    }

    public Page<ItemResponseDTO> listarTodosOsItens(Pageable pageable, Long categoriaId) {
        Page<Item> itens;

        if (categoriaId != null) {
            itens = itemRepository.findAllByCategoriaIdCategoria(categoriaId, pageable);
        } else {
            itens = itemRepository.findAllBy(pageable);
        }

        if (itens.isEmpty()) {
            throw new EntityNotFoundException("Sem itens cadastrados");
        }

        return itens.map(this::toResponseDTO);
    }

    public Page<ItemResponseDTO> listarTodosOsItensUsuario(Pageable pageable, Long categoriaId, Long usuarioId) {
        Page<Item> itens;

        if (categoriaId != null && usuarioId != null) {
            // busca por categoria e usuário
            itens = itemRepository.findAllByCategoriaIdCategoriaAndUsuarioIdUsuario(categoriaId, usuarioId, pageable);
        } else if (categoriaId != null) {
            // busca só por categoria
            itens = itemRepository.findAllByCategoriaIdCategoria(categoriaId, pageable);
        } else if (usuarioId != null) {
            // busca só por usuário
            itens = itemRepository.findAllByUsuarioIdUsuario(usuarioId, pageable);
        } else {
            // busca todos
            itens = itemRepository.findAll(pageable);
        }

        if (itens.isEmpty()) {
            throw new EntityNotFoundException("Sem itens cadastrados");
        }

        return itens.map(this::toResponseDTO);
    }


    public ItemResponseDTO atualizarItem(Long id, ItemAlterDTO dto) {
        Item item = buscaItem(id);

        if (dto.getNovoTitulo() != null) item.setTitulo(dto.getNovoTitulo());
        if (dto.getNovaDescricao() != null) item.setDescricao(dto.getNovaDescricao());
        if (dto.getNovoValor() != null) item.setValor(dto.getNovoValor());
        if (dto.getNovaCidade() != null) item.setCidade(dto.getNovaCidade());
        if (dto.getNovaDisponibilidadeTroca() != null)
            item.setDisponivelTroca(dto.getNovaDisponibilidadeTroca());

        if (dto.getNovaCategoriaId() != null) {
            Categoria novaCategoria = buscaCategoria(dto.getNovaCategoriaId());
            item.setCategoria(novaCategoria);
        }

        return toResponseDTO(itemRepository.save(item));
    }

    public List<ItemResponseDTO> buscarPorTitulo(String termo) {
        List<Item> itens = itemRepository.findAllByTituloContainingIgnoreCase(termo);
        return itens.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public void deletarItem(Long id) {
        itemRepository.delete(buscaItem(id));
    }

    private Item buscaItem(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Item com id '" + id + "' não encontrado."));
    }

    private Usuario buscaUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Usuário não encontrado"));
    }

    private Categoria buscaCategoria(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Categoria não encontrada"));
    }

    /** Converte entidade Item em DTO */
    public ItemResponseDTO toResponseDTO(Item item) {
        List<FotoResponseDTO> fotosDTO = (item.getFotos() != null && !item.getFotos().isEmpty())
                ? item.getFotos().stream()
                .map(f -> new FotoResponseDTO(
                        f.getIdFoto(),
                        baseUrl + "/" + f.getCaminhoArquivo().replace("\\", "/"),
                        item.getIdItem()
                ))
                .toList()
                : List.of();

        return new ItemResponseDTO(
                item.getIdItem(),
                item.getTitulo(),
                item.getDescricao(),
                item.getValor(),
                item.getCidade(),
                item.getDisponivelTroca(),
                item.getStatus(),
                item.getDataAnuncio(),
                item.getUsuario().getIdUsuario(),
                item.getCategoria().getIdCategoria(),
                fotosDTO
        );
    }
}

