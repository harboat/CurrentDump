package com.github.harboat.battleships.fleet;

import com.github.harboat.battleships.NotificationProducer;
import com.github.harboat.battleships.board.BoardService;
import com.github.harboat.clients.core.placement.Masts;
import org.mockito.Mock;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

import static org.testng.Assert.*;

@Listeners({MockitoTestNGListener.class})
public class FleetServiceTest {

    @Mock
    private FleetRepository repository;
    @Mock
    private NotificationProducer producer;
    @Mock
    private BoardService boardService;

    private FleetService service;

    @BeforeMethod
    public void setUp() {
        service = new FleetService(repository, producer, boardService);
    }

    @Test
    public void shouldConvertMasts() {
        //given
        Masts masts = new Masts(List.of(1, 2, 3));
        //when
        MastsState actual = service.convertMasts(masts);
        //then
        assertEquals(actual.getMasts(), new MastsState(new HashMap<Integer, MastState>() {{
            put(1, MastState.ALIVE);
            put(2, MastState.ALIVE);
            put(3, MastState.ALIVE);
        }}).getMasts());
    }

}