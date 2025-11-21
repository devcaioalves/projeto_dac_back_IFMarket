package io.github.devcaioalves.projetodacbackifmarket.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.devcaioalves.projetodacbackifmarket.dto.fotoItem.FotoAlterDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.fotoItem.FotoCreateDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.fotoItem.FotoResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.entities.FotoItem;
import io.github.devcaioalves.projetodacbackifmarket.repositories.FotoRepository;
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
    private final ObjectMapper objectMapper;

    public FotoResponseDTO criarFoto(FotoCreateDTO dto) {

        FotoItem foto = objectMapper.convertValue(dto, FotoItem.class);

        FotoItem salvaFotoItem = fotoRepository.save(foto);
        return convertToDTO(salvaFotoItem);
    }

    public FotoResponseDTO buscarFoto(Long id) {
        FotoItem fotoItem = fotoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Foto com id '" + id + "' não encontrada."));
        return convertToDTO(fotoItem);
    }

    public Page<FotoProjection> listarTodasAsFotos(Pageable pageable) {
        Page<FotoProjection> page = fotoRepository.findAllBy(pageable);
        if (page.isEmpty()) {
            throw new EntityNotFoundException("Sem Fotos.");
        }
        return page;
    }

    public FotoResponseDTO atualizarFoto(Long id, FotoAlterDTO dto) {
        FotoItem foto = fotoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Foto com id '" + id + "' não encontrada."));

        foto.setCaminhoArquivo(dto.getNovoCaminhoArquivo());
        foto.setItem(dto.getNovoItem());

        FotoItem fotoItemAtualizada = fotoRepository.save(foto);
        return convertToDTO(fotoItemAtualizada);
    }

    public void deletarFoto(Long id) {
        FotoItem foto = fotoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Foto com id '" + id + "' não encontrada."));

        fotoRepository.delete(foto);
    }

    private FotoResponseDTO convertToDTO(FotoItem foto) {
        return objectMapper.convertValue(foto, FotoResponseDTO.class);
    }

}
