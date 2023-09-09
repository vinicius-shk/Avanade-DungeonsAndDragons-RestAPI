package br.com.batalharepg.avanade.factory.concretefactories;

import br.com.batalharepg.avanade.entities.Personagem;
import br.com.batalharepg.avanade.factory.PersonagemFactory;
import br.com.batalharepg.avanade.factory.TipoPersonagem;
import org.springframework.stereotype.Component;

@Component
public class BarbaroFactory implements PersonagemFactory {
    @Override
    public Personagem criarPersonagem(String nome) {
        return new Personagem(nome,
            TipoPersonagem.BARBARO,
            21,
            10,
            2,
            5,
            2,
            8);
    }

    @Override
    public TipoPersonagem getTipoPersonagem() {
        return TipoPersonagem.BARBARO;
    }
}
