package br.com.fiap.solin.strategy;

import br.com.fiap.solin.entity.Alerta;
import br.com.fiap.solin.entity.Especie;
import br.com.fiap.solin.entity.Evento;
import br.com.fiap.solin.entity.Pet;
import br.com.fiap.solin.enums.NivelAlerta;
import br.com.fiap.solin.enums.TipoEvento;
import br.com.fiap.solin.repository.EventoRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes das regras de alerta de urina (cachorro: limite de 8 horas).
 */
class RegrasSemUrinarTest {

    private final EventoRepository eventoRepository = mock(EventoRepository.class);

    private Pet cachorro() {
        Especie especie = Especie.builder().nome("Cachorro").horasMaximasSemUrinar(8).build();
        return Pet.builder().id(1L).nome("Thor").especie(especie).build();
    }

    private void ultimaUrinaHa(int horas) {
        Evento evento = Evento.builder().tipo(TipoEvento.URINOU).dataHora(LocalDateTime.now().minusHours(horas)).build();
        when(eventoRepository.findFirstByPetIdAndTipoOrderByDataHoraDesc(1L, TipoEvento.URINOU))
                .thenReturn(Optional.of(evento));
    }

    @Test
    void noveHorasSemUrinarGeraAlertaAmarelo() {
        ultimaUrinaHa(9);

        Optional<Alerta> alerta = new RegraSemUrinarAmarelo(eventoRepository).avaliar(cachorro());

        assertTrue(alerta.isPresent());
        assertEquals(NivelAlerta.AMARELO, alerta.get().getNivel());
    }

    @Test
    void cincoHorasSemUrinarNaoGeraAlerta() {
        ultimaUrinaHa(5);

        assertTrue(new RegraSemUrinarAmarelo(eventoRepository).avaliar(cachorro()).isEmpty());
        assertTrue(new RegraSemUrinarVermelho(eventoRepository).avaliar(cachorro()).isEmpty());
    }

    @Test
    void dezesseteHorasSemUrinarGeraAlertaVermelho() {
        ultimaUrinaHa(17);

        Optional<Alerta> alerta = new RegraSemUrinarVermelho(eventoRepository).avaliar(cachorro());

        assertTrue(alerta.isPresent());
        assertEquals(NivelAlerta.VERMELHO, alerta.get().getNivel());
    }

    @Test
    void semNenhumRegistroNaoGeraAlerta() {
        when(eventoRepository.findFirstByPetIdAndTipoOrderByDataHoraDesc(1L, TipoEvento.URINOU))
                .thenReturn(Optional.empty());

        assertTrue(new RegraSemUrinarAmarelo(eventoRepository).avaliar(cachorro()).isEmpty());
    }
}
