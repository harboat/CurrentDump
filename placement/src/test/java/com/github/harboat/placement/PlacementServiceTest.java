package com.github.harboat.placement;

import com.github.harboat.clients.core.board.Size;
import com.github.harboat.clients.core.placement.GamePlacement;
import com.github.harboat.clients.core.placement.PlacementRequest;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static org.mockito.BDDMockito.*;

@Listeners({MockitoTestNGListener.class})
public class PlacementServiceTest {

//    @Mock
//    private PlacementRepository repository;
//    @Mock
//    private ConfigQueueProducer sender;
//    private PlacementService service;
//    @Captor
//    ArgumentCaptor<GamePlacement> captor;
//
//    @BeforeMethod
//    public void setUp() {
//        service = new PlacementService(repository, sender);
//    }
//
//    @Test
//    public void shouldGenerateWithProperGameId() {
//        //given
//        Size size = new Size(10, 10);
//        //when
//        service.generate(new PlacementRequest("test", "testPlayer", size));
//        verify(sender).sendPlacement(captor.capture());
//        var actual = captor.getValue();
//        //then
//        assertEquals(actual.gameId(), "test");
//    }
//
//    @Test
//    public void shouldGenerateWithProperPlayerId() {
//        //given
//        Size size = new Size(10, 10);
//        //when
//        service.generate(new PlacementRequest("test", "testPlayer", size));
//        verify(sender).sendPlacement(captor.capture());
//        var actual = captor.getValue();
//        //then
//        assertEquals(actual.playerId(), "testPlayer");
//    }
//
//    @Test
//    public void shouldGenerateNotNullFleet() {
//        //given
//        Size size = new Size(10, 10);
//        //when
//        service.generate(new PlacementRequest("test", "testPlayer", size));
//        verify(sender).sendPlacement(captor.capture());
//        var actual = captor.getValue();
//        //then
//        assertNotNull(actual.ships());
//    }
//
//    @Test
//    public void shouldGenerate10ShipsFleet() {
//        //given
//        Size size = new Size(10, 10);
//        //when
//        service.generate(new PlacementRequest("test", "testPlayer", size));
//        verify(sender).sendPlacement(captor.capture());
//        var actual = captor.getValue();
//        //then
//        assertEquals(actual.ships().size(), 10);
//    }
}
