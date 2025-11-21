package io.github.devcaioalves.projetodacbackifmarket.services;

import io.github.devcaioalves.projetodacbackifmarket.dto.fotoItem.FotoAlterDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.fotoItem.FotoCreateDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.fotoItem.FotoResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.entities.FotoItem;
import io.github.devcaioalves.projetodacbackifmarket.entities.Item;
import io.github.devcaioalves.projetodacbackifmarket.repositories.FotoRepository;
import io.github.devcaioalves.projetodacbackifmarket.repositories.ItemRepository;
import io.github.devcaioalves.projetodacbackifmarket.repositories.projection.FotoProjection;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FotoService {

    private final FotoRepository fotoRepository;
    private final ItemRepository itemRepository;

    public FotoResponseDTO criarFoto(FotoCreateDTO dto) {

        Item item = itemRepository.findById(dto.getItemId())
                .orElseThrow(() -> new EntityNotFoundException("Item não encontrado."));

        FotoItem foto = new FotoItem();
        foto.setCaminhoArquivo(dto.getCaminhoArquivo());
        foto.setItem(item);

        FotoItem salvaFoto = fotoRepository.save(foto);
        return toDTO(salvaFoto);
    }

    public FotoResponseDTO buscarFoto(Long id) {
        FotoItem foto = fotoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Foto com id '" + id + "' não encontrada."));
        return toDTO(foto);
    }

    public Page<FotoProjection> listarTodasAsFotos(Pageable pageable) {
        Page<FotoProjection> page = fotoRepository.findAllBy(pageable);

        if (page.isEmpty()) {
            throw new EntityNotFoundException("Sem fotos cadastradas.");
        }

        return page;
    }

    public FotoResponseDTO atualizarFoto(Long id, FotoAlterDTO dto) {
        FotoItem foto = fotoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Foto com id '" + id + "' não encontrada."));

        if (dto.getNovoCaminhoArquivo() != null) {
            foto.setCaminhoArquivo(dto.getNovoCaminhoArquivo());
        }

        if (dto.getNovoItemId() != null) {
            Item item = itemRepository.findById(dto.getNovoItemId())
                    .orElseThrow(() -> new EntityNotFoundException("Item não encontrado."));
            foto.setItem(item);
        }

        FotoItem fotoAtualizada = fotoRepository.save(foto);
        return toDTO(fotoAtualizada);
    }

    public void deletarFoto(Long id) {
        FotoItem foto = fotoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Foto com id '" + id + "' não encontrada."));

        fotoRepository.delete(foto);
    }

    private FotoResponseDTO toDTO(FotoItem foto) {
        return new FotoResponseDTO(
                foto.getIdFoto(),
                foto.getCaminhoArquivo(),
                foto.getItem().getIdItem()
        );
    }
}

