package br.com.batalharepg.avanade.configuration;

import br.com.batalharepg.avanade.exceptions.EventoJaRealizadoException;
import br.com.batalharepg.avanade.exceptions.NomeExistenteException;
import br.com.batalharepg.avanade.exceptions.NotFoundException;
import br.com.batalharepg.avanade.exceptions.TipoPersonagemDefensorIncorretoException;
import br.com.batalharepg.avanade.exceptions.TurnoNaoFinalizadoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionConfiguration {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(montarObjetoDeErro(e.getMessage()));
    }

    @ExceptionHandler(NomeExistenteException.class)
    public ResponseEntity<Object> handleNomeExistenteException(NomeExistenteException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(montarObjetoDeErro(e.getMessage()));
    }

    @ExceptionHandler(EventoJaRealizadoException.class)
    public ResponseEntity<Object> handleAtaqueJaRealizadoException(EventoJaRealizadoException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(montarObjetoDeErro(e.getMessage()));
    }

    @ExceptionHandler(TipoPersonagemDefensorIncorretoException.class)
    public ResponseEntity<Object> handleTipoPersonagemDefensorIncorretoException(TipoPersonagemDefensorIncorretoException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(montarObjetoDeErro(e.getMessage()));
    }

    @ExceptionHandler(TurnoNaoFinalizadoException.class)
    public ResponseEntity<Object> handleTurnoNaoFinalizadoException(TurnoNaoFinalizadoException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(montarObjetoDeErro(e.getMessage()));
    }

    private Map<String, String> montarObjetoDeErro(String mensagem) {
        Map<String, String> response = new HashMap<>();
        response.put("error", mensagem);
        return response;
    }
}
