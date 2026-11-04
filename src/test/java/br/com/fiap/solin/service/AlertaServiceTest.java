package br.com.fiap.solin.service;

import br.com.fiap.solin.entity.Alerta;
import br.com.fiap.solin.entity.Pet;
import br.com.fiap.solin.enums.NivelAlerta;
import br.com.fiap.solin.mapper.AlertaMapper;
import br.com.fiap.solin.repository.AlertaRepository;
import br.com.fiap.solin.strategy.RegraAlertaStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * O AlertaService nao pode criar de novo um alerta que ainda esta pendente.
 */
class AlertaServiceTest {

    private final AlertaRepository repository = mock(AlertaRepository.class);
    private final RegraAlertaStrategy regra = mock(RegraAlertaStrategy.class);
    private final AlertaService service = new AlertaService(repository, mock(AlertaMapper.class), List.of(regra));
    private final Pet pet = Pet.builder().id(1L).nome("Thor").build();

    @Test
    void criaAlertaQuandoNaoHaPendente() {
        when(regra.nome()).thenReturn("RegraTeste");
        when(regra.avaliar(pet)).thenReturn(Optional.of(Alerta.builder().nivel(NivelAlerta.AMARELO).build()));
        when(repository.existsByPetIdAndRegraAndResolvidoFalse(1L, "RegraTeste")).thenReturn(false);
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        List<Alerta> criados = service.avaliarRegrasParaPet(pet);

        assertEquals(1, criados.size());
        assertEquals("RegraTeste", criados.get(0).getRegra());
    }

    @Test
    void naoRepeteAlertaPendente() {
        when(regra.nome()).thenReturn("RegraTeste");
        when(repository.existsByPetIdAndRegraAndResolvidoFalse(1L, "RegraTeste")).thenReturn(true);

        List<Alerta> criados = service.avaliarRegrasParaPet(pet);

        assertTrue(criados.isEmpty());
        verify(repository, never()).save(any());
    }
}
