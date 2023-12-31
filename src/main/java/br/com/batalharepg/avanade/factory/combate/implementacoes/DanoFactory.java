package br.com.batalharepg.avanade.factory.combate.implementacoes;

import br.com.batalharepg.avanade.entities.DadosTurno;
import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.exceptions.EventoJaRealizadoException;
import br.com.batalharepg.avanade.factory.combate.AcaoFactory;
import br.com.batalharepg.avanade.factory.combate.TipoAcao;
import br.com.batalharepg.avanade.util.RolagemDados;
import org.springframework.stereotype.Component;

@Component
public class DanoFactory extends AcaoFactory {

    @Override
    public DadosTurno executarAcao(Personagem atacante, Personagem defensor, DadosTurno dadosTurno) {
        if (verificarSeAcaoDeveOcorrer(dadosTurno)) {
            return calcularDano(atacante, defensor, dadosTurno);
        }
        throw new EventoJaRealizadoException("Dano já computado");
    }

    @Override
    public TipoAcao getTipoAcao() {
        return TipoAcao.DANO;
    }

    private DadosTurno calcularDano(Personagem atacante, Personagem defensor, DadosTurno dadosTurno) {
        final var VALOR_ATAQUE_DEFENDIDO = 0;
        if (dadosTurno.getValorDoAtaqueAtacante() > dadosTurno.getValorDaDefesaDefensor()) {
            dadosTurno.setValorDoDanoAtacante(calcularValorTotalAcao(atacante));
        } else { dadosTurno.setValorDoDanoAtacante(VALOR_ATAQUE_DEFENDIDO); }
        if (dadosTurno.getValorDoAtaqueDefensor() > dadosTurno.getValorDaDefesaAtacante()) {
            dadosTurno.setValorDoDanoDefensor(calcularValorTotalAcao(defensor));
        } else { dadosTurno.setValorDoDanoDefensor(VALOR_ATAQUE_DEFENDIDO); }

        return aplicarDanoNaVidaAtual(dadosTurno);
    }

    private DadosTurno aplicarDanoNaVidaAtual(DadosTurno dadosTurno) {
        dadosTurno.setVidaAtualAtacante(dadosTurno.getVidaAtualAtacante() - dadosTurno.getValorDoDanoDefensor());
        dadosTurno.setVidaAtualDefensor(dadosTurno.getVidaAtualDefensor() - dadosTurno.getValorDoDanoAtacante());
        return dadosTurno;
    }

    @Override
    protected boolean verificarSeAcaoDeveOcorrer(DadosTurno dadosTurno) {
        return dadosTurno.getValorDoDanoAtacante() == null && dadosTurno.getValorDoDanoDefensor() == null;
    }

    @Override
    protected Integer calcularValorTotalAcao(Personagem personagem) {
        Integer valorDano = RolagemDados
            .rolarDados(personagem.getQuantidadeDadosAtaque(), personagem.getQuantidadeFacesDadosAtaque());
        return valorDano + personagem.getForca();
    }
}
