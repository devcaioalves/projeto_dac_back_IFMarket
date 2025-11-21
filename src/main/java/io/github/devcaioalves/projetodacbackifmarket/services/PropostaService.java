package io.github.devcaioalves.projetodacbackifmarket.services;

import io.github.devcaioalves.projetodacbackifmarket.dto.propostatroca.PropostaAlterDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.propostatroca.PropostaCreateDTO;
import io.github.devcaioalves.projetodacbackifmarket.dto.propostatroca.PropostaResponseDTO;
import io.github.devcaioalves.projetodacbackifmarket.entities.Item;
import io.github.devcaioalves.projetodacbackifmarket.entities.PropostaTroca;
import io.github.devcaioalves.projetodacbackifmarket.entities.Usuario;
import io.github.devcaioalves.projetodacbackifmarket.entities.enums.StatusProposta;
import io.github.devcaioalves.projetodacbackifmarket.repositories.ItemRepository;
import io.github.devcaioalves.projetodacbackifmarket.repositories.PropostaRepository;
import io.github.devcaioalves.projetodacbackifmarket.repositories.UsuarioRepository;
import io.github.devcaioalves.projetodacbackifmarket.repositories.projection.PropostaProjection;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PropostaService {

    private final PropostaRepository propostaRepository;
    private final ItemRepository itemRepository;
    private final UsuarioRepository usuarioRepository;

    public PropostaResponseDTO criarProposta(PropostaCreateDTO dto) {

        Item itemOfertante = itemRepository.findById(dto.getItemOfertante())
                .orElseThrow(() -> new EntityNotFoundException("Item ofertante não encontrado."));

        Item itemDestino = itemRepository.findById(dto.getItemDestino())
                .orElseThrow(() -> new EntityNotFoundException("Item destino não encontrado."));

        Usuario usuarioOfertante = usuarioRepository.findById(dto.getUsuarioOfertante())
                .orElseThrow(() -> new EntityNotFoundException("Usuário ofertante não encontrado."));

        Usuario usuarioDestino = usuarioRepository.findById(dto.getUsuarioDestino())
                .orElseThrow(() -> new EntityNotFoundException("Usuário destino não encontrado."));

        PropostaTroca proposta = PropostaTroca.builder()
                .itemOfertante(itemOfertante)
                .itemDestino(itemDestino)
                .usuarioOfertante(usuarioOfertante)
                .usuarioDestino(usuarioDestino)
                .valorDiferenca(dto.getValorDiferenca())
                .status(StatusProposta.PENDENTE)
                .dataProposta(LocalDateTime.now())
                .build();

        PropostaTroca salva = propostaRepository.save(proposta);
        return toResponseDTO(salva);
    }

    public PropostaResponseDTO buscarProposta(Long id) {
        PropostaTroca proposta = propostaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proposta não encontrada."));
        return toResponseDTO(proposta);
    }

    public Page<PropostaProjection> listarTodosAsPropostas(Pageable pageable) {
        Page<PropostaProjection> page = propostaRepository.findAllBy(pageable);

        if (page.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma proposta encontrada.");
        }
        return page;
    }

    public PropostaResponseDTO atualizarProposta(Long id, PropostaAlterDTO dto) {

        PropostaTroca proposta = propostaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proposta não encontrada."));

        if (dto.getNovoItemOfertante() != null) {
            Item novoItem = itemRepository.findById(dto.getNovoItemOfertante())
                    .orElseThrow(() -> new EntityNotFoundException("Item ofertante não encontrado."));
            proposta.setItemOfertante(novoItem);
        }

        if (dto.getNovoItemDestino() != null) {
            Item novoItem = itemRepository.findById(dto.getNovoItemDestino())
                    .orElseThrow(() -> new EntityNotFoundException("Item destino não encontrado."));
            proposta.setItemDestino(novoItem);
        }

        if (dto.getNovoUsuarioDestino() != null) {
            Usuario novoUsuario = usuarioRepository.findById(dto.getNovoUsuarioDestino())
                    .orElseThrow(() -> new EntityNotFoundException("Usuário destino não encontrado."));
            proposta.setUsuarioDestino(novoUsuario);
        }

        if (dto.getNovoStatus() != null)
            proposta.setStatus(dto.getNovoStatus());

        if (dto.getNovoValorDiferenca() != null)
            proposta.setValorDiferenca(dto.getNovoValorDiferenca());

        PropostaTroca atualizada = propostaRepository.save(proposta);
        return toResponseDTO(atualizada);
    }

    public void deletarProposta(Long id) {
        PropostaTroca proposta = propostaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proposta não encontrada."));

        propostaRepository.delete(proposta);
    }

    private PropostaResponseDTO toResponseDTO(PropostaTroca p) {
        return new PropostaResponseDTO(
                p.getIdProposta(),
                p.getItemOfertante().getIdItem(),
                p.getItemDestino().getIdItem(),
                p.getUsuarioOfertante().getIdUsuario(),
                p.getUsuarioDestino().getIdUsuario(),
                p.getValorDiferenca(),
                p.getStatus(),
                p.getDataProposta()
        );
    }
}
