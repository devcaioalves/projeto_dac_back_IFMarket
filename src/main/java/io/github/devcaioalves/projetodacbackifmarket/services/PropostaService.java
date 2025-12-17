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
        Item itemOfertante = buscaItem(dto.getItemOfertante(), "Item ofertante não encontrado.");
        Item itemDestino   = buscaItem(dto.getItemDestino(), "Item destino não encontrado.");

        Usuario usuarioOfertante = buscaUsuario(dto.getUsuarioOfertante(), "Usuário ofertante não encontrado.");
        Usuario usuarioDestino   = buscaUsuario(dto.getUsuarioDestino(), "Usuário destino não encontrado.");

        validarProposta(itemOfertante, itemDestino, usuarioOfertante, usuarioDestino);

        PropostaTroca proposta = PropostaTroca.builder()
                .itemOfertante(itemOfertante)
                .itemDestino(itemDestino)
                .usuarioOfertante(usuarioOfertante)
                .usuarioDestino(usuarioDestino)
                .valorDiferenca(dto.getValorDiferenca())
                .status(StatusProposta.PENDENTE)
                .dataProposta(LocalDateTime.now())
                .build();

        return toResponseDTO(propostaRepository.save(proposta));
    }

    public PropostaResponseDTO buscarProposta(Long id) {
        return toResponseDTO(buscaProposta(id));
    }

    public Page<PropostaResponseDTO> listarTodasPropostas(Pageable pageable) {
        Page<PropostaTroca> page = propostaRepository.findAll(pageable);

        if (page.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma proposta encontrada.");
        }

        return page.map(this::toResponseDTO);
    }

    public PropostaResponseDTO atualizarProposta(Long id, PropostaAlterDTO dto) {
        PropostaTroca proposta = buscaProposta(id);

        if (dto.getNovoItemOfertante() != null) {
            proposta.setItemOfertante(buscaItem(dto.getNovoItemOfertante(), "Item ofertante não encontrado."));
        }

        if (dto.getNovoItemDestino() != null) {
            proposta.setItemDestino(buscaItem(dto.getNovoItemDestino(), "Item destino não encontrado."));
        }

        if (dto.getNovoUsuarioDestino() != null) {
            proposta.setUsuarioDestino(buscaUsuario(dto.getNovoUsuarioDestino(), "Usuário destino não encontrado."));
        }

        if (dto.getNovoStatus() != null) {
            proposta.setStatus(dto.getNovoStatus());
        }

        if (dto.getNovoValorDiferenca() != null) {
            proposta.setValorDiferenca(dto.getNovoValorDiferenca());
        }

        return toResponseDTO(propostaRepository.save(proposta));
    }

    public void deletarProposta(Long id) {
        propostaRepository.delete(buscaProposta(id));
    }

    // 🔎 Métodos auxiliares

    private Item buscaItem(Long id, String mensagemErro) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(mensagemErro));
    }

    private Usuario buscaUsuario(Long id, String mensagemErro) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(mensagemErro));
    }

    private PropostaTroca buscaProposta(Long id) {
        return propostaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proposta não encontrada."));
    }

    private void validarProposta(Item itemOfertante, Item itemDestino,
                                 Usuario usuarioOfertante, Usuario usuarioDestino) {
        if (usuarioOfertante.getIdUsuario().equals(usuarioDestino.getIdUsuario())) {
            throw new IllegalArgumentException("Não é possível propor troca com você mesmo.");
        }

        if (!itemOfertante.getUsuario().getIdUsuario().equals(usuarioOfertante.getIdUsuario())) {
            throw new IllegalArgumentException("O item ofertante não pertence ao usuário ofertante.");
        }

        if (!itemDestino.getUsuario().getIdUsuario().equals(usuarioDestino.getIdUsuario())) {
            throw new IllegalArgumentException("O item destino não pertence ao usuário destino.");
        }
    }

    private PropostaResponseDTO toResponseDTO(PropostaTroca p) {
        return new PropostaResponseDTO(
                p.getIdProposta(),
                p.getItemOfertante().getIdItem(),
                p.getItemDestino().getIdItem(),
                p.getUsuarioOfertante().getIdUsuario(),
                p.getUsuarioDestino().getIdUsuario(),
                p.getItemOfertante().getTitulo(),
                p.getItemDestino().getTitulo(),
                p.getUsuarioOfertante().getNome(),
                p.getUsuarioDestino().getNome(),
                p.getValorDiferenca(),
                p.getStatus(),
                p.getDataProposta()
        );
    }
}
