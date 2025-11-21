package io.github.devcaioalves.projetodacbackifmarket.services;

import io.github.devcaioalves.projetodacbackifmarket.dto.fotoItem.FotoResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.item.ItemAlterDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.item.ItemCreateDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.item.ItemResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.entities.Categoria;
import io.github.devcaioalves.projetodacbackifmarket.entities.FotoItem;
import io.github.devcaioalves.projetodacbackifmarket.entities.Item;
import io.github.devcaioalves.projetodacbackifmarket.entities.Usuario;
import io.github.devcaioalves.projetodacbackifmarket.entities.enums.StatusItem;
import io.github.devcaioalves.projetodacbackifmarket.repositories.CategoriaRepository;
import io.github.devcaioalves.projetodacbackifmarket.repositories.FotoRepository;
import io.github.devcaioalves.projetodacbackifmarket.repositories.ItemRepository;
import io.github.devcaioalves.projetodacbackifmarket.repositories.UsuarioRepository;
import io.github.devcaioalves.projetodacbackifmarket.repositories.projection.ItemProjection;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final FotoRepository fotoRepository;

    public ItemResponseDTO criarItem(ItemCreateDTO dto) {

        Usuario usuario = buscaUsuario(dto.getUsuarioId());
        Categoria categoria = buscaCategoria(dto.getCategoriaId());

        Item item = Item.builder()
                .titulo(dto.getTitulo())
                .descricao(dto.getDescricao())
                .valor(dto.getValor())
                .disponivelTroca(dto.getDisponivelTroca())
                .status(StatusItem.DISPONIVEL)
                .dataAnuncio(LocalDateTime.now())
                .usuario(usuario)
                .categoria(categoria)
                .build();

        Item saved = itemRepository.save(item);

        if (dto.getFotos() != null && !dto.getFotos().isEmpty()) {
            List<FotoItem> fotos = fotoRepository.findAllById(dto.getFotos());
            fotos.forEach(f -> f.setItem(saved));
            saved.setFotos(fotos);
        }

        return toResponseDTO(saved);
    }

    public ItemResponseDTO buscarItem(Long id) {
        return toResponseDTO(buscaItem(id));
    }

    public Page<ItemProjection> listarTodosOsItens(Pageable pageable) {
        Page<ItemProjection> page = itemRepository.findAllBy(pageable);
        if (page.isEmpty()) throw new EntityNotFoundException("Sem itens cadastrados");
        return page;
    }

    public ItemResponseDTO atualizarItem(Long id, ItemAlterDTO dto) {

        Item item = buscaItem(id);

        if (dto.getNovoTitulo() != null) item.setTitulo(dto.getNovoTitulo());
        if (dto.getNovaDescricao() != null) item.setDescricao(dto.getNovaDescricao());
        if (dto.getNovoValor() != null) item.setValor(dto.getNovoValor());
        if (dto.getNovaDisponibilidadeTroca() != null) item.setDisponivelTroca(dto.getNovaDisponibilidadeTroca());
        if (dto.getNovoStatus() != null) item.setStatus(dto.getNovoStatus());

        if (dto.getNovaCategoriaId() != null) {
            Categoria novaCategoria = buscaCategoria(dto.getNovaCategoriaId());
            item.setCategoria(novaCategoria);
        }

        List<FotoItem> fotosAtuais = item.getFotos();
        fotosAtuais.clear(); // remove fotos antigas (orphanRemoval cuida de deletar do DB)

        if (dto.getNovasFotos() != null) {
            List<FotoItem> novasFotos = fotoRepository.findAllById(dto.getNovasFotos());
            // garante que cada foto tenha referência ao item
            novasFotos.forEach(f -> f.setItem(item));
            fotosAtuais.addAll(novasFotos);
        }

        return toResponseDTO(itemRepository.save(item));
    }

    public void deletarItem(Long id) {
        itemRepository.delete(buscaItem(id));
    }

    private Item buscaItem(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item com id '" + id + "' não encontrado."));
    }

    private Usuario buscaUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    private Categoria buscaCategoria(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
    }

    private ItemResponseDTO toResponseDTO(Item item) {
        return new ItemResponseDTO(
                item.getIdItem(),
                item.getTitulo(),
                item.getDescricao(),
                item.getValor(),
                item.getDisponivelTroca(),
                item.getStatus(),
                item.getDataAnuncio(),
                item.getUsuario().getIdUsuario(),
                item.getCategoria().getIdCategoria(),
                item.getFotos() != null
                        ? item.getFotos().stream()
                        .map(f -> new FotoResponseDTO(f.getIdFoto(), f.getCaminhoArquivo(), f.getItem().getIdItem()))
                        .toList()
                        : List.of()
        );
    }
}
