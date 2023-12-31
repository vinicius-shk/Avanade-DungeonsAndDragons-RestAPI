package br.com.batalharepg.avanade.controller;

import br.com.batalharepg.avanade.dto.response.DadosTurnoResponse;
import br.com.batalharepg.avanade.factory.combate.TipoAcao;
import br.com.batalharepg.avanade.service.CombateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/dano")
@RequiredArgsConstructor
public class DanoController {
    private final CombateService combateService;

    @Operation(summary = "Executa a fase de cálculo de dano da batalha e atualiza a vida atual dos personagens")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "calculado com sucesso",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = DadosTurnoResponse.class)) }),
        @ApiResponse(responseCode = "400", description = "Dano já computado",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "batalha/tipo de ação nao encontrado",
            content = @Content) })
    @PatchMapping("/{uuid}")
    public ResponseEntity<DadosTurnoResponse> calcularDanoPersonagens(@PathVariable UUID uuid) {
        return ResponseEntity.ok(combateService.calcularValorTotalAcaoPersonagens(uuid, TipoAcao.DANO));
    }
}
