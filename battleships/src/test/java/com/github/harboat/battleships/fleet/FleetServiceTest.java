package com.github.harboat.battleships.fleet;

import com.github.harboat.battleships.NotificationProducer;
import com.github.harboat.battleships.board.BoardService;
import com.github.harboat.clients.core.placement.GamePlacement;
import com.github.harboat.clients.core.placement.Masts;
import com.github.harboat.clients.core.placement.OccupiedCells;
import com.github.harboat.clients.core.placement.ShipType;
import com.github.harboat.clients.notification.EventType;
import com.github.harboat.clients.notification.NotificationRequest;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.github.harboat.clients.core.placement.ShipDto;

import java.util.*;

import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

@Listeners({MockitoTestNGListener.class})
public class FleetServiceTest {

    @Mock
    private FleetRepository repository;
    @Mock
    private NotificationProducer producer;
    @Mock
    private BoardService boardService;
    private FleetService service;
    @Captor
    private ArgumentCaptor<NotificationRequest<FleetDto>> captor;
    private ShipDto ship1;
    private ShipDto ship2;
    private Fleet fleet;

    @BeforeMethod
    public void setUp() {
        service = new FleetService(repository, producer, boardService);
        ship1 = new ShipDto(ShipType.DESTROYER, new Masts(List.of(1)), new OccupiedCells(List.of(2, 11, 12)));
        ship2 = new ShipDto(ShipType.DESTROYER, new Masts(List.of(3, 4)), new OccupiedCells(List.of(2, 12, 13, 14, 15, 5)));
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

    @Test
    public void shouldCreateFleetWithProperEventType() {
        //given
        Collection<ShipDto> collection = List.of(ship1, ship2);
        //when
        service.create(new GamePlacement("test", "testPlayer", collection));
        verify(producer).sendNotification(captor.capture());
        NotificationRequest<FleetDto> actual = captor.getValue();
        //then
        assertEquals(actual.type(), EventType.FLEET_CREATED);
    }

    @Test
    public void shouldCreateFleetWithProperPlayerId() {
        //given
        Collection<ShipDto> collection = List.of(ship1, ship2);
        //when
        service.create(new GamePlacement("test", "testPlayer", collection));
        verify(producer).sendNotification(captor.capture());
        NotificationRequest<FleetDto> actual = captor.getValue();
        //then
        assertEquals(actual.userId(), "testPlayer");
    }

//    @Test
//    public void shouldCreateFleetWithProperFleet() {
//        //given
//        Collection<ShipDto> collection = List.of(ship1, ship2);
//        //when
//        service.create(new GamePlacement("test", "testPlayer", collection));
//        verify(producer).sendNotification(captor.capture());
//        NotificationRequest<FleetDto> actual = captor.getValue();
//        //then
//        System.out.println(actual.body().getShips());
//        assertEquals(actual.body().getShips(),collection );
//    }

}