package io.github.devcaioalves.projetodacbackifmarket.services;

import io.github.devcaioalves.projetodacbackifmarket.dto.fotoItem.FotoResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.entities.FotoItem;
import io.github.devcaioalves.projetodacbackifmarket.entities.Item;
import io.github.devcaioalves.projetodacbackifmarket.repositories.FotoRepository;
import io.github.devcaioalves.projetodacbackifmarket.repositories.ItemRepository;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FotoService {

    private final FotoRepository fotoRepository;
    private final ItemRepository itemRepository;

    private static final String DIRETORIO_UPLOAD = "uploads/itens/";

    public FotoResponseDTO criarFoto(MultipartFile arquivo, Long itemId) {

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item não encontrado."));

        String caminhoArquivo = salvarArquivo(arquivo);

        FotoItem foto = new FotoItem();
        foto.setCaminhoArquivo(caminhoArquivo);
        foto.setItem(item);

        return toDTO(fotoRepository.save(foto));
    }

    public FotoResponseDTO buscarFoto(Long id) {
        return fotoRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() ->
                        new EntityNotFoundException("Foto com id '" + id + "' não encontrada.")
                );
    }

    public void deletarFoto(Long id) {

        FotoItem foto = fotoRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Foto com id '" + id + "' não encontrada.")
                );

        deletarArquivo(foto.getCaminhoArquivo());
        fotoRepository.delete(foto);
    }

    private String salvarArquivo(MultipartFile arquivo) {

        if (arquivo == null || arquivo.isEmpty()) {
            throw new IllegalArgumentException("Arquivo inválido.");
        }

        try {
            String nomeArquivo = UUID.randomUUID() + "_" + arquivo.getOriginalFilename();
            Path caminho = Paths.get(DIRETORIO_UPLOAD, nomeArquivo);

            Files.createDirectories(caminho.getParent());
            Files.write(caminho, arquivo.getBytes());

            return DIRETORIO_UPLOAD + nomeArquivo;

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar o arquivo.", e);
        }
    }

    private void deletarArquivo(String caminhoArquivo) {
        try {
            Files.deleteIfExists(Paths.get(caminhoArquivo));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao deletar o arquivo.", e);
        }
    }

    private FotoResponseDTO toDTO(FotoItem foto) {
        return new FotoResponseDTO(
                foto.getIdFoto(),
                "http://localhost:8080/" + foto.getCaminhoArquivo().replace("\\", "/"),
                foto.getItem().getIdItem()
        );
    }

}


