package br.com.batalharepg.avanade.factory.personagem.implementacoes;

import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.factory.personagem.PersonagemFactory;
import br.com.batalharepg.avanade.factory.personagem.TipoClassePersonagem;
import br.com.batalharepg.avanade.factory.personagem.TipoPersonagem;
import org.springframework.stereotype.Component;

@Component
public class GuerreiroFactory extends PersonagemFactory {
    @Override
    public Personagem criarPersonagem(String nome) {
        Personagem personagem = new Personagem(nome, this.getTipoPersonagem(), this.getTipoClassePersonagem());
        return completaCriacaoPersonagem(personagem);
    }

    @Override
    public Integer getVida() {
        return 20;
    }

    @Override
    public Integer getForca() {
        return 7;
    }

    @Override
    public Integer getDefesa() {
        return 5;
    }

    @Override
    public Integer getAgilidade() {
        return 6;
    }

    @Override
    public Integer getQuantidadeDadosAtaque() {
        return 1;
    }

    @Override
    public Integer getQuantidadeFacesDadosAtaque() {
        return 12;
    }

    @Override
    public TipoPersonagem getTipoPersonagem() {
        return TipoPersonagem.HEROI;
    }

    @Override
    public TipoClassePersonagem getTipoClassePersonagem() { return TipoClassePersonagem.GUERREIRO; }

}
