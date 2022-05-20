package com.github.harboat.battleships.fleet;

import com.github.harboat.battleships.CoreQueueProducer;
import com.github.harboat.battleships.NotificationProducer;
import com.github.harboat.battleships.board.BoardService;
import com.github.harboat.battleships.game.GameUtility;
import com.github.harboat.clients.game.ShipDto;
import com.github.harboat.clients.game.*;
import com.github.harboat.clients.notification.EventType;
import com.github.harboat.clients.notification.NotificationRequest;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.*;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Listeners({MockitoTestNGListener.class})
public class FleetServiceTest {

//    @Mock
//    private FleetRepository repository;
//    @Mock
//    private GameUtility gameUtility;
//    @Mock
//    private NotificationProducer producer;
//    @Mock
//    private CoreQueueProducer coreQueueProducer;
//    @Mock
//    private BoardService boardService;
//    private FleetService service;
//    @Captor
//    private ArgumentCaptor<NotificationRequest<FleetDto>> notificationRequestCaptor;
//    @Captor
//    private ArgumentCaptor<PlacementResponse> placementResponseCaptor;
//    @Captor
//    private ArgumentCaptor<ShotResponse> shotResponseCaptor;
//    @Captor
//    private ArgumentCaptor<PlayerWon> playerWonCaptor;
//    private ShipDto ship1;
//    private ShipDto ship2;
//
//
//    @BeforeMethod
//    public void setUp() {
//        service = new FleetService(repository, gameUtility, producer, coreQueueProducer, boardService);
//        ship1 = new ShipDto(ShipType.DESTROYER, new Masts(List.of(1)), new OccupiedCells(List.of(2, 11, 12)));
//        ship2 = new ShipDto(ShipType.DESTROYER, new Masts(List.of(3, 4)), new OccupiedCells(List.of(2, 12, 13, 14, 15, 5)));
//    }
//
//    @Test
//    public void shouldConvertMasts() {
//        //given
//        Masts masts = new Masts(List.of(1, 2, 3));
//        //when
//        MastsState actual = service.convertMasts(masts);
//        //then
//        assertEquals(actual.getMasts(), new MastsState(new HashMap<Integer, MastState>() {{
//            put(1, MastState.ALIVE);
//            put(2, MastState.ALIVE);
//            put(3, MastState.ALIVE);
//        }}).getMasts());
//    }
//
//    @Test
//    public void shouldCreateFleetWithProperEventType() {
//        //given
//        Collection<ShipDto> collection = List.of(ship1, ship2);
//        //when
//        service.create(new GamePlacement("test", "testPlayer", collection));
//        verify(producer).sendNotification(notificationRequestCaptor.capture());
//        verify(coreQueueProducer).sendResponse(placementResponseCaptor.capture());
//        NotificationRequest<FleetDto> actual = notificationRequestCaptor.getValue();
//        //then
//        assertEquals(actual.type(), EventType.FLEET_CREATED);
//    }
//
//    @Test
//    public void shouldCreateFleetWithProperPlayerId() {
//        //given
//        Collection<ShipDto> collection = List.of(ship1, ship2);
//        //when
//        service.create(new GamePlacement("test", "testPlayer", collection));
//        verify(producer).sendNotification(notificationRequestCaptor.capture());
//        verify(coreQueueProducer).sendResponse(placementResponseCaptor.capture());
//        NotificationRequest<FleetDto> actual = notificationRequestCaptor.getValue();
//        //then
//        assertEquals(actual.userId(), "testPlayer");
//    }
//
////    @Test
////    public void shouldCreateFleetWithProperFleet() {
////        //given
////        Collection<ShipDto> collection = List.of(ship1, ship2);
////        //when
////        service.create(new GamePlacement("test", "testPlayer", collection));
////        verify(producer).sendNotification(notificationRequestCaptor.capture());
////        verify(coreQueueProducer).sendResponse(placementResponseCaptor.capture());
////        NotificationRequest<FleetDto> actual = notificationRequestCaptor.getValue();
////        //then
////        System.out.println(actual.body().getShips());
////        assertEquals(actual.body().getShips(),collection );
////    }
//
//    @Test
//    public void shouldShootAndMiss() {
//        //given
//        Ship ship = new Ship(ShipType.SUBMARINE,
//                new MastsState(new HashMap<Integer, MastState>() {{
//                    put(3, MastState.ALIVE);
//                    put(4, MastState.ALIVE);
//                }}),
//                new OccupiedCells(Arrays.asList(2, 12, 13, 14, 15, 5)));
//        Fleet fleet = new Fleet("testFleet", "test", "testPlayer", List.of(ship));
//        ShotRequest shotRequest = new ShotRequest("test", "testPlayer", 10);
//        given(repository.findByGameIdAndPlayerId(any(), any())).willReturn(Optional.of(fleet));
//        //when
//        service.shoot(shotRequest);
//        verify(coreQueueProducer).sendResponse(shotResponseCaptor.capture());
//        var actual = shotResponseCaptor.getValue();
//        //then
//        assertTrue(actual.cells().contains(new Cell(10, false)));
//    }
//
//    @Test
//    public void shouldShootAndSunk() {
//        //given
//        Ship ship = new Ship(ShipType.DESTROYER,
//                new MastsState(new HashMap<Integer, MastState>() {{
//                    put(1, MastState.ALIVE);
//                }}),
//                new OccupiedCells(Arrays.asList(2, 11, 12)));
//        Fleet fleet = new Fleet("testFleet", "test", "testPlayer", List.of(ship));
//        ShotRequest shotRequest = new ShotRequest("test", "testPlayer", 1);
//        given(repository.findByGameIdAndPlayerId(any(), any())).willReturn(Optional.of(fleet));
//        doNothing().when(coreQueueProducer).sendResponse(shotResponseCaptor.capture());
//        //when
//        service.shoot(shotRequest);
//        verify(coreQueueProducer, times(2)).sendResponse(any());
//
////        var actual = playerWonCaptor.getValue();
////        //then
////        assertEquals(actual.playerId(), "testPlayer");
//    }
}