package br.com.batalharepg.avanade.service.batalha;

import br.com.batalharepg.avanade.dto.response.BatalhaResponse;
import br.com.batalharepg.avanade.entities.Batalha;
import br.com.batalharepg.avanade.entities.DadosTurno;
import br.com.batalharepg.avanade.exceptions.EventoJaRealizadoException;
import br.com.batalharepg.avanade.exceptions.NotFoundException;
import br.com.batalharepg.avanade.exceptions.TurnoNaoFinalizadoException;
import br.com.batalharepg.avanade.repository.BatalhaRepository;
import br.com.batalharepg.avanade.service.TurnoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateBatalhaService {
    private final BatalhaRepository batalhaRepository;
    private final TurnoService turnoService;

    @Transactional
    public BatalhaResponse verificarSeBatalhaAcabou(UUID uuid) {
        Batalha batalha = batalhaRepository.findByIdWithExceptionIfNotFound(uuid);
        if (Boolean.TRUE.equals(batalha.getBatalhaFinalizada())) {
            throw new EventoJaRealizadoException("Batalha já finalizada");
        }
        DadosTurno dadosTurnoAtual = obterTurnoDeMaiorNumero(batalha);
        verificarSeTurnoFoiFinalizado(dadosTurnoAtual);
        batalha = verificarSeHouveVencedor(batalha, dadosTurnoAtual);
        if (Boolean.TRUE.equals(batalha.getBatalhaFinalizada())) {
            return batalha.getResponseDto();
        }
        batalha.setNumeroTurnoAtual(batalha.getNumeroTurnoAtual() + 1);
        batalhaRepository.save(batalha);
        turnoService.criarTurnoAdicional(batalha, dadosTurnoAtual);
        return batalha.getResponseDto();
    }

    private DadosTurno obterTurnoDeMaiorNumero(Batalha batalha) {
        return batalha.getDadosTurnosList().stream()
            .filter(turno -> turno.getNumeroTurno().equals(batalha.getNumeroTurnoAtual()))
            .findFirst()
            .orElseThrow(() -> new NotFoundException("Turno não encontrado"));
    }

    private void verificarSeTurnoFoiFinalizado(DadosTurno dadosTurno) {
        if (dadosTurno.getValorDoAtaqueAtacante() == null || dadosTurno.getValorDoAtaqueDefensor() == null) {
            throw new TurnoNaoFinalizadoException("Ataque não computado, realize a ação de ataque primeiro");
        } else if (dadosTurno.getValorDaDefesaAtacante() == null || dadosTurno.getValorDaDefesaDefensor() == null) {
            throw new TurnoNaoFinalizadoException("Defesa não computada, realize a ação de defesa primeiro");
        } else if (dadosTurno.getValorDoDanoAtacante() == null || dadosTurno.getValorDoDanoDefensor() == null) {
            throw new TurnoNaoFinalizadoException("Dano não computado, realize a ação de dano primeiro");
        }
    }

    private Batalha verificarSeHouveVencedor(Batalha batalha, DadosTurno dadosTurno) {
        if (dadosTurno.getVidaAtualAtacante() <= 0 && dadosTurno.getVidaAtualDefensor() <= 0) {
            batalha.setBatalhaFinalizada(true);
            batalha.setNomeVencedor(Boolean.TRUE.equals(batalha.getAtacanteVenceuIniciativa()) ?
                batalha.getAtacante().getNome() :
                batalha.getDefensor().getNome());
            return batalhaRepository.save(batalha);
        } else if (dadosTurno.getVidaAtualAtacante() <= 0 || dadosTurno.getVidaAtualDefensor() <= 0) {
            batalha.setBatalhaFinalizada(true);
            batalha.setNomeVencedor(dadosTurno.getVidaAtualAtacante() <= 0 ? batalha.getDefensor().getNome() :
                batalha.getAtacante().getNome());
            return batalhaRepository.save(batalha);
        }
        return batalha;
    }
}
